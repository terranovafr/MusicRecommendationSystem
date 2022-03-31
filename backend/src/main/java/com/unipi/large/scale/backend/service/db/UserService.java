package com.unipi.large.scale.backend.service.db;

import com.unipi.large.scale.backend.data.aggregations.Country;
import com.unipi.large.scale.backend.entities.mongodb.MongoUser;
import com.unipi.large.scale.backend.entities.neo4j.Neo4jUser;
import com.unipi.large.scale.backend.service.exceptions.DbException;
import com.unipi.large.scale.backend.service.exceptions.LoginException;
import com.unipi.large.scale.backend.service.exceptions.RegistrationException;
import com.unipi.large.scale.backend.service.exceptions.SimilarityException;
import com.unipi.large.scale.backend.data.Distance;
import com.unipi.large.scale.backend.data.Login;
import com.unipi.large.scale.backend.data.Survey;
import com.unipi.large.scale.backend.entities.neo4j.FriendRequest;
import org.bson.types.ObjectId;
import org.neo4j.driver.summary.SummaryCounters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService extends EntityService {

    public MongoUser getMongoUserById(ObjectId id) {

        MongoUser mongoUser = customUserRepository.findById(id);

        if (mongoUser == null) {
            throw new DbException("No User found with id " + id);
        }

        return mongoUser;
    }

    @Transactional
    public void deleteUserById(String id) {

        ObjectId objectId = new ObjectId(id);

        if (!customUserRepository.deleteUser(objectId)) {
            logger.error("Unable to delete mongodb user of id " + id);
            throw new DbException("Unable to delete mongodb user of id " + id);
        }

        if (!customCommentRepository.deleteUserComments(objectId)){
            logger.error("Unable to delete comments for user " + id);
        }

        if (neo4jUserDao.deleteByMongoId(id).nodesDeleted() == 0) {
            logger.error("Unable to delete neo4j user of id " + id);
            throw new DbException("Unable to delete neo4j user of id " + id);
        }
    }

    @Transactional
    public MongoUser registerUser(MongoUser mongoUser) {

        if (customUserRepository.existsByUsername(mongoUser.getUsername().toLowerCase(Locale.ROOT))) throw new RegistrationException("Username already taken");

        if (customUserRepository.existsByEmail(mongoUser.getEmail().toLowerCase(Locale.ROOT))) throw new RegistrationException("This email already belongs to another user");

        mongoUser.setUsername(mongoUser.getUsername().toLowerCase(Locale.ROOT));
        mongoUser.setEmail(mongoUser.getEmail().toLowerCase(Locale.ROOT));
        mongoUser.setPassword(passwordEncoder.encode(mongoUser.getPassword()));
        mongoUser.setRegistrationDate(LocalDate.now());

        MongoUser insertedUser = customUserRepository.insertUser(mongoUser);
        ObjectId objectId = insertedUser.getId();

        if (objectId == null) {
            logger.error("Unable to create mongodb user");
            throw new DbException("Unable to create mongodb user");
        }

        Neo4jUser neo4jUser = new Neo4jUser(objectId.toString(), insertedUser.getFirstName(), insertedUser.getLastName(), insertedUser.getCountry(), insertedUser.getImage());

        if (neo4jUserDao.createUser(neo4jUser).nodesCreated() == 0){

            if (!customUserRepository.deleteUser(objectId)){
                logger.error("Unable to delete mongodb user of id " + objectId + " after not being able to create him on neo4j");
                throw new DbException("Unable to delete mongodb user of id " + objectId + " after not being able to create him on neo4j");
            }
        }

        if (utils.areSurveyValuesCorrect(insertedUser)) {

            clustering.performClustering(insertedUser);

            if (!customUserRepository.updateCluster(insertedUser)){
                logger.error("Unable to update cluster for mongodb user of id " + objectId);
                throw new DbException("Unable to update cluster for mongodb user of id " + objectId);
            }

            neo4jUser.setCluster(insertedUser.getCluster());

            if (neo4jUserDao.updateCluster(neo4jUser).propertiesSet() == 0) {
                logger.error("Unable to update cluster for neo4j user of id " + objectId);
            }

            setNearestNeighbors(insertedUser);
        }

        return insertedUser;
    }

    @Transactional
    public void updateUser(MongoUser newData) {

        ObjectId id = newData.getId();

        MongoUser dbData = getMongoUserById(id);
        Neo4jUser neo4jUser = new Neo4jUser(dbData.getId().toString(), dbData.getFirstName(), dbData.getLastName(), dbData.getCluster(), dbData.getCountry(), dbData.getImage());

        newData.setUsername(newData.getUsername().toLowerCase(Locale.ROOT));
        newData.setEmail(newData.getEmail().toLowerCase(Locale.ROOT));

        Map<String, String> toBeUpdated = new HashMap<>();

        getChangedInfo(dbData, newData, toBeUpdated, neo4jUser);

        if (!toBeUpdated.isEmpty()) {
            if (!customUserRepository.updateUserInfo(id, toBeUpdated)){
                logger.error("Unable to update info for mongodb user of id " + id);
                throw new DbException("Unable to update info for mongodb user of id " + id);
            }
        }

        if (toBeUpdated.containsKey("first_name") || toBeUpdated.containsKey("last_name") || toBeUpdated.containsKey("country") || toBeUpdated.containsKey("picture")){
            if (neo4jUserDao.updateUser(neo4jUser).propertiesSet() == 0) {
                logger.error("Unable to update info for neo4j user of id " + id);
                throw new DbException("Unable to update info for neo4j user of id" + id);
            }
        }
    }

    public MongoUser login(Login login) {

        MongoUser mongoUser = customUserRepository.findByEmail(login.getEmail().toLowerCase(Locale.ROOT));

        if (mongoUser == null) throw new LoginException("No user found with email: " + login.getEmail());

        if (!passwordEncoder.matches(login.getPassword(), mongoUser.getPassword())) throw new LoginException("Invalid password");

        return mongoUser;
    }

    public List<MongoUser> getAllUsers() {

        return customUserRepository.findAll();
    }

    public List<Neo4jUser> getSimilarUsers(String id) {

        getNeo4jUserByMongoId(id);

        return neo4jUserDao.getSimilarUsersWithFriendshipStatus(id);
    }

    public List<Neo4jUser> getNearbySimilarUsers(String id) {

        getNeo4jUserByMongoId(id);

        return neo4jUserDao.getNearbySimilarUsersWithFriendshipStatus(id);
    }

    @Transactional
    public void addFriendRequest(String fromUserId, String toUserId) {

        getNeo4jUserByMongoId(fromUserId);
        getNeo4jUserByMongoId(toUserId);

        if (neo4jUserDao.addFriendRequest(fromUserId, toUserId).relationshipsCreated() == 0){
            logger.error("Unable to create a friend request between " + fromUserId + " and " + toUserId);
            throw new DbException("Unable to create a friend request between " + fromUserId + " and " + toUserId);
        }
    }

    @Transactional
    public void deleteFriendRequest(String fromUserId, String toUserId) {

        getNeo4jUserByMongoId(fromUserId);
        getNeo4jUserByMongoId(toUserId);

        if (neo4jUserDao.deleteFriendRequest(fromUserId, toUserId).relationshipsDeleted() == 0){
            logger.error("Unable to delete a friend request between " + fromUserId + " and " + toUserId + ". It's possibile that the users did not share a friend request.");
            throw new DbException("Unable to delete a friend request between " + fromUserId + " and " + toUserId + ". It's possibile that the users did not share a friend request.");
        }
    }

    @Transactional
    public void updateFriendRequest(String fromUserId, String toUserId, int status) {

        getNeo4jUserByMongoId(fromUserId);
        getNeo4jUserByMongoId(toUserId);

        FriendRequest.Status newStatus = status == 0 ? FriendRequest.Status.REFUSED : FriendRequest.Status.ACCEPTED;

        SummaryCounters counters = neo4jUserDao.updateFriendRequest(fromUserId, toUserId, newStatus);

        if (counters.relationshipsCreated() == 0 && counters.propertiesSet() == 0) {
            logger.error("Unable to update or create friend request between " + fromUserId + " and " + toUserId);
            throw new DbException("Unable to update or create friend request between " + fromUserId + " and " + toUserId);
        }
    }

    public List<Neo4jUser> getIncomingFriendRequests(String id) {

        getNeo4jUserByMongoId(id);

        return neo4jUserDao.getIncomingFriendRequests(id);
    }

    public Neo4jUser getNeo4jUserByMongoId(String id) {

        Optional<Neo4jUser> optionalNeo4jUser = neo4jUserDao.getByMongoId(id);

        if (optionalNeo4jUser.isEmpty()) throw new DbException("Unable to find user with id: " + id + " on neo4j");

        return optionalNeo4jUser.get();
    }

    private void getChangedInfo(MongoUser dbData, MongoUser newData, Map<String, String> toBeUpdated, Neo4jUser neo4jUser) {

        if (!Objects.equals(dbData.getFirstName(), newData.getFirstName())) {
            toBeUpdated.put("first_name", newData.getFirstName());
            neo4jUser.setFirstName(newData.getFirstName());
        }
        if (!Objects.equals(dbData.getLastName(), newData.getLastName())) {
            toBeUpdated.put("last_name", newData.getLastName());
            neo4jUser.setLastName(newData.getLastName());
        }
        if (!Objects.equals(dbData.getGender(), newData.getGender())) {
            toBeUpdated.put("gender", newData.getGender());
        }
        if (!Objects.equals(dbData.getCountry(), newData.getCountry())) {
            toBeUpdated.put("country", newData.getCountry());
            neo4jUser.setCountry(newData.getCountry());
        }
        if (!Objects.equals(dbData.getUsername(), newData.getUsername())) {
            if (customUserRepository.existsByUsername(newData.getUsername())) {
                throw new RegistrationException("Username already taken!");
            }
            toBeUpdated.put("username", newData.getUsername());
        }
        if (!Objects.equals(dbData.getPhone(), newData.getPhone())) {
            toBeUpdated.put("phone", newData.getPhone());
        }
        if (!Objects.equals(dbData.getEmail(), newData.getEmail())) {
            if (customUserRepository.existsByEmail(newData.getEmail())) {
                throw new RegistrationException("Email already used!");
            }
            toBeUpdated.put("email", newData.getEmail());
        }
        if (!Objects.equals(newData.getPassword(), "")) {
            toBeUpdated.put("password", passwordEncoder.encode(newData.getPassword()));
        }

        if (!Objects.equals(dbData.getImage(), newData.getImage())) {
            toBeUpdated.put("picture", newData.getImage());
            neo4jUser.setImage(newData.getImage());
        }
    }

    public Survey getUserClusterValues(String id) {

        MongoUser mongoUser = getMongoUserById(new ObjectId(id));

        if (!utils.areSurveyValuesCorrect(mongoUser)) {
            throw new RuntimeException("The user " + id + " does not have a valid survey");
        }

        return new Survey(mongoUser);
    }

    public List<Neo4jUser> getFriends(String id) {

        getNeo4jUserByMongoId(id);

        return neo4jUserDao.getUserFriends(id);
    }

    public Survey getUserClusterClusterValues(int id) {

        return customUserRepository.getAverageClusterValues(id);
    }

    public MongoUser getMostSimilarUser(String id) {

        getNeo4jUserByMongoId(id);

        List<Neo4jUser> mostSimilarUser = neo4jUserDao.getMostSimilarUser(id);

        Optional<Neo4jUser> similarUser = mostSimilarUser.stream().findFirst();

        if (similarUser.isEmpty()) throw new SimilarityException("User " + id + " does not have similar users");

        return getMongoUserById(new ObjectId(similarUser.get().getMongoId()));
    }


    private void setNearestNeighbors(MongoUser mongoUser) {

        getNeo4jUserByMongoId(mongoUser.getId().toString());

        if (!utils.areSurveyValuesCorrect(mongoUser)) {
            throw new RuntimeException("User does not have a valid survey");
        }
        if (mongoUser.getCluster() == 0){
            throw new RuntimeException("User does not belong to any cluster");
        }

        Survey userSurvey = new Survey(mongoUser);
        List<MongoUser> mongoUsers = customUserRepository.findByClusterWithSurvey(mongoUser.getCluster());

        List<Distance> distances = new ArrayList<>();

        for (MongoUser user: mongoUsers) {

            if (user.getId().toString().equals(mongoUser.getId().toString())) continue;
            if (!utils.areSurveyValuesCorrect(user)) continue;
            Survey survey = new Survey(user);
            distances.add(new Distance(user.getId().toString(), utils.getDistance(userSurvey, survey)));
        }
        distances.sort(Comparator.comparingDouble(Distance::getDistance));

        neo4jUserDao.setNearestNeighbors(mongoUser.getId().toString(), distances, 20);
    }

    public void quarantineUser(String id) {

        getNeo4jUserByMongoId(id);

        neo4jUserDao.quarantineUser(id);
    }

    public List<MongoUser> searchUsersByUsername(String username) {

        return customUserRepository.getUsersByUsernameStartingWith(username.toLowerCase(Locale.ROOT));
    }

    public int getClusterWithHighestVariance() {

        return customUserRepository.getClusterWithHighestVariance().getId();
    }

    public List<Country> getTopKCountries(int k){

        return customUserRepository.getTopKCountries(k);
    }
}
