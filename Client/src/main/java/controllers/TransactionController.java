package controllers;

import models.Id;
import models.Message;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController m, IdController j) {
        msgCtrl = m;
        idCtrl = j;
    }

    public List<Id> getIds() {
        return idCtrl.getIds();
    }

    public String getId(String idFetch) {
        ArrayList<Id> allIdsList = idCtrl.getIds();
        for (Id mid : allIdsList) {
            if (mid.getGithub().equals(idFetch)) {
                System.out.println("ID = Name: " + mid.getName() + "  GitHub handle: " + mid.getGithub());
                return mid.getName();
            }
        }
        return "Please use a registered Github handle";
    }

    public String putId(String gitID, String newName) {
        ArrayList<Id> allIdsList = idCtrl.getIds();
        Id idToFind = null;
        for (Id mid : allIdsList) {
            if (mid.getGithub().equals(gitID)) {
                System.out.println("✅ Name Found!");
                idToFind = mid;
                break;
            }
        }
        idCtrl.putId(idToFind, newName);
        System.out.println("Name changed = " + newName + " for provided Github handle: " + gitID);
        return newName;
    }

//    public String deleteId(String id) {
//        return null;
//    }

    public String postId(String idToRegister, String name, String githubName) {
        Id tid = new Id(idToRegister, name, githubName);
        idCtrl.postId(tid);
        return ("Id registered.");
    }

    public List<Message> getMessages() {
        return msgCtrl.getMessages();
    }

    public List<Message> getMessagesFromId(String github) {
        Id id = getIdByGithub(github);
        if (id != null) {
            return msgCtrl.getMessagesForId(id);
        }
        System.out.println("No ID found for github: " + github);
        return new ArrayList<>();
    }

    private Id getIdByGithub(String github) {
        List<Id> allIds = idCtrl.getIds();
        for (Id id : allIds) {
            if (id.getGithub().equals(github)) {
                return id;
            }
        }
        return null;
    }



    public Message getMessageForSequence(String seq) {
        return msgCtrl.getMessageForSequence(seq);
    }

    public List<Message> getMessagesFromFriend(String myGithub, String friendGithub) {
        Id myId = getIdByGithub(myGithub);
        Id friendId = getIdByGithub(friendGithub);
        if (myId != null && friendId != null) {
            return msgCtrl.getMessagesFromFriend(myId, friendId);
        }
        System.out.println("One or both IDs not found.");
        return new ArrayList<>();
    }






















    public String postMessage(String message, String fromID, String toID){
        Message msgTest = new Message(message,fromID,toID);
        msgCtrl.postMessage(fromID, toID, msgTest);
        return "Message posted.";
    }




    public String deleteId(String github) {
        boolean success = idCtrl.deleteId(github);
        if (success) {
            return "ID with github " + github + " successfully deleted.";
        } else {
            return "Failed to delete ID with github " + github + ".";
        }
    }



}
