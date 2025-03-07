package com.gymapp.controller;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathParser {
    public static String extractId(String path, String route) {
        // Convert the route template to a regex pattern
        String regex = route.replace("{id}", "(\\d+)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            return matcher.group(1); // Extract the first capture group (id)
        }

        throw new IllegalArgumentException("Path does not match the route");
    }

    public static String transformToTemplate(String path, Set<String> paths) {
        // Define a regex pattern for numeric segments (or customize for other patterns)
        Pattern pattern = Pattern.compile("/\\d+");
        return pattern.matcher(path).replaceAll("/{id}");
    }


}
