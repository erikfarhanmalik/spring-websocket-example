package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OnStartRunner implements CommandLineRunner {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public void run(String... args) {
        for (int i = 0; i < 25; i++) {
            int sleepingTime = new Random().nextInt(10) * 1000;
            Greeting greeting = new Greeting("Hello, " + "OnStartRunner" + "! " + i + " ===> " + sleepingTime + "s");
            executor.submit(() -> {
                try {
                    Thread.sleep(sleepingTime); // simulated delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messagingTemplate.convertAndSend("/topic/greetings", greeting); // this will send message manually
            });
        }
    }
}
