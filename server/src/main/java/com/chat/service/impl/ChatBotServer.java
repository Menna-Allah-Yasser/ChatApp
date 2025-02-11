package com.chat.service.impl;

import com.chat.service.repository.ChatBotRepository;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatBotServer extends UnicastRemoteObject implements ChatBotRepository {

    private static ChatBotServer instance = null;
    private static final String API_KEY = "AIzaSyBg-rZpCelzqLFdos1IZV_vM8Y3_1Q79wY";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
    private OkHttpClient client;

    private ChatBotServer() throws RemoteException {
        super();
        client = new OkHttpClient();
    }

    public static synchronized ChatBotServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ChatBotServer();
        }
        return instance;
    }

    @Override
    public String getBotResponse(String userMessage){
        try {
            // Build the JSON payload
            JSONObject part = new JSONObject();
            part.put("text", userMessage);
            JSONArray parts = new JSONArray();
            parts.put(part);
            JSONObject content = new JSONObject();
            content.put("parts", parts);
            JSONArray contents = new JSONArray();
            contents.put(content);
            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("contents", contents);

            // Create the RequestBody using JSON payload and set media type.
            RequestBody requestBody = RequestBody.create(requestBodyJson.toString(), MediaType.get("application/json"));

            // Build the HTTP POST request.
            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            // Execute the request synchronously.
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray candidates = jsonResponse.getJSONArray("candidates");
                if (candidates.length() > 0) {
                    JSONObject firstCandidate = candidates.getJSONObject(0);
                    JSONObject contentObj = firstCandidate.getJSONObject("content");
                    JSONArray partsArray = contentObj.getJSONArray("parts");
                    if (partsArray.length() > 0) {
                        return partsArray.getJSONObject(0).getString("text");
                    }
                }
                return "No response available.";
            } else {
                return "Request failed with code: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling Gemini API", e);
        }
    }
}
