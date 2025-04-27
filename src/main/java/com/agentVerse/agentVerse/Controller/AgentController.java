package com.agentVerse.agentVerse.Controller;

import com.agentVerse.agentVerse.DTO.AgentAnswerResponse;
import com.agentVerse.agentVerse.Model.*;
import com.agentVerse.agentVerse.Service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping
    public List<Agent> getAllAgents(){
        return agentService.getAllAgents(); // this will return lst of agents
    }

    @GetMapping("/{id}")
    public Agent getAgentById(@PathVariable String id){
        return agentService.getAgentById(id);
    }

    @PostMapping("/{id}/ask")
    public AgentAnswerResponse getResponseFromAgent(@PathVariable String id, @RequestBody AgentRequest agentRequest){
//        String question = questionRequest.getQuestion();
        Contents contents = agentRequest.getContents();
        String question = null;

        if (contents != null && contents.getParts() != null && !contents.getParts().isEmpty()) {
            question = contents.getParts().get(contents.getParts().size() - 1).getText();
        }

        if (question == null) {
            throw new IllegalArgumentException("No question from user found in conversation history.");
        }

        System.out.println("Received Question: " + question);


        return agentService.getResponseFromAgent(id, question, contents);
    }


}
