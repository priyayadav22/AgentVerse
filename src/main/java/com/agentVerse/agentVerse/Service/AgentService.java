package com.agentVerse.agentVerse.Service;

import com.agentVerse.agentVerse.Constants.InitialPromptConstants;
import com.agentVerse.agentVerse.DTO.AgentAnswerResponse;
import com.agentVerse.agentVerse.Exception.AgentNotFoundException;
import com.agentVerse.agentVerse.Model.Contents;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import com.agentVerse.agentVerse.Model.Agent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentService {
    private List<Agent> agents = new ArrayList<>();
    private final GeminiService geminiService;

    public AgentService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Agent>> typeReference = new TypeReference<List<Agent>>() {
        };
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Constants/agents.json");
        try {
            agents = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Agent> getAllAgents() {
        return agents;
    }

    public Agent getAgentById(String id) {
        return agents.stream()
                .filter(agent -> agent.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AgentNotFoundException(id));
    }

    public AgentAnswerResponse getResponseFromAgent(String id, String question, Contents conversationHistory) {
        Agent agent = getAgentById(id);
        String prompt = InitialPromptConstants.getPromptById(id);

        String geminiResponse = geminiService.generateResponseFromGemini(id, question, prompt, conversationHistory);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode answer = null;
        try {
            answer = mapper.readTree(geminiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new AgentAnswerResponse(agent.getName(), question, answer);
    }

}
