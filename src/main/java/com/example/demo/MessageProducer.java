package com.example.demo;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public String sendMessage(@RequestParam(value = "message", defaultValue = "") String message) {
        kafkaTemplate.send("quickstart-events", message);
        return "Message '" + message + "' sent.";
    }

    @PostMapping(path = "/wahllokal", produces = "text/plain")
    public String wahllokal(@RequestParam(value = "wahllokal", defaultValue = "2402") String wahllokal,
                            @RequestBody Votes newVotes) {
        kafkaTemplate.send("wahl.lokal." + wahllokal, newVotes.toJson());
        return "Message '" + newVotes.toJson() + "' sent to Wahllokal " + wahllokal + ".";
    }

}