package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;
import models.Message;

public class MessageController {
    ServerController sc;

    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public MessageController(ServerController shared) {
        sc = shared;
        messagesSeen = new HashSet<Message>();
    }
    public ArrayList<Message> getMessages() {
       String jsonInput = sc.getMessages();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));

            ArrayList<Message> msgList = new ArrayList<>(msgs);
            // return array of Ids
            return msgList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }





    public ArrayList<Message> getMessagesForId(Id id) {
        String jsonInput = sc.sendRequest("/ids/" + id.getGithub() + "/messages", "GET", "");
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));
            ArrayList<Message> msgList = new ArrayList<>(msgs);
            return msgList;
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return new ArrayList<>();
    }












    public Message getMessageForSequence(String seq) {
        return null;
    }







    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return null;
    }




    public Message postMessage(String myId, String toId, Message msg) {
        ObjectMapper om = new ObjectMapper();
        try {
            msg.setFromid(myId);
            msg.setToid(toId);
            sc.sendRequest("/ids/xt0fer/messages","POST", om.writeValueAsString(msg));
            System.out.println(om.writeValueAsString(msg));
            return msg;
        } catch (JsonProcessingException exception) {
            System.out.println("Invalid Message");
            return null;
        }
    }
 
}