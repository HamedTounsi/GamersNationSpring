package com.gamersnation.gamersnationApplication.player;

import javax.persistence.*;
import java.util.Locale;

@Entity(name = "Player")
@Table
public class Player{
    @Id
    @SequenceGenerator( //find ud af hvad jeg laver?
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    private String puuid;
    private String summonerName;
    private boolean mode;
    private int level;
    private String rank;
    private double tolerance;
    private double commitment;
    private boolean voiceChat;
    private String position;
    private double matchPercent;


    //Constructor with no parameter
    public Player(){}

    //Constructor without puuid and summonerName
    public Player(boolean mode, int level, String rank, double tolerance, double commitment, boolean voiceChat, String position) {
        this.mode = mode;
        this.level = level;
        this.rank = rank;
        this.tolerance = tolerance;
        this.commitment = commitment;
        this.voiceChat = voiceChat;
        this.position = position;
    }

    //Constructor with all parameters
    public Player(String puuid, String summonerName, int level, String rank, boolean mode, double tolerance, double commitment, boolean voiceChat, String position){
        this.puuid = puuid;
        this.summonerName = summonerName;
        this.mode = mode;
        this.level = level;
        this.rank=rank;
        this.tolerance=tolerance;
        this.commitment=commitment;
        this.voiceChat=voiceChat;
        this.position=position;
        this.matchPercent=matchPercent;
    }

    public String getPuuid() {return puuid;}

    public String getSummonerName(){
        return summonerName;
    }

    public boolean getMode(){
        return mode;
    }

    public int getLevel(){
        return level;
    }

    public String getRank(){
        return rank;
    }

    public double getTolerance(){
        return tolerance;
    }

    public double getCommitment(){
        return commitment;
    }

    public boolean getVoiceChat(){
        return voiceChat;
    }

    public String getPosition(){
        return position;
    }

    public double getMatchPercent() {
        return matchPercent;
    }

    public void setMatchPercent(double matchPercent) {
        this.matchPercent = matchPercent;
    }

    public double rankToNumberModifyer(){
        if (rank.toLowerCase(Locale.ROOT).contains("unranked") || rank == null){
            return 1;
        }else if (rank.toLowerCase(Locale.ROOT).contains("iron")){
            return 11;
        }else if (rank.toLowerCase(Locale.ROOT).contains("bronze")){
            return 21;
        }else if (rank.toLowerCase(Locale.ROOT).contains("silver")){
            return 31;
        }else if (rank.toLowerCase(Locale.ROOT).contains("gold")){
            return 41;
        }else if (rank.toLowerCase(Locale.ROOT).contains("platinum")){
            return 51;
        }else if (rank.toLowerCase(Locale.ROOT).contains("diamond")){
            return 61;
        }else if (rank.toLowerCase(Locale.ROOT).contains("master") &&
                !rank.toLowerCase(Locale.ROOT).contains("grandmaster")){
            return 71;
        }else if (rank.toLowerCase(Locale.ROOT).contains("grandmaster")){
            return 81;
        }else if (rank.toLowerCase(Locale.ROOT).contains("challenger")){
            return 91;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Player{" +
                "puuid='" + puuid + '\'' +
                ", name='" + summonerName + '\'' +
                ", mode=" + mode +
                ", level=" + level +
                ", rank=" + rank +
                ", tolerance=" + tolerance +
                ", commitment=" + commitment +
                ", voiceChat=" + voiceChat +
                ", position=" + position +
                ",matchPercent=" + matchPercent+
                '}';
    }
}