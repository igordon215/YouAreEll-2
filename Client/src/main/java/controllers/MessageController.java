package controllers;

import java.util.*;

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
//    public ArrayList<Message> getMessages() {
//       String jsonInput = sc.getMessages();
//        // convert json to array of Ids
//        ObjectMapper mapper = new ObjectMapper();
//        List<Message> msgs;
//        try {
//            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));
//
//            ArrayList<Message> msgList = new ArrayList<>(msgs);
//            // return array of Ids
//            return msgList;
//        } catch (JsonMappingException e) {
//            System.out.println("Error processing JSON from response: " + e.getMessage());
//        } catch (JsonProcessingException e) {
//            System.out.println("Error processing JSON from response: " + e.getMessage());
//        }
//        return null;
//    }



    public ArrayList<Message> getMessages() {
        String jsonInput = sc.getMessages();
        try {
            return Message.fromJsonArray(jsonInput);
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return new ArrayList<>();
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
        String jsonInput = sc.getMessages();
        try {
            ArrayList<Message> messages = Message.fromJsonArray(jsonInput);
            for (Message msg : messages) {
                if (msg.getSequence().equals(seq)) {
                    return msg;
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON: " + e.getMessage());
        }
        return null;
    }







//    public Message getMessageForSequence(String seq) {
//        String jsonInput = sc.sendRequest("/messages/" + seq, "GET", "");
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.readValue(jsonInput, Message.class);
//        } catch (JsonProcessingException e) {
//            System.out.println("Error processing JSON from response: " + e.getMessage());
//        }
//        return null;
//    }







    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        String jsonInput = sc.sendRequest("/ids/" + myId.getGithub() + "/from/" + friendId.getGithub(), "GET", "");
        ObjectMapper mapper = new ObjectMapper();
        List<Message> msgs;
        try {
            msgs = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Message.class));
            return new ArrayList<>(msgs);
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return new ArrayList<>();
    }




    public Message postMessage(String myId, String toId, Message msg) {
        ObjectMapper om = new ObjectMapper();
        try {
            msg.setFromid(myId);
            msg.setToid(toId);
            msg.setTimestamp(null);// null timestamp/ msg's were not posting because of expected format

            String jsonBody = om.writeValueAsString(msg);
            System.out.println("Sending to server: " + jsonBody);

            String response = sc.sendRequest("/ids/" + myId + "/messages", "POST", jsonBody);
            System.out.println("Server response: " + response);

            if (response.contains("error")) {
                System.out.println("Failed to post message. Server response: " + response);
                return null;
            }

            return om.readValue(response, Message.class);
        } catch (JsonProcessingException exception) {
            System.out.println("Error processing message: " + exception.getMessage());
            return null;
        }
    }
 
}