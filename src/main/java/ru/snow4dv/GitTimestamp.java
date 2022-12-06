package ru.snow4dv;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitTimestamp {
    private final Timestamp timestamp;
    private final String timezone;


    /**
     * Creates timestamp object based on commit user action data
     * @param gitObjectString User action
     */
    public GitTimestamp(String gitObjectString) {
        Pattern userStringPattern = Pattern.compile("(.*) <(.*@.*\\..*)> ([0-9]*) ([+\\-=])?([0-9]{4})");
        Matcher userStringMatcher = userStringPattern.matcher(gitObjectString);
        userStringMatcher.matches();
        String timeString = userStringMatcher.group(3);
        this.timestamp = new Timestamp(Long.parseLong(timeString) * 1000);
        this.timezone = userStringMatcher.group(4) + userStringMatcher.group(5);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTimezone() {
        return timezone;
    }
}
