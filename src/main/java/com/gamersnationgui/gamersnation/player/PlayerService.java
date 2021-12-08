package com.gamersnationgui.gamersnation.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayer() {
        return playerRepository.findAll();
    }

    // Adds a given player to gamersnationdb if the player.name doesn't already exists in the database
    public void addPlayer(Player player) {
        Optional<Player> playerName = playerRepository.findPlayerByName(player.getName());
        if (playerName.isPresent()){
            throw new IllegalStateException("Name has already been taken");
        } else {
            playerRepository.save(player);
        }
    }
}
