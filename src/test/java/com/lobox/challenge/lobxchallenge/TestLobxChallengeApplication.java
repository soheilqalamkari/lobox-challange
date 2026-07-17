package com.lobox.challenge.lobxchallenge;

import org.springframework.boot.SpringApplication;

public class TestLobxChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.from(LobxChallengeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
