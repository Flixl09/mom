package com.example.demo;

import org.apache.kafka.common.internals.Topic;
import org.json.JSONObject;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Component
@EnableKafka
public class MessageConsumer {

    private List<String> messages = new ArrayList<>();

    private Map<String, Integer> votes = new HashMap<>(Map.of("oevp", 0, "spoe", 0, "fpoe", 0, "gruene", 0, "neos", 0, "kpoe", 0));

    @KafkaListener(topics = "quickstart-events")
    public void processMessage(String content) {
        System.out.println( "Read from Message Queue: " + content);
    }

    @KafkaListener(topicPattern = "wahl.lokal.*", groupId = "vote_listener")
    public void sink(String data,
                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Received data from " + topic + ": " + data);
        messages.add(data + " from " + topic);
        JSONObject obj = new JSONObject(data);
        for (String party : obj.keySet()) {
            try {
                votes.put(party, votes.get(party) + obj.getInt(party));
            } catch (Exception e) {
                System.out.println(e.getClass());
                System.out.println(party);
            }
        }
        System.out.println(votes);
    }

    @GetMapping("/receivePlain")
    public String getAllmessages() {
        if (messages.isEmpty()) {
            return "No messages received yet.";
        }
        String s = "";
        for (String message : messages) {
            s += message + "\n";
        }
        return s;
    }

    @GetMapping(path = "/receive", produces = "application/json")
    public String getVotes() {
        if (votes.isEmpty()) {
            return "No votes received yet.";
        }
        JSONObject obj = new JSONObject(votes);
        return obj.toString();
    }

}