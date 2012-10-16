package com.netflix.frigga.ami;

import com.netflix.frigga.NameConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppVersion implements Comparable {

    /**
     * All of these are valid:
     * subscriberha-1.0.0-586499
     * subscriberha-1.0.0-586499.h150
     * subscriberha-1.0.0-586499.h150/WE-WAPP-subscriberha/150
     */
    private static final Pattern APP_VERSION_PATTERN = Pattern.compile(
            "([" + NameConstants.NAME_HYPHEN_CHARS +
            "]+)-([0-9.]+)-([0-9]{5,7})(?:[.]h([0-9]+))?(?:\\/([-a-zA-z0-9]+)\\/([0-9]+))?");


    private String packageName;
    private String version;
    private String buildJobName;
    private String buildNumber;
    private String changelist;

    private AppVersion() {
    }

    public static AppVersion parseName(String amiName) {
        if (amiName == null) {
            return null;
        }
        Matcher matcher = APP_VERSION_PATTERN.matcher(amiName);
        if (!matcher.matches()) {
            return null;
        }
        AppVersion parsedName = new AppVersion();
        parsedName.packageName = matcher.group(1);
        parsedName.version = matcher.group(2);
        parsedName.changelist = matcher.group(3);
        parsedName.buildNumber = matcher.group(4);
        parsedName.buildJobName = matcher.group(5);
        return parsedName;
    }

    public int compareTo(Object o) {
        if (equals (o)) { // if x.equals(y), then x.compareTo(y) should be 0
            return 0;
        }

        if (o == null) {
            return 1; // equals(null) can never be true, so compareTo(null) should never be 0
        }

        AppVersion other = (AppVersion) o; // ClassCastException is the desired result here

        int comparison = packageName.compareTo(other.packageName);
        if (comparison != 0) {
            return comparison;
        }
        comparison = version.compareTo(other.version);
        if (comparison != 0) {
            return comparison;
        }
        comparison = buildJobName.compareTo(other.buildJobName);
        if (comparison != 0) {
            return comparison;
        }
        comparison = buildNumber.compareTo(other.buildNumber);
        if (comparison != 0) {
            return comparison;
        }
        comparison = changelist.compareTo(other.changelist);
        return comparison;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AmiName [packageName=").append(packageName).append(", version=").append(version)
                .append(", buildJobName=").append(buildJobName).append(", buildNumber=").append(buildNumber)
                .append(", changelist=").append(changelist).append("]");
        return builder.toString();
    }

    public static Pattern getAppVersionPattern() {
        return APP_VERSION_PATTERN;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildJobName() {
        return buildJobName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getChangelist() {
        return changelist;
    }

}
