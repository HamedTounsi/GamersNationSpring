package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

//This is the API Layer tilføjet til et nyt class for sig selv test
@Configuration
@RestController
@RequestMapping(path = "api/v1/player")
public class RestAPIManager {
    private final RestAPIManagerRepository restAPIManagerRepository;

    @Autowired
    public RestAPIManager(RestAPIManagerRepository restAPIManagerRepository) {
        this.restAPIManagerRepository = restAPIManagerRepository;
    }

    /*
    @GetMapping
    public List<Player> getPlayer() {
        return applicationModel.getPlayer();
    }*/

    @PostMapping(value = "/addPlayer", consumes = "application/json", produces = "application/json")
    public void addPlayer(@RequestBody Player player) {
        restAPIManagerRepository.save(player);
    }

}
