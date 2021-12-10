package com.gamersnation.gamersnationApplication.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Optional<Player> findPlayerByPuuid(String puuid);

    Optional<Player> findPlayerByName(String name);

    @Query(value = "SELECT name FROM Player")
    ArrayList<String> findALlPlayerNames();


}
