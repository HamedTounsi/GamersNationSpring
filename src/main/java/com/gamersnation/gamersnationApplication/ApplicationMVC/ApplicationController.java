package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.ExternalAPIManagers.RiotAPIManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController {
    private final RiotAPIManager riotAPIManager = new RiotAPIManager();

    String responesBodyOnSummonerName;
    String responesBodyOnSummonerID;
    String summonerID;
    Integer statusCode;


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

    @FXML
    public void initialize(){
        this.findPlayer.setOnAction(actionEvent -> {
            statusCode = riotAPIManager.httpRequestBySummonernameStatusCode(summonerNameField.getText());
            if (statusCode == 200) {
                responesBodyOnSummonerName = riotAPIManager.httpRequestBySummonername(summonerNameField.getText());
                summonerID = riotAPIManager.parseEncryptedSummonerID(responesBodyOnSummonerName);
                this.playerLvl.setText("" + riotAPIManager.parseLvl(responesBodyOnSummonerName));
                responesBodyOnSummonerID = riotAPIManager.httpRequestByEncryptedSummonerID(summonerID);
                this.playerRank.setText(riotAPIManager.parseRank(responesBodyOnSummonerID));
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
        //this.playerListComBox.setItems(FXCollections.observableArrayList(playerService.getPlayers()));
    }
}
