package ru.snow4dv.GitUnits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitUser {
    private String name, email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public GitUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * This constructor takes the git commit string as argument
     * Format: (author|commiter) name <email> timestamp timezone
     * @param gitObjectString String that was taken from the commit git object
     */
    public GitUser(String gitObjectString) {
        String[] description = gitObjectString.split(" ");
        Pattern userStringPattern = Pattern.compile("(.*) <(.*@.*\\..*)> [0-9]* ([+\\-=])?([0-9]{4})");
        Matcher userStringMatcher = userStringPattern.matcher(gitObjectString);
        userStringMatcher.matches();
        this.name = userStringMatcher.group(1);
        this.email = userStringMatcher.group(2);
    }
}
