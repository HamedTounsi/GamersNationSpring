package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.Optional;

@Component
@Repository
public interface RestAPIManagerRepository extends JpaRepository<Player, String> {

    Optional<Player> findPlayerByPuuid(String puuid);

    Optional<Player> findPlayerByName(String name);

    @Query(value = "SELECT name FROM Player")
    ArrayList<String> findALlPlayerNames();

}
