package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.RestAPIManagers.RestAPIManager;
import com.gamersnation.gamersnationApplication.player.Player;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gamersnation.gamersnationApplication.player.AppUserRole.USER;

@Service
public class ApplicationModel implements UserDetailsService {
    RestAPIManager restAPIManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ApplicationModel(RestAPIManager restAPIManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.restAPIManager = restAPIManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Sener et HTTP POST request med en spiller til vores API for at gemme spilleren i databasen
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

    public void addUser(String puuid, String summonerName, String password) throws IOException{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        JSONObject userJsonObject = new JSONObject();
        try {
            userJsonObject.put("puuid", puuid);
            userJsonObject.put("summonerName", summonerName);
            userJsonObject.put("password", encodedPassword);
            userJsonObject.put("appUserRole", USER);
        } catch (JSONException e){
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);
        String result = restTemplate.postForObject("http://localhost:8080/api/v1/player/addUser", request, String.class);
    }

    //Udføre match procent beregning af nogle givende søge kriterier og samtlige spillere i databasen
    public ArrayList<Player> calculateMatch(boolean mode, int level, String rank, double tolerance, double commitment,
                                            boolean voiceChat, String position){
        List<Player> playerList = restAPIManager.getPlayerList();
        ArrayList<Player> matchedPlayers = new ArrayList<>();
        Player searchPlayer = new Player(mode, level, rank, tolerance, commitment, voiceChat, position);
        for (int i = 0; i < playerList.size(); i++) {
            Player checkPlayer = playerList.get(i);
            MatchCalculator mycal = new MatchCalculator(searchPlayer, checkPlayer);
            checkPlayer.setMatchPercent(mycal.matchResult());
            matchedPlayers.add(checkPlayer);
        }
        return mergeSort(matchedPlayers);
    }

    //Merge sort Algoritme del 1 (Deler ArayList op)
    public static ArrayList<Player> mergeSort(ArrayList<Player> playerList){
        if(playerList.size() == 1){
            return playerList;
        }

        int mid = playerList.size()/2;
        ArrayList<Player> left = new ArrayList<Player>();
        ArrayList<Player> right = new ArrayList<Player>();

        for (int i = 0; i < mid; i++) {
            left.add(playerList.get(i));
        }

        for (int i = mid; i < playerList.size(); i++) {
            right.add(playerList.get(i));
        }

        mergeSort(left);
        mergeSort(right);

        merge(playerList, left, right);

        return playerList;
    }

    //Merge sort Algoritme del 2 (Samler ArrayList i en sortert rækkefølge)
    public static void merge(ArrayList<Player> playerList, ArrayList<Player> left, ArrayList<Player> right){
        int leftIndex=0, rightIndex=0, playerListIndex=0;

        while(leftIndex<left.size() && rightIndex < right.size()){
            if (left.get(leftIndex).getMatchPercent()  >= right.get(rightIndex).getMatchPercent()){
                playerList.set(playerListIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                playerList.set(playerListIndex, right.get(rightIndex));
                rightIndex++;
            }
            playerListIndex++;
        }

        ArrayList<Player> leftOver;
        int leftOverIndex;

        if (leftIndex >= left.size()){
            leftOver = right;
            leftOverIndex = rightIndex;
        } else {
            leftOver = left;
            leftOverIndex = leftIndex;
        }

        for (int i = leftOverIndex; i < leftOver.size(); i++) {
            playerList.set(playerListIndex, leftOver.get(i));
            playerListIndex++;
        }
    }

    //Test method (Only used for JUnit Test)
    public static double matchPlayers(Player player1, Player player2){
        MatchCalculator mycal = new MatchCalculator(player1, player2);
        return mycal.matchResult();
    }

    //Log-in
    public Boolean LoginMatch(String lUsername, String lPassword){
        if (lUsername.equals(restAPIManager.getUserNameByUserName(lUsername)) &&
                bCryptPasswordEncoder.matches(lPassword, restAPIManager.getPasswordByUserName(lUsername))){
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return restAPIManager.getUserBySummonerName(userName);
    }
}

//Indlejret class til match beregning
class MatchCalculator {
    private final Player player1;
    private final Player player2;
    private double result;

    //Constructor
    public MatchCalculator(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    //Calculation of level, Rank, tolerance and seriousness difference of the players
    public double numberPercent(double u1, double u2) {
        if (u1 > u2) {
            this.result = (u2 / u1) * 100;
        } else {
            this.result = (u1 / u2) * 100;
        }
        return this.result;
    }

    //Calculation of the voice-chat wish
    public double voiceChatDif() {
        if (player1.getVoiceChat() == player2.getVoiceChat()) {
            this.result = 100;
        } else {
            this.result = 0;
        }
        return this.result;
    }

    //Calculation of position preference
    public double positionDif() {
        if (player1.getPosition() == player2.getPosition()) {
            this.result = 100;
        } else {
            this.result = 0;
        }
        return this.result;
    }

    //Calculate the final match percent
    public double matchResult(){
        double levelResult=(numberPercent(player1.getLevel(),player2.getLevel())/100)*10;
        double rankResult=(numberPercent(player1.rankToNumberModifyer(),player2.rankToNumberModifyer())/100)*15;
        double positionResult=(positionDif()/100)*30;
        double toleranceResult = (numberPercent(player1.getTolerance(),player2.getTolerance())/100)*20;
        double commitmentResult = (numberPercent(player1.getCommitment(), player2.getCommitment())/100)*20;
        double voicechatResult = (voiceChatDif()/100)*5;
        return levelResult+rankResult+positionResult+toleranceResult+commitmentResult+voicechatResult;
    }

}



