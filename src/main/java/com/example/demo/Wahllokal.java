package com.example.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Wahllokal {
    private int wahllokal;
    private int oevp;
    private int spoe;
    private int fpoe;
    private int gruene;
    private int neos;
    private int kpoe;

    public Wahllokal(int wahllokal) {
        this.wahllokal = wahllokal;
        this.oevp = 0;
        this.spoe = 0;
        this.fpoe = 0;
        this.gruene = 0;
        this.neos = 0;
        this.kpoe = 0;
    }

    private void genRandomVotes() {
        Random rand = new Random();
        this.oevp = rand.nextInt(100);
        this.spoe = rand.nextInt(100);
        this.fpoe = rand.nextInt(100);
        this.gruene = rand.nextInt(100);
        this.neos = rand.nextInt(100);
        this.kpoe = rand.nextInt(100);
    }

    public void sendData() {
        genRandomVotes();
        Votes votes = new Votes(this.oevp, this.spoe, this.fpoe, this.gruene, this.neos, this.kpoe);
        // post request to /wahllokal with votes as body
        String jsonInputString = votes.toJson();
        try {
            HttpURLConnection conn = getConnection(jsonInputString);
            int code = conn.getResponseCode();
            System.out.println("Response Code: " + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection getConnection(String jsonInputString) throws IOException {
        URL url = new URL("http://localhost:8080/wahllokal?wahllokal=" + this.wahllokal);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "text/plain");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return conn;
    }
}
