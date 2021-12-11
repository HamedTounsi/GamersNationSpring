package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.Player;
import com.gamersnation.gamersnationApplication.ApplicationMVC.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This is the API Layer tilføjet til et nyt class for sig selv test
@Configuration
@RestController
@RequestMapping(path = "api/v1/player")
public class RestAPIManager {
    private final PlayerService playerService;

    @Autowired
    public RestAPIManager(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayer() {
        return playerService.getPlayer();
    }

    @PostMapping
    public void addPlayer(@RequestBody Player player){
        playerService.addPlayer(player);
    }

    @Bean
    CommandLineRunner commandLineRunner(RestAPIManagerRepository repository) {
        return args -> {
            Player player = new Player(
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
            repository.save(player);
        };
    }

}
