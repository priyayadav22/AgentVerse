package com.agentVerse.agentVerse.Service;

import com.agentVerse.agentVerse.Model.Contents;
import com.agentVerse.agentVerse.Model.MessagePart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;

@Service
public class GeminiService {
    @Value("${gemini.api.key}")
    private String GEMINI_API_KEY;

    public String generateResponseFromGemini(String id, String question, String prompt, Contents conversationHistory) {
        String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Build conversation parts
        JSONArray partsArray = new JSONArray();

        // Add prompt first
        partsArray.put(new JSONObject().put("text", prompt));

        // Add previous conversation history
        for (MessagePart part : conversationHistory.getParts()) {
            partsArray.put(new JSONObject().put("text", part.getText()));
        }

        // Add current question
        partsArray.put(new JSONObject().put("text", question));

        // Create final request body
        JSONObject requestBodyJson = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts", partsArray)));

        System.out.println(requestBodyJson.toString(2)); // Pretty print JSON

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson.toString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL, requestEntity, String.class);

        return response.getBody();
    }

}

