package com.gamersnationgui.gamersnation.player;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This is the API Layer
@Component
@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

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

    @FXML
    public Button signUpBtn;

    @FXML
    public ComboBox playerListComBox;

    @FXML
    public TextField summonerNameField;

    @FXML
    public void initialize(){
        this.signUpBtn.setOnAction(actionEvent -> playerService.httpRequestBySummonername(summonerNameField.getText()));
        this.playerListComBox.setItems(FXCollections.observableArrayList(playerService.getPlayers()));
    }
}
