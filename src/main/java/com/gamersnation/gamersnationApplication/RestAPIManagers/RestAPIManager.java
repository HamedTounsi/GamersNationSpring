package com.gamersnation.gamersnationApplication.RestAPIManagers;

import com.gamersnation.gamersnationApplication.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Configuration
@RestController
@RequestMapping(path = "api/v1/player")
public class RestAPIManager {
    private final RestAPIManagerRepository restAPIManagerRepository;

    @Autowired //Forbinder RestAPIManager og RestAPIManagerRepository
    public RestAPIManager(RestAPIManagerRepository restAPIManagerRepository) {
        this.restAPIManagerRepository = restAPIManagerRepository;
    }

    @PostMapping(value = "/addPlayer", consumes = "application/json", produces = "application/json")
    public void addPlayer(@RequestBody Player player) {
        restAPIManagerRepository.save(player);
    }

    //Creates Dummy Profiles in the database
    @Bean
    CommandLineRunner saveDummyProfiles(){
        return args -> {
            Player player1 = new Player("01","HakunaMatata", 50, "Gold I", true, 6, 10, true, "Jungle");
            Player player2 = new Player("02","DanTheMan", 115, "Diamond III", true, 2, 10, false, "Top");
            Player player3 = new Player("03","MPRuler", 11, "unranked", false, 2, 4, true, "Mid");
            Player player4 = new Player("04","MUPlayer", 20, "Unranked", false, 10, 6, true, "Support");
            Player player5 = new Player("05","KushMaximumDamage", 4, "Unranked", false, 3, 3, true, "Bot");
            Player player6 = new Player("06","DaddyGoku", 67, "Silver IV", true, 10, 8, true, "Jungle");
            Player player7 = new Player("07","Fransisco", 92, "Platinum I", false, 6, 6, true, "Jungle");
            Player player8 = new Player("08","Tammyrex", 41, "Iron I", false, 5, 8, true, "Top");
            Player player9 = new Player("09","FunkCanyons", 168, "Master II", true, 2, 10, true, "Jungle");
            Player player10 = new Player("10","SleekDaily", 10, "Unranked", false, 7, 8, true, "Bot");
            Player player11 = new Player("11","Nozymo", 194, "Diamond II", true, 4, 9, true, "Mid");
            Player player12 = new Player("12","BanditGutsy", 35, "Bronze II", false, 6, 7, false, "Top");
            Player player13 = new Player("13","Relaxlers", 68, "Gold II", false, 8, 6, true, "Support");
            Player player14 = new Player("14","RapCanyons", 97, "Unranked", true, 5, 10, true, "Mid");
            Player player15 = new Player("15","ManSucker", 30, "Unranked", true, 9, 10, true, "Bot");
            Player player16 = new Player("16","Mafiast", 196, "Grandmaster III", true, 2, 10, true, "Jungle");
            Player player17 = new Player("17","Rainbowanne", 122, "Silver II", false, 7, 7, true, "Top");
            Player player18 = new Player("18","BuffDelight", 64, "Platinum IV", true, 5, 5, false, "Mid");
            Player player19 = new Player("19","ShadesDarth", 166, "Iron III", false, 9, 7, true, "Support");
            Player player20 = new Player("20","BeediAbdi", 82, "Gold IV", true, 8, 7, true, "Support");
            restAPIManagerRepository.saveAll(List.of(player1,player2,player3,player4,player5,player6,player7,player8,player9,player10,player11,player12,player13,player14,player15,player16,player17,player18,player19,player20));
        };
    }

    //Returns a List of all the players in the database
    public List<Player> getPlayerList(){
        List<Player> playerList = restAPIManagerRepository.findAll();
        return playerList;
    }

}
