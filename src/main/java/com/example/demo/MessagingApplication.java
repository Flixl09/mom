package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingApplication.class, args);
		Wahllokal mae = new Wahllokal(2402);
		Wahllokal fisch = new Wahllokal(2401);
		Wahllokal hain = new Wahllokal(2410);
		while (true) {
			mae.sendData();
			fisch.sendData();
			hain.sendData();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
