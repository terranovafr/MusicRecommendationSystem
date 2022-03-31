package com.unipi.large.scale.backend.service;

import com.unipi.large.scale.backend.data.Survey;
import com.unipi.large.scale.backend.entities.mongodb.MongoUser;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

@Service
public class Utils {

    public double getDistance(Survey fromSurvey, Survey toSurvey) {

        return Math.sqrt(Math.pow(fromSurvey.getExtraversion() - toSurvey.getExtraversion(), 2) +
                        Math.pow(fromSurvey.getAgreeableness() - toSurvey.getAgreeableness(), 2) +
                        Math.pow(fromSurvey.getConscientiousness() - toSurvey.getConscientiousness(), 2) +
                        Math.pow(fromSurvey.getNeuroticism() - toSurvey.getNeuroticism(), 2) +
                        Math.pow(fromSurvey.getOpenness() - toSurvey.getOpenness(), 2) +
                        Math.pow(fromSurvey.getTimeSpent() - toSurvey.getTimeSpent(), 2));
    }

    public String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {

            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    public boolean areSurveyValuesCorrect(MongoUser mongoUser) {

        return mongoUser.getExtraversion() != 0 && mongoUser.getAgreeableness() != 0 && mongoUser.getConscientiousness() != 0 && mongoUser.getNeuroticism() != 0 && mongoUser.getOpenness() != 0 && mongoUser.getTimeSpent() != 0;
    }
}
