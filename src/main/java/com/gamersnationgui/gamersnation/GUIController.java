package com.gamersnationgui.gamersnation;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class GUIController {
    private final HostServices hostServices;

    public GUIController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    public Label signUpLbl;

    @FXML
    public Button signUpBtn;


    @FXML
    public void initialize(){
        this.signUpBtn.setOnAction(actionEvent -> this.signUpLbl.setText(""));
    }
}
