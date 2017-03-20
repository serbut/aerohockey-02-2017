package com.aerohockey.controller;

import com.aerohockey.model.UserProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by sergey on 20.03.17.
 */
public class Responses {
    static String errorResponse(String errorMsg) {
        final JSONObject userDetailsJson = new JSONObject();
        userDetailsJson.put("error", errorMsg);
        return userDetailsJson.toJSONString();
    }

    static JSONObject userResponse(UserProfile userProfile) {
        final JSONObject userDetailsJson = new JSONObject();
        userDetailsJson.put("id", userProfile.getId());
        userDetailsJson.put("login", userProfile.getLogin());
        userDetailsJson.put("email", userProfile.getEmail());
        userDetailsJson.put("rating", userProfile.getRating());
        return userDetailsJson;
    }

    static JSONArray leaderboardResponse(List<UserProfile> users) {
        final JSONArray jsonArray = new JSONArray();
        for (UserProfile u : users) {
            jsonArray.add(userResponse(u));
        }
        return jsonArray;
    }
}
