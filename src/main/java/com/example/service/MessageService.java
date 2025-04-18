package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    public MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        Optional<Message> optionalMessage = messageRepository.findById(message.getPostedBy());
        if(optionalMessage.isPresent()){
            messageRepository.save(message);
            return message;
        }else{
            return null;
        }
    }

    public boolean deleteMessageByMessageId(int id){
        if(messageRepository.existsById(id)){
            messageRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public List<Message> getAllMessagesByUserId(int id){
        return messageRepository.findByPostedBy(id);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(int id){
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()){
            return message.get();
        }else{
            return null;
        }
    }

    public boolean updateMessage(int id, Message message){
        if(messageRepository.existsById(id)){
            Message existedMessage = messageRepository.findById(id).get();
            existedMessage.setMessageText(message.getMessageText());
            messageRepository.save(existedMessage);
            return true;
        }else{
            return false;
        }
    }
}
