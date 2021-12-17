package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.ExternalAPIManagers.RiotAPIManager;
import com.gamersnation.gamersnationApplication.StageReadyEvent;
import com.gamersnation.gamersnationApplication.player.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.springframework.data.util.CastUtils.cast;

@Controller
public class ApplicationController {
    private final RiotAPIManager riotAPIManager = new RiotAPIManager();
    private final ApplicationModel applicationModel;
    ApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final DecimalFormat df = new DecimalFormat("00.00");

    public ApplicationController(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    /*
    public void sceneSwitch() throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("SearchView.fxml"));
        Stage window = (Stage)signUpBtn.getScene().getWindow();
        window.setScene(new Scene(root, 600,600));
    }*/

    String puuid;
    String responesBodyOnSummonerName;
    String responesBodyOnSummonerID;
    String summonerID;
    String rank;
    Integer statusCode;
    int level;
    boolean chosenMode;
    boolean chosenVoiceChat;
    boolean chosenSearchVoiceChat;
    boolean chosenSearchMode;


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
    public Label label;

    @FXML
    public Button searchBtn;
    public TextField searchLevel;
    public TextField searchRank;
    public ChoiceBox searchMode;
    public ChoiceBox searchPosition;
    public ChoiceBox searchVoiceChat;
    public Slider searchCommitment;
    public Slider searchTolerance;
    public ListView listView;

    @FXML
    public void initialize(){
        this.modeChoiceBox.getItems().add("Ranked");
        this.modeChoiceBox.getItems().add("Normal");

        this.vcChoiceBox.getItems().add("On");
        this.vcChoiceBox.getItems().add("Off");

        this.positionChoiceBox.getItems().add("Bot");
        this.positionChoiceBox.getItems().add("Jungle");
        this.positionChoiceBox.getItems().add("Mid");
        this.positionChoiceBox.getItems().add("Support");
        this.positionChoiceBox.getItems().add("Top");

        this.searchPosition.getItems().add("Bot");
        this.searchPosition.getItems().add("Jungle");
        this.searchPosition.getItems().add("Mid");
        this.searchPosition.getItems().add("Support");
        this.searchPosition.getItems().add("Top");

        this.searchMode.getItems().add("Ranked");
        this.searchMode.getItems().add("Normal");

        this.searchVoiceChat.getItems().add("On");
        this.searchVoiceChat.getItems().add("Off");

        this.errortxt.setVisible(false);


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


        //this.playerListComBox.setItems(FXCollections.observableArrayList(playerService.getPlayers()));

        //Action event for the 'sign-up' Button
        this.signUpBtn.setOnAction(signUpBtnEvent -> {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //Action event for the 'Search' button
        this.searchBtn.setOnAction(actionEvent -> {
            if (this.searchVoiceChat.getValue() == "On"){
                chosenSearchVoiceChat = true;
            } else { chosenSearchVoiceChat = false;}

            if (this.searchMode.getValue() == "Ranked"){
                chosenSearchMode = true;
            } else { chosenSearchMode = false;}


            ArrayList<Player> playerDisplay = applicationModel.calculateMatch(chosenSearchMode,
                                            Integer.parseInt(searchLevel.getText()),
                                            searchRank.getText(),
                                            searchTolerance.getValue(),
                                            searchCommitment.getValue(),
                                            chosenSearchVoiceChat,
                                            searchPosition.getValue().toString());
            for (int i = 0; i < 10; i++) {
                listView.getItems().add("Summoner Name: "+playerDisplay.get(i).getSummonerName() +"     "+ df.format(playerDisplay.get(i).getMatchPercent())+"% Match");
                listView.getItems().add("Level: "+playerDisplay.get(i).getLevel()
                        +"  Rank: "+playerDisplay.get(i).getRank()
                        +"  Commitment: "+ playerDisplay.get(i).getCommitment()
                        +"  Tolerance: "+playerDisplay.get(i).getTolerance());
                listView.getItems().add("Position: "+playerDisplay.get(i).getPosition()
                        +"  Voice Chat: "+playerDisplay.get(i).getVoiceChat()
                        +"  Rank Mode: "+playerDisplay.get(i).getMode());
                listView.getItems().add("---------------------------------------------------------------------------");
            }
        });
    }
}
