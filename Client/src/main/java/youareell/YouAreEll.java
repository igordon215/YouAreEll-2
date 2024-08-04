package youareell;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import controllers.*;

public class YouAreEll {
    private TransactionController tt;

    public YouAreEll(TransactionController t) {
        this.tt = t;
    }

    public static void main(String[] args) {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
                new TransactionController(
                        new MessageController(ServerController.shared()),
                        new IdController(ServerController.shared())
                ));
    }

    public String get_ids() {
        List<models.Id> allIds = tt.getIds();
        StringBuilder sb = new StringBuilder();
        for (models.Id id : allIds) {
            sb.append(id.toString() + "\n");
        }
        return sb.toString();
    }

    public String get_messages() {
        List<models.Message> latestMessages = tt.getMessages();
        StringBuilder sb = new StringBuilder();
        for (models.Message msg : latestMessages) {
            sb.append(msg.toString() + "\n");
        }
        return sb.toString();
    }

    public String postId(String userId, String githubName, String name) {
        String createdId = tt.postId(userId, name, githubName);
        return createdId;
    }

    public String getId(String git) {
        String gettingId = tt.getId(git);
        return gettingId;
    }

    public String putId(String name, String newName) {
        String puttingId = tt.putId(name, newName);
        return puttingId;
    }

    public String postMessage(String body, String from, String to){
        String createdPost = tt.postMessage(body,from,to);
        return createdPost;
    }




}
