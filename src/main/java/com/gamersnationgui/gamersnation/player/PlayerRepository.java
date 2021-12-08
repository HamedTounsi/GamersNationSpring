package com.gamersnationgui.gamersnation.player;

import com.gamersnationgui.gamersnation.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Optional<Player> findPlayerByPuuid(String puuid);

    Optional<Player> findPlayerByName(String name);

    @Query(value = "SELECT name FROM Player")
    ArrayList<String> findALlPlayerNames();


}
