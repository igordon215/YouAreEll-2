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
        for (Id mid : allIdsList){
            if (mid.getGithub().equals(idFetch)){
                System.out.println("ID = Name: " + mid.getName() + "  GitHub handle: " + mid.getGithub() );
                return mid.getName();
            }
        }
        return "Please use a registered Github handle";
    }
    public String putId(String id) {
        return null;
    }

    public String deleteId(String id) {
        return null;
    }

    public String postId(String idToRegister, String name ,String githubName) {
        Id tid = new Id(idToRegister, name, githubName);
        idCtrl.postId(tid);
        return ("Id registered.");
    }

    public List<Message> getMessages() {
        return msgCtrl.getMessages();
    }
}
