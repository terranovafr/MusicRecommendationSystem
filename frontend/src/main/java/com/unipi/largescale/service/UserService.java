package com.unipi.largescale.service;

import com.unipi.largescale.API.API;
import com.unipi.largescale.entities.Survey;
import com.unipi.largescale.entities.User;
import com.unipi.largescale.entities.aggregations.Album;
import com.unipi.largescale.gui.FXMLHomeDocumentController;

import java.util.List;

import static com.unipi.largescale.API.API.getMostSimilarUser;
import static com.unipi.largescale.util.UtilGUI.isNegativelyCorrelated;

public class UserService {
    public static User user;
    public static double timeStart;
    private static final int[] answers = new int[50];
    private static final double[] times = new double[50];

    // method called each time a new question is answered;
    public static void saveAnswer(int index, int answer, double time) throws Exception {
        if(index < 50){
            answers[index] = answer;
            times[index] = time;
        }
        // last question
        if(index == 49){
            // flipping questions negatively correlated
            for(int i = 0; i < 50; i++){
                if(isNegativelyCorrelated(i+1)) {
                    switch(answers[i]) {
                        case 1 -> answers[i] = 5;
                        case 2 -> answers[i] = 4;
                        case 4 -> answers[i] = 2;
                        case 5 -> answers[i] = 1;
                    }
                }
            }
            double value = 0;
            double cumulativeTime = 0;
            for(int i = 0; i < 10; ++i){
                value += answers[i];
                cumulativeTime += times[i];
            }
            user.setExtraversion(value/10);
            value = 0;
            for(int i = 10; i < 20; ++i){
                value += answers[i];
                cumulativeTime += times[i];
            }
            user.setNeuroticism(value/10);
            value = 0;
            for(int i = 20; i < 30; ++i){
                value += answers[i];
                cumulativeTime += times[i];
            }
            user.setAgreeableness(value/10);
            value = 0;
            for(int i = 30; i < 40; ++i){
                value += answers[i];
                cumulativeTime += times[i];
            }
            user.setConscientiousness(value/10);
            value = 0;
            for(int i = 40; i < 50; ++i){
                value += answers[i];
                cumulativeTime += times[i];
            }
            user.setOpenness(value/10);
            user.setTimeSpent(cumulativeTime/60);
            System.out.println("Sending answers to the server");
            user = API.registerUser(user);
        }
    }

    public static int getClusterID(){
        return user.getCluster();
    }

    public static void logoutUser(){
        FXMLHomeDocumentController.admin = false;
        user = null;
    }

    public static List<Album> getClusterKHighestRatedAlbums(){
        List<Album> list = API.getClusterKHighestRatedAlbums(user.getCluster(), 1);
        assert list != null;
        return list;
    }

    public static void updateUserInfo(User newUser){
        newUser.setAgreeableness(user.getAgreeableness());
        newUser.setNeuroticism(user.getNeuroticism());
        newUser.setConscientiousness(user.getConscientiousness());
        newUser.setOpenness(user.getOpenness());
        newUser.setExtraversion(user.getExtraversion());
        newUser.setTimeSpent(user.getTimeSpent());
        newUser.setCluster(user.getCluster());
        newUser.setId(user.getId());
        API.updateUserInfo(newUser);
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setFullName(newUser.getFullName());
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setCountry(newUser.getCountry());
        user.setGender(newUser.getGender());
        user.setDateOfBirth(newUser.getDateOfBirth());
        user.setImage(newUser.getImage());
    }

    public static double[] getClusterPersonalityValues(){
        Survey clusterValues = API.getClusterValues(user.getCluster());
        if(clusterValues != null)
            return new double[]{clusterValues.getOpenness(),clusterValues.getAgreeableness(),clusterValues.getNeuroticism(),clusterValues.getExtraversion(),clusterValues.getConscientiousness(),clusterValues.getTimeSpent()};
        else return null;
    }

    public static double[] getClusterPersonalityValues(int cluster){
        Survey clusterValues = API.getClusterValues(cluster);
        if(clusterValues != null)
            return new double[]{clusterValues.getOpenness(),clusterValues.getAgreeableness(),clusterValues.getNeuroticism(),clusterValues.getExtraversion(),clusterValues.getConscientiousness(),clusterValues.getTimeSpent()};
        else return null;
    }

    public static String getDeviation(){
        Survey clusterValues= API.getClusterValues(user.getCluster());
        if(clusterValues != null) {
            double diffC = Math.abs(user.getConscientiousness() - clusterValues.getConscientiousness());
            double diffN = Math.abs(user.getNeuroticism() - clusterValues.getNeuroticism());
            double diffE = Math.abs(user.getExtraversion() - clusterValues.getExtraversion());
            double diffA = Math.abs(user.getAgreeableness() - clusterValues.getAgreeableness());
            double diffO = Math.abs(user.getOpenness() - clusterValues.getOpenness());
            double deviation = diffC + diffN + diffE + diffA + diffO;
            return String.format("%.02f", (deviation / 5) * 100);
        } else return "0";
    }

    public static String getDeviationNN(){
        User NN = getMostSimilarUser(user);
        if(NN == null)
            return null;
        double diffC = (user.getConscientiousness() - NN.getConscientiousness());
        double diffN = (user.getNeuroticism() - NN.getNeuroticism());
        double diffE = (user.getExtraversion() - NN.getExtraversion());
        double diffA = (user.getAgreeableness() - NN.getAgreeableness());
        double diffO = (user.getOpenness() - NN.getOpenness());
        double deviation = Math.sqrt(diffC*diffC + diffN*diffN + diffE*diffE + diffA*diffA + diffO*diffO);
        return String.format("%.02f",(deviation/5)*100);
    }

    public static List<User> getFriendshipRequests(){
        return API.getFriendRequests(user);
    }

    public static double[] getPersonalityValues(){
        return new double[]{user.getOpenness(),user.getAgreeableness(),user.getNeuroticism(),user.getExtraversion(),user.getConscientiousness(),user.getTimeSpent()};
    }

    public static List<User> getSimilarUsers(){
        return API.getSimilarUsers(user);
    }

    public static List<User> getUsersByUsername(String username){
        return API.searchUserByUsername(username);
    }

    public static void deleteUser(User user){
        API.deleteUser(user);
    }

    public static void quarantineUser(User user){
        API.quarantineUser(user);
    }

    public static List<User> getFriendships(){
        return API.getFriendships(user);
    }

    public static User getUserInfo(String id){
        return API.getUserInfo(id);
    }

    public static void loginUser(String loginEmail, String loginPassword) throws Exception {
        User userLogin = new User(loginEmail, loginPassword);
        user = API.loginUser(userLogin);
        assert user != null;
    }

    public static void addFriendRequest(String receiverId){
        API.addFriendRequest(user, receiverId);
    }

    public static void deleteFriendRequest(User receiver){
        API.updateFriendRequest(receiver, user, 0);
    }

    public static void acceptFriendRequest(User receiver){
        API.updateFriendRequest(user, receiver, 1);
    }

    public static List<User> getNearbyUsers(){
        return API.getNearbyUsers(user);
    }

    public static void checkForNewRecommended(){
        API.checkForUpdates(user);
    }
}
