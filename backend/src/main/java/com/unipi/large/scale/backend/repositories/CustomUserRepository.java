package com.unipi.large.scale.backend.repositories;

import com.unipi.large.scale.backend.data.aggregations.Country;
import com.unipi.large.scale.backend.data.aggregations.HighestVarianceCluster;
import com.unipi.large.scale.backend.data.Survey;
import com.unipi.large.scale.backend.entities.mongodb.MongoUser;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomUserRepository extends CustomRepository{

    public MongoUser findById(ObjectId id) {

        return mongoTemplate.findById(id, MongoUser.class);
    }

    public MongoUser findByEmail(String email) {

        return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), MongoUser.class);
    }

    public List<MongoUser> findAll() {

        return mongoTemplate.findAll(MongoUser.class);
    }

    public List<MongoUser> getUsersByUsernameStartingWith(String username) {

        Query query = new Query();
        query.addCriteria(Criteria.where("username").regex("^" + username));
        query.with(Sort.by(Sort.Direction.ASC, "username"));
        query.fields().include("first_name").include("last_name").include("picture");
        query.limit(10);
        return mongoTemplate.find(query, MongoUser.class);
    }

    public boolean updateUserInfo(ObjectId id, Map<String, String> toBeUpdated) {

        Update update = new Update();

        for (Map.Entry<String, String> field: toBeUpdated.entrySet()) {

            update.set(field.getKey(), field.getValue());
        }

        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), update, MongoUser.class).wasAcknowledged();
    }

    public MongoUser insertUser(MongoUser user) {

        return mongoTemplate.insert(user);
    }

    public boolean deleteUser(ObjectId id) {

        return mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), MongoUser.class).wasAcknowledged();
    }

    public boolean existsByUsername(String username) {

        return mongoTemplate.exists(Query.query(Criteria.where("username").is(username)), MongoUser.class);
    }

    public boolean existsByEmail(String email) {

        return mongoTemplate.exists(Query.query(Criteria.where("email").is(email)), MongoUser.class);
    }

    public HighestVarianceCluster getClusterWithHighestVariance() {

        GroupOperation groupByCluster = Aggregation.group("cluster")
            .max("agreeableness").as("AGRMax")
            .min("agreeableness").as("AGRMin")
            .max("openness").as("OPNMax")
            .min("openness").as("OPNMin")
            .max("conscientiousness").as("CSNMax")
            .min("conscientiousness").as("CSNMin")
            .max("extraversion").as("EXTMax")
            .min("extraversion").as("EXTMin")
            .max("neuroticism").as("ESTMax")
            .min("neuroticism").as("ESTMin")
            .max("time_spent").as("TimeSpentMax")
            .min("time_spent").as("TimeSpentMin");

        ProjectionOperation projectDifferences = Aggregation.project()
                .and("AGRMax").minus("AGRMin").as("differenceAGR")
                .and("OPNMax").minus("OPNMin").as("differenceOPN")
                .and("CSNMax").minus("CSNMin").as("differenceCSN")
                .and("EXTMax").minus("EXTMin").as("differenceEXT")
                .and("ESTMax").minus("ESTMin").as("differenceEST")
                .and("TimeSpentMax").minus("TimeSpentMin").as("differenceTS");


        AggregationExpression difference = AccumulatorOperators.Sum.sumOf("differenceAGR")
                .and("differenceOPN")
                .and("differenceCSN")
                .and("differenceEXT")
                .and("differenceEST")
                .and("differenceTS");

        ProjectionOperation projectDifference = Aggregation.project("_id")
                .and(difference).as("difference");

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "difference"));

        LimitOperation limitOperation = Aggregation.limit(1);

        Aggregation aggregation = Aggregation.newAggregation(groupByCluster, projectDifferences, projectDifference, sortOperation, limitOperation);

        AggregationResults<HighestVarianceCluster> result = mongoTemplate.aggregate(aggregation, "user", HighestVarianceCluster.class);

        return result.getUniqueMappedResult();
    }

    public List<Country> getTopKCountries(int k) {

        GroupOperation groupByCountry = Aggregation.group("country")
                .avg("agreeableness").as("AGR")
                .avg("openness").as("OPN")
                .avg("conscientiousness").as("CSN")
                .avg("extraversion").as("EXT")
                .avg("neuroticism").as("EST");

        AggregationExpression average = AccumulatorOperators.Avg.avgOf("AGR").and("OPN").and("CSN").and("EXT").and("EST");

        ProjectionOperation projectAverage = Aggregation.project("_id")
                .and(average).as("avg");

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "avg"));

        LimitOperation limitOperation = Aggregation.limit(k);

        Aggregation aggregation = Aggregation.newAggregation(groupByCountry, projectAverage, sortOperation, limitOperation);

        AggregationResults<Country> results = mongoTemplate.aggregate(aggregation, "user", Country.class);

        return results.getMappedResults();
    }

    public Survey getAverageClusterValues(int cluster) {

        MatchOperation matchCluster = Aggregation.match(Criteria.where("cluster").is(cluster));

        GroupOperation groupOperation = Aggregation.group("cluster")
                .avg("agreeableness").as("agreeableness")
                .avg("openness").as("openness")
                .avg("conscientiousness").as("conscientiousness")
                .avg("extraversion").as("extraversion")
                .avg("neuroticism").as("neuroticism")
                .avg("time_spent").as("timeSpent");

        Aggregation aggregation = Aggregation.newAggregation(matchCluster, groupOperation);

        AggregationResults<Survey> results = mongoTemplate.aggregate(aggregation, "user", Survey.class);

        return results.getUniqueMappedResult();
    }

    public List<MongoUser> findAllWithSurveyAndCluster() {

        Query query = new Query();
        query.fields().include("extraversion", "agreeableness", "conscientiousness", "neuroticism", "openness", "time_spent", "cluster");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findByClusterWithSurvey(int cluster) {

        Query query = new Query();
        query.addCriteria(Criteria.where("cluster").is(cluster));
        query.fields().include("extraversion", "agreeableness", "conscientiousness", "neuroticism", "openness", "time_spent");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findAllWithEmail() {

        Query query = new Query();
        query.fields().include("email");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findAllWithUsername() {

        Query query = new Query();
        query.fields().include("username");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findAllWithPassword() {

        Query query = new Query();
        query.fields().include("password");
        return mongoTemplate.find(query, MongoUser.class);
    }

    // UTILITIES

    public void updateEmail(MongoUser user, String email) {

        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("email", email);
        mongoTemplate.updateFirst(query, update, MongoUser.class);
    }

    public void bulkUpdateEmail(List<MongoUser> users) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        for (MongoUser user: users) {
            Update update = new Update();
            update.set("email", user.getEmail());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(user.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public void bulkUpdateUsername(List<MongoUser> users) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        for (MongoUser user: users) {
            Update update = new Update();
            update.set("username", user.getUsername());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(user.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public boolean updateCluster(MongoUser user)   {

        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("cluster", user.getCluster());
        return mongoTemplate.updateFirst(query, update, MongoUser.class).wasAcknowledged();
    }

    public void bulkUpdateCluster(List<MongoUser> users) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        for (MongoUser user: users) {
            Update update = new Update();
            update.set("cluster", user.getCluster());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(user.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public void updatePassword(MongoUser user)   {

        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("password", user.getPassword());
        mongoTemplate.updateFirst(query, update, MongoUser.class);
    }

    public void bulkUpdatePassword(List<MongoUser> users) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        for (MongoUser user: users) {
            Update update = new Update();
            update.set("password", user.getPassword());
            bulkOperations.updateOne(Query.query(Criteria.where("id").is(user.getId())), update);
        }
        System.out.println(bulkOperations.execute());
    }

    public void bulkCreateNewUser(List<MongoUser> users) {

        System.out.println(mongoTemplate.insert(users, MongoUser.class));
    }

    public void bulkDeleteUsers(List<MongoUser> users) {

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        for (MongoUser user: users) {
            Query query = Query.query(Criteria.where("id").is(user.getId()));
            bulkOperations.remove(query);
        }
        System.out.println(bulkOperations.execute());
    }

    public List<MongoUser> findAllIds() {

        Query query = new Query();
        query.fields().include("id");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findAllClusters() {

        Query query = new Query();
        query.fields().include("cluster");
        return mongoTemplate.find(query, MongoUser.class);
    }

    public List<MongoUser> findToGenerateComments() {

        Query query = new Query();
        query.fields().include("first_name").include("last_name");
        return mongoTemplate.find(query, MongoUser.class);
    }

}
