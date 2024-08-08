package com.MariaEduardaWendelMaia.BallitChampionship.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MessageService {

    private final List<String> messages = Arrays.asList(
            "-> Ótimo trabalho! 💪",
            "-> Continue assim! 💖",
            "-> Você vai conseguir! 🚀",
            "-> Você foi muito bem! 🌟",
            "-> Você está indo muito bem! 👍",
            "-> Vai dar tudo certo! 💫",
            "-> Você está quase lá! 🏆",
            "-> Você é incrivel! 🤩"
    );

    private final Random random = new Random();

    public String getRandomMessage() {
        int index = random.nextInt(messages.size());
        return messages.get(index);
    }
}
