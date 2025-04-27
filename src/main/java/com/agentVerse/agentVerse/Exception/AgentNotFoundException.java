package com.agentVerse.agentVerse.Exception;

public class AgentNotFoundException extends  RuntimeException{

    public AgentNotFoundException(String id){
        super("Agent not found with id: " + id);
    }

}
