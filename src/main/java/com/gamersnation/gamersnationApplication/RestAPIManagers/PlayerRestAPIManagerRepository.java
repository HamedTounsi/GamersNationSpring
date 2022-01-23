package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface PlayerRestAPIManagerRepository extends JpaRepository<Player, String> {

    //Finder spiller i databasen ud fra puuid
    Optional<Player> findPlayerByPuuid(String puuid);

    //Finder spiller i databasen ud fra summonername
    Optional<Player> findPlayerBySummonerName(String name);

    //returnere alle spiller i databasen
    List<Player> findAll();

    //returnere alle spiller navne i databasen
    @Query(value = "SELECT summonerName FROM Player")
    ArrayList<String> findALlPlayerNames();

}
