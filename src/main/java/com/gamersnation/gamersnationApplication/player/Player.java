package com.gamersnation.gamersnationApplication.player;

import javax.persistence.*;

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

    public Player(){}

    public Player(String puuid,String summonerName, int level, String rank, boolean mode, double tolerance, double commitment, boolean voiceChat, String position){
        this.puuid = puuid;
        this.summonerName = summonerName;
        this.mode = mode;
        this.level = level;
        this.rank=rank;
        this.tolerance=tolerance;
        this.commitment=commitment;
        this.voiceChat=voiceChat;
        this.position=position;
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

    /*
    public String getPositionString(){
        String positionString=null;
        if (position==1){
            positionString="Top";
        }else if (position==2){
            positionString="Jungle";
        }else if (position==3){
            positionString="Mid";
        }else if (position==4){
            positionString="Support";
        }else if (position==5){
            positionString="Bot";
        }
        return positionString;
    }*/

    /*
    iron
    bronze
    silver
    gold
    Platnum
    diamond
    master
    grandmaster
    challenger

    public String rankModifyer(){
        int rankStageint=0;
        String rankStage="";
        int ranksLvl=rank;

        if (ranksLvl==0){
            rankStage="unranked";
            return rankStage;
        }else{
            rankStageint+=1;
        }

        while (ranksLvl>4){
            ranksLvl-=4;
            rankStageint+=1;
        }

        if (rankStageint==1){
            rankStage="Iron";
        }else if (rankStageint==2){
            rankStage="Bronze";
        }else if (rankStageint==3){
            rankStage="Silver";
        }else if (rankStageint==4){
            rankStage="Gold";
        }else if (rankStageint==5){
            rankStage="Platnum";
        }else if (rankStageint==6){
            rankStage="Diamond";
        }else if (rankStageint==7){
            rankStage="Master";
        }else if (rankStageint==8){
            rankStage="Grand master";
        }else if (rankStageint>=9){
            return "Challenger";
        }

        return rankStage + " " + ranksLvl;
    }
*/
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
                '}';
    }

    /*
    public Double compatibility(Player player1, Player player2){
        PercentCalculator myCal = new PercentCalculator();
        double lvl = myCal.numberPercent(player1.getLvl(), player2.getLvl());
        double rnk = myCal.numberPercent(player1.getRank(), player2.getRank());
        double pos = myCal.pVDif(player1.getPosition(), player2.getPosition());
        double tol = myCal.numberPercent(player1.getTolerance(), player2.getTolerance());
        double vc = myCal.pVDif(player1.getVc(), player2.getVc());
        double seri = myCal.numberPercent(player1.getCommitment(), player2.getCommitment());

        return endMatchResult(lvl, rnk, pos, tol, vc, seri);
    }

    // Gammel toString
    @Override
    public String toString(){
        String modeOfPlayer;
        String voiceChat;
        if(mode){
            modeOfPlayer="ranked";
        }else{
            modeOfPlayer="casual";
        }

        if (vc==1){
            voiceChat="yes";
        }else {
            voiceChat="no";
        }

        return ("The username of the player: "+name
                +"\nThe mode the player whish to play: " +modeOfPlayer
                +"\nThe level of the player is: "+lvl
                +"\nThe rank of the player is: "+rankModifyer()
                +"\nThe players tolerance for error is: "+tolerance
                +"\nThe player says "+voiceChat+" to voice chat" +
                "\nThe players preferred position is: "+getPositionString());
    }*/
}