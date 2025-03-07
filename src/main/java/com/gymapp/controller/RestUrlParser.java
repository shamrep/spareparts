package com.gymapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestUrlParser {

    public static class ParsedUrl {
        private final String basePath;
        private final List<String> pathVariables;
        private final Map<String, String> queryParams;

        public ParsedUrl(String basePath, List<String> pathVariables, Map<String, String> queryParams) {
            this.basePath = basePath;
            this.pathVariables = pathVariables;
            this.queryParams = queryParams;
        }

        public String getBasePath() {
            return basePath;
        }

        public List<String> getPathVariables() {
            return pathVariables;
        }

        public Map<String, String> getQueryParams() {
            return queryParams;
        }

        @Override
        public String toString() {
            return "ParsedUrl{" +
                   "basePath='" + basePath + '\'' +
                   ", pathVariables=" + pathVariables +
                   ", queryParams=" + queryParams +
                   '}';
        }
    }

    public static ParsedUrl parse(String url) {
        String[] urlParts = url.split("\\?", 2);

        // Extract base path
        String basePath = urlParts[0];

        // Extract path variables (numbers or words between slashes)
        List<String> pathVariables = new ArrayList<>();
        Matcher matcher = Pattern.compile("/(\\w+)").matcher(basePath);
        while (matcher.find()) {
            pathVariables.add(matcher.group(1));
        }

        // Extract query parameters
        Map<String, String> queryParams = new HashMap<>();
        if (urlParts.length > 1) {
            String[] queryPairs = urlParts[1].split("&");
            for (String pair : queryPairs) {
                String[] keyValue = pair.split("=", 2);
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                queryParams.put(key, value);
            }
        }

        return new ParsedUrl(basePath, pathVariables, queryParams);
    }
}
