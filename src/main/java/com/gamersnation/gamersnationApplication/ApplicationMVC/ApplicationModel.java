package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.RestAPIManagers.RestAPIManager;
import com.gamersnation.gamersnationApplication.RestAPIManagers.RestAPIManagerRepository;
import com.gamersnation.gamersnationApplication.player.Player;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.HashMap;

@Service
public class ApplicationModel {
    private final RestAPIManager restAPIManager;

    @Autowired
    public ApplicationModel(RestAPIManager restAPIManager) {
        this.restAPIManager = restAPIManager;
    }


    // Adds a given player to gamersnationdb if the player.name doesn't already exists in the database
    public void addPlayer(String puuid,String summonerName, int level, String rank, boolean mode, double tolerance, double commitment, boolean voiceChat, String position)
    throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject playerJsonObject = new JSONObject();
        try {
            playerJsonObject.put("puuid", puuid);
            playerJsonObject.put("summonerName", summonerName);
            playerJsonObject.put("level", level);
            playerJsonObject.put("rank", rank);
            playerJsonObject.put("mode", mode);
            playerJsonObject.put("tolerance", tolerance);
            playerJsonObject.put("commitment", commitment);
            playerJsonObject.put("voiceChat", voiceChat);
            playerJsonObject.put("position", position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<String>(playerJsonObject.toString(), headers);

        String result = restTemplate.postForObject("http://localhost:8080/api/v1/player/addPlayer", request, String.class);
    }


    /*public List<Player> getPlayer() {
        return APIRepository.findAll();
    }*/

    /*
    public ArrayList<String> getPlayers(){
        ArrayList<String> playerNames = APIRepository.findALlPlayerNames();
        return playerNames;
    }*/

    /*Optional<Player> playerName = APIRepository.findPlayerBySummonerName(player.getSummonerName());
        if (playerName.isPresent()){
            throw new IllegalStateException("Name has already been taken");
        } else {
            APIRepository.save(player);
        }*/
}
