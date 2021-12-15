package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.ExternalAPIManagers.RiotAPIManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ApplicationController {
    private final RiotAPIManager riotAPIManager = new RiotAPIManager();
    private final ApplicationModel applicationModel;

    public ApplicationController(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    String puuid;
    String responesBodyOnSummonerName;
    String responesBodyOnSummonerID;
    String summonerID;
    String rank;
    Integer statusCode;
    int level;
    boolean chosenMode;
    boolean chosenVoiceChat;


    @FXML
    public Label signUpLbl;
    public TextField summonerNameField;
    public Button findPlayer;
    public TextField showSummonerName;
    public ChoiceBox modeChoiceBox;
    public TextField playerLvl;
    public TextField playerRank;
    public Slider toleranceSlider;
    public Slider commitmentSlider;
    public ChoiceBox vcChoiceBox;
    public ChoiceBox positionChoiceBox;
    public Button signUpBtn;
    public Text errortxt;


    @FXML
    public void initialize(){
        this.findPlayer.setOnAction(actionEvent -> {
            statusCode = riotAPIManager.httpRequestBySummonernameStatusCode(summonerNameField.getText());
            if (statusCode == 200) {
                responesBodyOnSummonerName = riotAPIManager.httpRequestBySummonername(summonerNameField.getText());
                summonerID = riotAPIManager.parseEncryptedSummonerID(responesBodyOnSummonerName);
                puuid = riotAPIManager.parsePuuid(responesBodyOnSummonerName);
                level = riotAPIManager.parseLvl(responesBodyOnSummonerName);
                responesBodyOnSummonerID = riotAPIManager.httpRequestByEncryptedSummonerID(summonerID);
                rank = riotAPIManager.parseRank(responesBodyOnSummonerID);
                this.playerLvl.setText("" + level);
                this.playerRank.setText(rank);
                this.showSummonerName.setText(this.summonerNameField.getText());
            } else {
                playerLvl.clear();
                playerRank.clear();
                showSummonerName.clear();
                summonerNameField.setText("Name not Found");
            }
        });



        this.modeChoiceBox.getItems().add("Ranked");
        this.modeChoiceBox.getItems().add("Normal");



        this.vcChoiceBox.getItems().add("On");
        this.vcChoiceBox.getItems().add("Off");


        this.positionChoiceBox.getItems().add("Bot");
        this.positionChoiceBox.getItems().add("Jungle");
        this.positionChoiceBox.getItems().add("Mid");
        this.positionChoiceBox.getItems().add("Support");
        this.positionChoiceBox.getItems().add("Top");

        this.errortxt.setVisible(false);


        //this.playerListComBox.setItems(FXCollections.observableArrayList(playerService.getPlayers()));

        this.signUpBtn.setOnAction(actionEvent -> {
            try {
                if (this.vcChoiceBox.getValue() == "On"){
                    chosenVoiceChat = true;
                } else { chosenVoiceChat = false;}

                if (this.modeChoiceBox.getValue() == "Ranked"){
                    chosenMode = true;
                } else { chosenMode = false;}

                //Tjekker om alle felter er udfyldt
                if (!showSummonerName.getText().isEmpty() &&
                    !playerLvl.getText().isEmpty() &&
                    !playerRank.getText().isEmpty() &&
                    !modeChoiceBox.getValue().toString().equals("Mode") &&
                    !vcChoiceBox.getValue().toString().equals("Voice Chat") &&
                    !positionChoiceBox.getValue().toString().equals("Position")) {
                        applicationModel.addPlayer(puuid,
                            summonerNameField.getText(),
                            level,
                            rank,
                            chosenMode,
                            this.toleranceSlider.getValue(),
                            this.commitmentSlider.getValue(),
                            chosenVoiceChat,
                            this.positionChoiceBox.getValue().toString());
                        } else {
                            this.errortxt.setVisible(true);
                        }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
