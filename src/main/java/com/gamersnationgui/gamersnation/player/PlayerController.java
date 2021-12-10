package com.gamersnationgui.gamersnation.player;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.data.util.CastUtils.cast;

//This is the API Layer
@Component
@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    String responesBodyOnSummonerName;
    String responesBodyOnSummonerID;
    String summonerID;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayer() {
        return playerService.getPlayer();
    }

    @PostMapping
    public void addPlayer(@RequestBody Player player){
        playerService.addPlayer(player);
    }


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
            responesBodyOnSummonerName = playerService.httpRequestBySummonername(summonerNameField.getText());
            summonerID = playerService.parseEncryptedSummonerID(responesBodyOnSummonerName);
            this.playerLvl.setText(""+playerService.parseLvl(responesBodyOnSummonerName));
            responesBodyOnSummonerID = playerService.httpRequestByEncryptedSummonerID(summonerID);
            this.playerRank.setText(playerService.parseRank(responesBodyOnSummonerID));
            this.showSummonerName.setText(this.summonerNameField.getText());
        });
        this.modeChoiceBox.getItems().add("Ranked");
        this.modeChoiceBox.getItems().add("Normal");
        this.vcChoiceBox.getItems().add("On");
        this.vcChoiceBox.getItems().add("Off");
        //this.playerListComBox.setItems(FXCollections.observableArrayList(playerService.getPlayers()));
    }
}
