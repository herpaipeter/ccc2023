package hu.herpaipeter.common;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatcher {

    public static String findMatch(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.results().map(MatchResult::group).findFirst().orElse("");
    }

    public static List<String> findAllMatch(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.results().map(MatchResult::group).toList();
    }

}
