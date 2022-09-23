package com.lunatech.imdb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFormat {

    public static final String NUMBERS = "^[0-9]+$";
    public static final String ALPHABETS = "^[ A-Za-z]+$";
    public static final String EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String ALPHANUMERIC = "^[a-zA-Z0-9]*$";
    public static final String ALPHANUMERIC_WITH_SPECIAL_XTERS = "^[a-zA-Z0-9_-]+$"; // allows alphanumeric plus xters like _ and -
    public static final String URL = "((http|https)://)(www.)?"
            + "[a-zA-Z0-9@:%._\\-+~#?&//=]"
            + "{2,256}\\.[a-z]"
            + "{2,6}\\b([-a-zA-Z0-9@:%"
            + "._\\+~#?&//=]*)";

    public static boolean onlyNumbers(String data) {
        return data != null && data.matches(PatternFormat.NUMBERS);
    }

    public static boolean onlyAlphabets(String data) {
        return data != null && data.matches(PatternFormat.ALPHABETS) && data.length() >= 2;
    }

    public static boolean regexCheck(String regex, String data) {
        return data.matches(regex);
    }

    public static boolean containsKeyWord(String keyWord, String data) {
        Pattern pattern = Pattern.compile(keyWord, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }
}
