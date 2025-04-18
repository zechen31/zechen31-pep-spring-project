package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account registeredAccount = accountService.register(account);
        if(registeredAccount != null){
            return ResponseEntity.ok(registeredAccount);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loginInformation = accountService.login(account);
        if(loginInformation != null){
            return ResponseEntity.ok(loginInformation);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        int messageLength = message.getMessageText().length();
        if( messageLength == 0 || messageLength > 254 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            return ResponseEntity.ok(createdMessage);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int id){
        boolean rowModified = messageService.deleteMessageByMessageId(id);
        if (rowModified){
            return ResponseEntity.ok(1);
        }else{
            return ResponseEntity.ok(null);
        }
        
    }
    @GetMapping("/accounts/{id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable int id){
        List<Message> messages = messageService.getAllMessagesByUserId(id);
        return ResponseEntity.ok(messages);

    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable int id){
        Message message = messageService.getMessageByMessageId(id);
        return ResponseEntity.ok(message);
        
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int id, @RequestBody Message message){
        int messageLength = message.getMessageText().length();
        if( messageLength == 0 || messageLength > 255 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }else{
            boolean rowAffected = messageService.updateMessage(id, message);
            if(rowAffected){
                return ResponseEntity.ok(1);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
    }
}
