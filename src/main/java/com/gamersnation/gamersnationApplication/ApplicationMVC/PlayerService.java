package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.RestAPIManagers.RestAPIManagerRepository;
import com.gamersnation.gamersnationApplication.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final RestAPIManagerRepository APIRepository;

    @Autowired
    public PlayerService(RestAPIManagerRepository APIRepository) {
        this.APIRepository = APIRepository;
    }

    public List<Player> getPlayer() {
        return APIRepository.findAll();
    }

    // Adds a given player to gamersnationdb if the player.name doesn't already exists in the database
    public void addPlayer(Player player) {
        Optional<Player> playerName = APIRepository.findPlayerByName(player.getName());
        if (playerName.isPresent()){
            throw new IllegalStateException("Name has already been taken");
        } else {
            APIRepository.save(player);
        }
    }

    public ArrayList<String> getPlayers(){
        ArrayList<String> playerNames = APIRepository.findALlPlayerNames();
        return playerNames;
    }
}
