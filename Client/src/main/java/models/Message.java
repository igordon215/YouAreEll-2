package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/*
 * POJO for an Message object
 *
 *   {
    "sequence": "-",
    "timestamp": "_",
    "fromid": "xt0fer",
    "toid": "kristofer",
    "message": "Hello, Kristofer!"
  },

*
 */
public class
Message implements Comparable<Message> {
    // sample from server
    // {"sequence":"ea9ccec875bbbbdcca464eb59718ae7cba9def95","timestamp":"2023-08-06T18:45:21.083445025Z",
    // "fromid":"xt0fer","toid":"torvalds","message":"Can you hear me now?!"}
    private String message = "";
    private String toid = "";
    private String fromid = "";
    private String timestamp = "";
    private String sequence = "";

    public Message() {
    }
    public Message (String message, String fromId, String toId, String timestamp, String sequence) {
        this.message = message;
        this.fromid = fromId;
        this.toid = toId;
        this.timestamp = timestamp;
        this.sequence = sequence;
    }
    
    public Message (String message, String fromId, String toId) {
        this.message = message;
        this.fromid = fromId;
        this.toid = toId;
    }

    public Message (String message, String fromId) {
        this.message = message;
        this.fromid = fromId;
        this.toid = "";
    }

    public static Message fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Message.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static ArrayList<Message> fromJsonArray(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Message.class));
    }

//    @Override
//    public String toString() {
//        return "to: " + this.toid + "\nfrom: "+ this.fromid + "\n" + this.message + "\n----\n";
//    }

//    @Override
//    public String toString() {
//        return "Sequence: " + this.sequence +
//                "\nTimestamp: " + this.timestamp +
//                "\nFrom: " + this.fromid +
//                "\nTo: " + this.toid +
//                "\nMessage: " + this.message +
//                "\n----\n";
//    }


    @Override
    public String toString() {
        return "Sequence: " + this.sequence +
                "\nTimestamp: " + formatTimestamp() +
                "\nFrom: " + this.fromid +
                "\nTo: " + this.toid +
                "\nMessage: " + this.message +
                "\n----\n";
    }


    @Override
    public int compareTo(Message o) {
        return this.sequence.compareTo(((Message) o).getSequence());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toId) {
        this.toid = toId;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromId) {
        this.fromid = fromId;
    }

    public String getTimestamp() {
        return formatTimestamp();
    }

    public String getSequence() {
        return sequence;
    }



    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }


    private String formatTimestamp() {
        if (timestamp == null || timestamp.isEmpty()) {
            return "N/A";
        }
        // Remove the 'T', and keep only year, month, day, hour, and minute
        String[] parts = timestamp.split("T");
        if (parts.length == 2) {
            String date = parts[0];
            String time = parts[1].substring(0, 5);  //  HH:MM
            return date + " " + time;
        }
        return timestamp;
    }


}