package com.gamersnationgui.gamersnation.player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlayerConfig {

    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository repository) {
        return args -> {
            Player hamed = new Player(
                    "Hamed",
                    "Hamedxoxo",
                    true,
                    1,
                    "Bronze",
                    1,
                    1,
                    1,
                    1
            );
            repository.save(hamed);
        };
    }
}
