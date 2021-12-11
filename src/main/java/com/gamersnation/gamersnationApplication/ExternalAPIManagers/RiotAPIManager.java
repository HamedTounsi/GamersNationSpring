package com.gamersnation.gamersnationApplication.ExternalAPIManagers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RiotAPIManager {

    public String httpRequestBySummonername(String summonerName){
        if (summonerName != null) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(
                                URI.create("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName))
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 OPR/80.0.4170.91")
                        .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("Origin", "https://developer.riotgames.com")
                        .header("X-Riot-Token", "RGAPI-232bfc90-0141-4d09-87cb-bd67015cd3d9")
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            }catch (IOException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return "Summonername not found!";
    }

    public String httpRequestByEncryptedSummonerID(String encryptedSummonerID){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + encryptedSummonerID))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 OPR/80.0.4170.91")
                    .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                    .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Origin", "https://developer.riotgames.com")
                    .header("X-Riot-Token", "RGAPI-232bfc90-0141-4d09-87cb-bd67015cd3d9")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Player not found!";
    }

    public String parsePuuid(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get("puuid").getAsString();
    }

    public int parseLvl(String jsonLine){
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get("summonerLevel").getAsInt();
    }

    public String parseEncryptedSummonerID(String jsonLine){
        JsonElement jsonElement=new JsonParser().parse(jsonLine);
        JsonObject jsonObject=jsonElement.getAsJsonObject();
        return jsonObject.get("id").getAsString();
    }

    public String parseRank(String jsonLine){
        try {
            JsonElement jsonElement = new JsonParser().parse(jsonLine);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            String tier = jsonObject.get("tier").getAsString();
            String rank = jsonObject.get("rank").getAsString();
            return tier + " " + rank;
        }catch (IndexOutOfBoundsException e){
            return "unranked";
        }
    }
}
