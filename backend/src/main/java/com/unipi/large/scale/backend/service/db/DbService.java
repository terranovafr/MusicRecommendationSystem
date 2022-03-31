package com.unipi.large.scale.backend.service.db;

import com.unipi.large.scale.backend.data.Counter;
import com.unipi.large.scale.backend.data.Distance;
import com.unipi.large.scale.backend.data.Survey;
import com.unipi.large.scale.backend.entities.mongodb.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DbService extends EntityService{

    private static final String CSV_FILE_NAME_LIKES = "/Users/jacopo/IdeaProjects/large-scale-project/large-scale-backend/song_preference.csv";
    private static final String CSV_FILE_NAME_SIMILARITIES = "/Users/jacopo/IdeaProjects/large-scale-project/large-scale-backend/similarities.csv";
    private static final String CSV_FILE_NAME_PASSWORDS = "/Users/jacopo/IdeaProjects/large-scale-project/large-scale-backend/passwords.csv";

    public void addLikesHeuristic() {

        List<String[]> csvRelationships = new ArrayList<>();
        List<MongoUser> mongoUsers = customUserRepository.findAllWithSurveyAndCluster();
        List<MongoUser> extroverseUsers = new ArrayList<>();
        List<MongoUser> agreeableUsers = new ArrayList<>();
        List<MongoUser> conscentiousUsers = new ArrayList<>();
        List<MongoUser> neuroticUsers = new ArrayList<>();
        List<MongoUser> openUsers = new ArrayList<>();

        for (MongoUser mongoUser: mongoUsers) {

            switch (getBiggestPersonalityTrait(mongoUser)) {
                case 0 -> extroverseUsers.add(mongoUser);
                case 1 -> agreeableUsers.add(mongoUser);
                case 2 -> conscentiousUsers.add(mongoUser);
                case 3 -> neuroticUsers.add(mongoUser);
                case 4 -> openUsers.add(mongoUser);
            }
        }

        addRelationships(openUsers, "liveness", csvRelationships, Sort.Direction.DESC, Sort.Direction.ASC);
        addRelationships(conscentiousUsers, "instrumentalness", csvRelationships, Sort.Direction.ASC, Sort.Direction.DESC);
        addRelationships(extroverseUsers, "energy", csvRelationships, Sort.Direction.ASC, Sort.Direction.DESC);
        addRelationships(agreeableUsers, "tempo", csvRelationships, Sort.Direction.ASC, Sort.Direction.DESC);
        addRelationships(neuroticUsers, "instrumentalness", csvRelationships, Sort.Direction.ASC, Sort.Direction.DESC);

        File csvOutputFile = new File(CSV_FILE_NAME_LIKES);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            csvRelationships.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addRelationships(List<MongoUser> users, String type, List<String[]> csvRelationships, Sort.Direction bestDirection, Sort.Direction worstDirection) {

        List<MongoSong> bestTypeSongs = customSongRepository.findSongsSortBy(type, bestDirection);
        List<MongoSong> worstTypeSongs = customSongRepository.findSongsSortBy(type, worstDirection);

        for (MongoUser mongoUser: users) {

            int NUM_BEST_SONGS = 10;
            List<MongoSong> bestSongs = extractKRandom(bestTypeSongs, NUM_BEST_SONGS);

            for (MongoSong song: bestSongs) {
                csvRelationships.add(new String[] {mongoUser.getId().toString(), song.getId().toString(), "1"});
            }

            int NUM_WORST_SONGS = 5;
            List<MongoSong> worstSongs = extractKRandom(worstTypeSongs, NUM_WORST_SONGS);
            for (MongoSong song: worstSongs) {
                csvRelationships.add(new String[] {mongoUser.getId().toString(), song.getId().toString(), "-1"});
            }
        }
    }

    private String convertToCSV(String[] data) {
        return String.join(",", data);
    }

    private List<MongoSong> extractKRandom(List<MongoSong> songs, int k) {

        Random random = new Random();
        List<MongoSong> extractedSongs = new ArrayList<>(k);

        for (int i = 0; i < k; i++) {
            int randomIndex;
            MongoSong song;
            do {
                randomIndex = random.nextInt(songs.size());
                song = songs.get(randomIndex);
            } while (extractedSongs.contains(song));

            extractedSongs.add(song);
        }

        return extractedSongs;
    }

    private int getBiggestPersonalityTrait(MongoUser user) {

        double[] personalityValues = new double[] {user.getExtraversion(), user.getAgreeableness(), user.getConscientiousness(), user.getNeuroticism(), user.getOpenness()};

        int maxAt = 0;

        for (int i = 0; i < personalityValues.length; i++) {
            maxAt = personalityValues[i] > personalityValues[maxAt] ? i : maxAt;
        }

        return maxAt;
    }

    public void generateSimilarities() {

        List<String[]> csvRelationships = new ArrayList<>();

        for (int i = 1; i < 6; i++) {

            List<MongoUser> users = customUserRepository.findByClusterWithSurvey(i);
            Map<String, List<Distance>> stringListMap = new HashMap<>();

            int index = 0;

            for (MongoUser user: users) {

                System.out.println("User " + index);
                index++;

                Survey userSurvey = new Survey(user);

                List<Distance> distances = new ArrayList<>();

                for (MongoUser toUser: users) {
                    if (user.getId().toString().equals(toUser.getId().toString())) continue;
                    if (!utils.areSurveyValuesCorrect(toUser)) continue;
                    Survey toUserSurvey = new Survey(toUser);
                    distances.add(new Distance(toUser.getId().toString(), utils.getDistance(userSurvey, toUserSurvey)));
                }
                distances.sort(Comparator.comparingDouble(Distance::getDistance));

                List<Distance> distanceList = new ArrayList<>();

                for (int j = 0; j < 10; j++) {
                    Distance distance = distances.get(j);
                    boolean contains = false;
                    List<Distance> distances1 = stringListMap.get(distance.getUserId());
                    if (distances1 != null)
                    {
                        for (Distance newDistance: stringListMap.get(distance.getUserId())){
                            if (Objects.equals(newDistance.getUserId(), user.getId().toString())) {
                                contains = true;
                                break;
                            }
                        }
                    }
                    if (!contains){
                        distanceList.add(distance);
                    }
                }

                stringListMap.put(user.getId().toString(), distanceList);
            }

            for (Map.Entry<String, List<Distance>> entry: stringListMap.entrySet()){

                for (Distance distance: entry.getValue()) {
                    double weight;
                    if (distance.getDistance() == 0) {
                        weight = 100;
                    } else {
                        weight = Math.round(1/Math.pow(distance.getDistance(), 2)* 100.00) / 100.00;
                    }
                    csvRelationships.add(new String[] {entry.getKey(), distance.getUserId(), String.valueOf(weight)});
                }
            }
        }

        File csvOutputFile = new File(CSV_FILE_NAME_SIMILARITIES);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            csvRelationships.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateComments(){

        Random random = new Random();

        String[] comments = {"Nice song.", "Really dope!", "Awesome song!", "Best song!", "Wow, this sucks.", "Really amazing song!", "Really bad song.", "This song couldn't be worse", "The first album was better.", "The guitarist is not good at all", "Awesome sound", "Really chill", "Average song.", "Nothing special.", "This song could have been better.", "Really disappointed", "I love Taylor Swift"};

        List<MongoUser> users = customUserRepository.findToGenerateComments();
        List<MongoSong> songs = customSongRepository.findToGenerateComments();
        List<Comment> toBeCreated = new ArrayList<>();
        List<MongoSong> toBeUpdated = new ArrayList<>();

        for (MongoSong song: songs) {

            for (int i = 0; i < 15; i++) {
                MongoUser user = users.get(random.nextInt(users.size()));
                String text = comments[random.nextInt(comments.length)];
                toBeCreated.add(createComment(user, text, song));
            }
            List<Comment> commentList = customCommentRepository.bulkInsertComments(toBeCreated);
            song.setComments(new ArrayList<>());

            for (int i = 0; i < 10; i++) {
                song.addComment(createCommentSubset(commentList.get(i)));
            }

            toBeUpdated.add(song);
            toBeCreated.clear();
        }

        customSongRepository.bulkUpdateComments(toBeUpdated);
    }

    private Comment createComment(MongoUser user, String text, MongoSong song) {

        Comment comment = new Comment();
        comment.setName(user.getFirstName());
        comment.setSurname(user.getLastName());
        comment.setUserId(user.getId());
        comment.setSongId(song.getId());
        comment.setText(text);
        comment.setDate(LocalDate.now());
        return comment;
    }

    private CommentSubset createCommentSubset(Comment comment) {

        CommentSubset commentSubset = new CommentSubset();
        commentSubset.setName(comment.getName());
        commentSubset.setSurname(comment.getSurname());
        commentSubset.setUserId(comment.getUserId());
        commentSubset.setText(comment.getText());
        commentSubset.setCommentId(comment.getId());
        commentSubset.setDate(comment.getDate());
        return commentSubset;
    }

    public void setLikesMongoDb() {

        List<MongoSong> mongoSongs = customSongRepository.findAllIds();
        Map<String, MongoSong> mongoSongMap = new HashMap<>();

        for (MongoSong song: mongoSongs) {
            List<Like> likeList = new ArrayList<>();
            for (int i = 1; i < 6; i++) {

                Like like = new Like(i, 0, 0);
                likeList.add(like);
            }

            song.setLikes(likeList);
            mongoSongMap.put(song.getId().toString(), song);
        }

        Map<String, Integer> clusterMap = new HashMap<>();

        List<MongoUser> mongoUsers = customUserRepository.findAllClusters();

        for (MongoUser user: mongoUsers) {

            clusterMap.put(user.getId().toString(), user.getCluster());
        }

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/jacopo/IdeaProjects/large-scale-project/likes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (List<String> record: records) {

            int cluster = clusterMap.get(record.get(0));
            MongoSong song = mongoSongMap.get(record.get(1));
            Like like = song.getLikes().get(cluster - 1);
            if (Integer.parseInt(record.get(2)) == 1) {
                like.setNumLikes(like.getNumLikes() + 1);
            } else {
                like.setNumUnlikes(like.getNumUnlikes() + 1);
            }
        }

        List<MongoSong> toBeUpdated = new ArrayList<>();

        for (Map.Entry<String, MongoSong> songEntry: mongoSongMap.entrySet()) {

            MongoSong song = songEntry.getValue();
            List<Like> likeList = song.getLikes();
            int predominantCluster = 0;
            int maxLikes = 0;

            for (Like like: likeList) {

                int likes = like.getNumLikes();
                int unlikes = like.getNumUnlikes();
                int totalLikes = likes - unlikes;
                if (totalLikes > maxLikes) {
                    predominantCluster = like.getCluster();
                    maxLikes = totalLikes;
                }
            }
            if (maxLikes != 0){
                int post = 0;
            }
            if (predominantCluster == 0) {
                predominantCluster = ThreadLocalRandom.current().nextInt(1, 6);
            }
            song.setCluster(predominantCluster);
            toBeUpdated.add(song);
        }

        customSongRepository.bulkUpdateLikes(toBeUpdated);
    }

    public void hashPasswords() {

        List<MongoUser> mongoUsers = customUserRepository.findAllWithPassword();
        List<MongoUser> usersToBeUpdated = new ArrayList<>();

        for (MongoUser mongoUser: mongoUsers) {
            String password = mongoUser.getPassword();
            mongoUser.setPassword(passwordEncoder.encode(password));
            usersToBeUpdated.add(mongoUser);

            if (usersToBeUpdated.size() == 1000) {
                customUserRepository.bulkUpdatePassword(usersToBeUpdated);
                usersToBeUpdated.clear();
            }
        }

        if (!usersToBeUpdated.isEmpty()) {
            customUserRepository.bulkUpdatePassword(usersToBeUpdated);
        }
    }

    public void generatePasswords() {

        List<MongoUser> mongoUsers = customUserRepository.findAllIds();
        List<MongoUser> usersToBeUpdated = new ArrayList<>();

        for (MongoUser mongoUser: mongoUsers) {
            String password = utils.generatePassword();
            mongoUser.setPassword(password);
            usersToBeUpdated.add(mongoUser);

            if (usersToBeUpdated.size() == 1000) {
                customUserRepository.bulkUpdatePassword(usersToBeUpdated);
                usersToBeUpdated.clear();
            }
        }

        if (!usersToBeUpdated.isEmpty()) {
            customUserRepository.bulkUpdatePassword(usersToBeUpdated);
        }
    }

    public void changeDuplicateEmails() {

        List<MongoUser> users = customUserRepository.findAllWithEmail();

        Map<String, Counter> emailMap = new HashMap<>();

        Map<String, List<MongoUser>> duplicateEmails = new HashMap<>();

        for (MongoUser user: users) {

            if (emailMap.containsKey(user.getEmail())) {
                Counter counter = emailMap.get(user.getEmail());
                counter.setCount(counter.getCount() + 1);
                counter.getUsers().add(user);
                emailMap.put(user.getEmail(), counter);
            }
            else {
                emailMap.put(user.getEmail(), new Counter(1, user));
            }
        }

        for (Map.Entry<String, Counter> entry: emailMap.entrySet()) {
            if (entry.getValue().getCount() > 1) duplicateEmails.put(entry.getKey(), entry.getValue().getUsers());
        }

        List<MongoUser> toBeUpdated = new ArrayList<>();

        for (Map.Entry<String, List<MongoUser>> entry: duplicateEmails.entrySet()) {

            List<MongoUser> mongoUsers = entry.getValue();

            if (mongoUsers.size() > 1) {

                int i = 0;

                for (MongoUser mongoUser: mongoUsers) {

                    String userEmail = mongoUser.getEmail();
                    userEmail = userEmail.replace("@", i + "@");
                    mongoUser.setEmail(userEmail);
                    i++;
                }
            }

            toBeUpdated.addAll(mongoUsers);

            if (toBeUpdated.size() > 950) {
                customUserRepository.bulkUpdateEmail(toBeUpdated);
                toBeUpdated.clear();
            }
        }
        customUserRepository.bulkUpdateEmail(toBeUpdated);
    }

    public void changeDuplicateUsername() {

        List<MongoUser> users = customUserRepository.findAllWithUsername();

        Map<String, Counter> usernameMap = new HashMap<>();

        Map<String, List<MongoUser>> duplicateUsernames = new HashMap<>();

        for (MongoUser user: users) {

            if (usernameMap.containsKey(user.getUsername())) {
                Counter counter = usernameMap.get(user.getUsername());
                counter.setCount(counter.getCount() + 1);
                counter.getUsers().add(user);
                usernameMap.put(user.getUsername(), counter);
            }
            else {
                usernameMap.put(user.getUsername(), new Counter(1, user));
            }
        }

        for (Map.Entry<String, Counter> entry: usernameMap.entrySet()) {
            if (entry.getValue().getCount() > 1) duplicateUsernames.put(entry.getKey(), entry.getValue().getUsers());
        }

        List<MongoUser> toBeUpdated = new ArrayList<>();

        for (Map.Entry<String, List<MongoUser>> entry: duplicateUsernames.entrySet()) {

            List<MongoUser> mongoUsers = entry.getValue();

            if (mongoUsers.size() > 1) {

                int i = 0;

                for (MongoUser mongoUser: mongoUsers) {

                    String userUsername = mongoUser.getUsername();
                    userUsername = userUsername + i;
                    mongoUser.setUsername(userUsername);
                    i++;
                }
            }

            toBeUpdated.addAll(mongoUsers);

            if (toBeUpdated.size() > 950) {
                customUserRepository.bulkUpdateUsername(toBeUpdated);
                toBeUpdated.clear();
            }
        }
        customUserRepository.bulkUpdateUsername(toBeUpdated);
    }

    public void savePlainTextPasswords() {

        List<MongoUser> mongoUsers = customUserRepository.findAllWithEmail();

        List<String> emails = new ArrayList<>();

        List<String[]> passwords = new ArrayList<>();

        Map<String, String> passwordMap = new HashMap<>();

        for (MongoUser mongoUser: mongoUsers) {

            emails.add(mongoUser.getEmail());
        }

        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("/Users/jacopo/IdeaProjects/large-scale-project/password.json"));

            for (JSONObject object : (Iterable<JSONObject>) jsonArray) {
                passwordMap.put(object.get("email").toString(), object.get("password").toString());
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        for (String email: emails) {

            passwords.add(new String[] {email, passwordMap.get(email)});
        }

        File csvOutputFile = new File(CSV_FILE_NAME_PASSWORDS);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            passwords.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
