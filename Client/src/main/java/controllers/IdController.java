package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Id;

public class IdController {
    ServerController sc;
    private HashMap<String, Id> allIds;
    private static ObjectMapper objectMapper = new ObjectMapper();
    Id myId;

    public IdController(ServerController shared) {
        sc = shared;
        allIds = new HashMap<String, Id>();
    }

    public ArrayList<Id> getIds() {
        String jsonInput = sc.getIds();
        // convert json to array of Ids
        ObjectMapper mapper = new ObjectMapper();
        List<Id> ids;
        try {
            ids = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Id.class));

            ArrayList<Id> idList = new ArrayList<>(ids);
            // return array of Ids
            return idList;
        } catch (JsonMappingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from response: " + e.getMessage());
        }
        return null;
    }

    public Id postId(Id id) {
        // create json from id
        // call server, get json result Or error
        // result json to Id obj

        return null;
    }

    public boolean putId(String github, String newName) {
        try {
            String jsonBody = String.format("{\"github\":\"%s\",\"name\":\"%s\"}", github, newName);
            String response = sc.sendRequest("/ids", "POST", jsonBody);
            System.out.println("Server response: " + response);
            return !response.contains("Error") && !response.contains("error");
        } catch (Exception e) {
            System.out.println("Error updating name: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteId(String github) {
        try {
            String response = sc.sendRequest("/ids/" + github, "DELETE", "");
            return response.contains("success") || response.isEmpty();
        } catch (Exception e) {
            System.out.println("Error deleting ID: " + e.getMessage());
            return false;
        }
    }



}