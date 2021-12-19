package com.gamersnation.gamersnationApplication.ExternalAPIManagers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RiotAPIManager {
    private final String APIKEY = "INSERT API KEY HERE";

    //Sender et HTTP GET Request til RIOT API med SummonerName
    public String httpRequestBySummonername(String summonerName){
        summonerName = summonerName.replace(" ",""); //Sletter/erstatter alle mellemrum
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(
                                URI.create("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName))
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 OPR/80.0.4170.91")
                        .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("Origin", "https://developer.riotgames.com")
                        .header("X-Riot-Token", APIKEY)
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            }catch (IOException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        return null;
    }
    //Sender et HTTP GET Request til RIOT API med SummonerName %(Returnere kun status kode)%
    public Integer httpRequestBySummonernameStatusCode(String summonerName){
        summonerName = summonerName.replace(" ",""); //Sletter/erstatter alle mellemrum
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 OPR/80.0.4170.91")
                    .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                    .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Origin", "https://developer.riotgames.com")
                    .header("X-Riot-Token", APIKEY)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
    //Sender et HTTP GET Request til RIOT API med SummonerID
    public String httpRequestByEncryptedSummonerID(String encryptedSummonerID){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + encryptedSummonerID))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 OPR/80.0.4170.91")
                    .header("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8")
                    .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Origin", "https://developer.riotgames.com")
                    .header("X-Riot-Token", APIKEY)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }
    //Parser HTTP Respone og returnere puuid
    public String parsePuuid(String jsonLine){
        try {
            JsonElement jsonElement = new JsonParser().parse(jsonLine);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return jsonObject.get("puuid").getAsString();
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }
    //Parser HTTP Respone og returnere level
    public Integer parseLvl(String jsonLine){
        try {
            JsonElement jsonElement = new JsonParser().parse(jsonLine);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return jsonObject.get("summonerLevel").getAsInt();
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }
    //Parser HTTP Respone og returnere Encrypted SummonerID
    public String parseEncryptedSummonerID(String jsonLine){
        try {
            JsonElement jsonElement=new JsonParser().parse(jsonLine);
            JsonObject jsonObject=jsonElement.getAsJsonObject();
            return jsonObject.get("id").getAsString();
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }
    //Parser HTTP Respone og returnere Rank og tier
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
