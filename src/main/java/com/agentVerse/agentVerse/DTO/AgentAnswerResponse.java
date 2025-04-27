package com.agentVerse.agentVerse.DTO;

import com.fasterxml.jackson.databind.JsonNode;

public class AgentAnswerResponse {

    private String agentname;
    private String question;
    private JsonNode reply;

    public AgentAnswerResponse(String agentname, String question, JsonNode reply) {
        this.agentname = agentname;
        this.question = question;
        this.reply = reply;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public JsonNode getReply() {
        return reply;
    }

    public void setReply(JsonNode reply) {
        this.reply = reply;
    }
}
