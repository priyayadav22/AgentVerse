//package com.agentVerse.agentVerse.Controller;
//
//import com.agentVerse.agentVerse.Repository.MessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.*;
//import com.agentVerse.agentVerse.Model.Message;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/messages")
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @PostMapping
//    public ResponseEntity<String> saveMessage(@RequestBody Message message){
//        messageRepository.save(message);
//        return new ResponseEntity<>("Message Saved", HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Message>> getMessageHistory() {
//        List<Message> messages = messageRepository.findAll();
//        return new ResponseEntity<>(messages, HttpStatus.OK);
//    }
//
//}
