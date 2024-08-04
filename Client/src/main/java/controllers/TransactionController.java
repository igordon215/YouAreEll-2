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
        //find the correct id via arrayID loop
        ArrayList<Id> allIdsList = idCtrl.getIds();
        Id idToFind = null;
        for (Id mid : allIdsList) {
            if (mid.getGithub().equals(gitID)) {
                System.out.println("I found it!");
                idToFind = mid;
                break;
            }
        }
        //new name set
        idCtrl.putId(idToFind, newName);
        //print name change
        System.out.println("Name changed = " + newName + " for provided Github handle: " + gitID);
        //id to be returned
        return newName;
    }

    public String deleteId(String id) {
        return null;
    }

    public String postId(String idToRegister, String name, String githubName) {
        Id tid = new Id(idToRegister, name, githubName);
        idCtrl.postId(tid);
        return ("Id registered.");
    }

    public List<Message> getMessages() {
        return msgCtrl.getMessages();
    }

    public List<Message> getMessagesFromId(Id id) {
        return msgCtrl.getMessagesForId(id);
    }

    public String postMessage(String message, String fromID, String toID){
        Message msgTest = new Message(message,fromID,toID);
        msgCtrl.postMessage(fromID, toID, msgTest);
        return "Message posted.";
    }
}
