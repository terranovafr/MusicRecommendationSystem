package com.unipi.largescale.util;

import com.unipi.largescale.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilGUI {

    public static List<String> getQuestions(){
        List<String> questions = new ArrayList<>();
        questions.add("I am the life of the party.");
        questions.add("I don't talk a lot.");
        questions.add("I feel comfortable around people.");
        questions.add("I keep in the background.");
        questions.add("I start conversations.");
        questions.add("I have little to say.");
        questions.add("I talk to a lot of different people at parties.");
        questions.add("I don't like to draw attention to myself.");
        questions.add("I don't mind being the center of attention.");
        questions.add("I am quiet around strangers.");
        questions.add("I get stressed out easily.");
        questions.add("I am relaxed most of the time.");
        questions.add("I worry about things.");
        questions.add("I seldom feel blue.");
        questions.add("I am easily disturbed.");
        questions.add("I get upset easily.");
        questions.add("I change my mood a lot.");
        questions.add("I have frequent mood swings.");
        questions.add("I get irritated easily.");
        questions.add("I often feel blue.");
        questions.add("I feel little concern for others.");
        questions.add("I am interested in people.");
        questions.add("I insult people.");
        questions.add("I sympathize with others' feelings.");
        questions.add("I am not interested in other people's problems.");
        questions.add("I have a soft heart.");
        questions.add("I am not really interested in others.");
        questions.add("I take time out for others.");
        questions.add("I feel others' emotions.");
        questions.add("I make people feel at ease.");
        questions.add("I am always prepared.");
        questions.add("I leave my belongings around.");
        questions.add("I pay attention to details.");
        questions.add("I make a mess of things.");
        questions.add("I get chores done right away.");
        questions.add("I often forget to put things back in their proper place.");
        questions.add("I like order.");
        questions.add("I shirk my duties.");
        questions.add("I follow a schedule.");
        questions.add("I am exacting in my work.");
        questions.add("I have a rich vocabulary.");
        questions.add("I have difficulty understanding abstract ideas.");
        questions.add("I have a vivid imagination.");
        questions.add("I am not interested in abstract ideas.");
        questions.add("I have excellent ideas.");
        questions.add("I do not have a good imagination.");
        questions.add("I am quick to understand things.");
        questions.add("I use difficult words.");
        questions.add("I spend time reflecting on things.");
        questions.add("I am full of ideas.");
        return questions;
    }

    public static List<String> readListOfCountries(){
        String[] locales = Locale.getISOCountries();
        List<String> listCountries = new ArrayList<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            listCountries.add(obj.getDisplayCountry(Locale.ENGLISH));
        }
        return listCountries;
    }

    public static String getPersonalityDescription(User user){
        String description = "";
        if(user.getOpenness() > 3)
            description +=  "You are inventive and curious.\n";
        else description += "You are consistent and cautious.\n";
        if(user.getConscientiousness() > 3)
            description +=  "You are efficient and organized.\n";
        else description += "You are easy-going and laid back.\n";
        if(user.getExtraversion() > 3)
            description += "You are outgoing and energetic.\n";
        else description += "You are solitary and reserved.\n";
        if(user.getAgreeableness() > 3)
            description += "You are friendly and compassionate.\n";
        else
            description += "You are analytical and detached.\n";
        if(user.getNeuroticism() > 3)
            description += "You are sensitive and you tend to be nervous.";
        else
            description += "You are secure and stable.";
        return description;
    }

    public static boolean isNegativelyCorrelated(int index){
        ArrayList<Integer> negativelyCorrelated = new ArrayList<>(List.of(2, 4, 6, 8, 10, 12, 14,21,23,25,27,32,34,36,42,44,46));
        return negativelyCorrelated.contains(index);
    }
}
