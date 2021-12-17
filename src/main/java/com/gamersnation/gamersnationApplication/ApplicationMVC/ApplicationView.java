package com.gamersnation.gamersnationApplication.ApplicationMVC;

import com.gamersnation.gamersnationApplication.StageReadyEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class ApplicationView implements ApplicationListener<StageReadyEvent>{

    private final String applicationTitle;
    private final Resource fxml;
    private final ApplicationContext applicationContext;
    public static boolean toggle = false;

    ApplicationView(@Value("${spring.application.ui.title}") String applicationTitle,
                    @Value("classpath:/SignUpView.fxml") Resource resource, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.fxml = resource;
        this.applicationContext = applicationContext;
    }

    /*
    public void switchToSearchView() throws IOException {
        URL url = ApplicationView.SearchFxml.getURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root;
        try {
            root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root, 600, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gamers Nation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



        Stage stage = new Stage();
        URL url = new URL("classpath:/SearchView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = FXMLLoader.load(getClass().getResource("classpath:/SearchView.fxml"));
        Stage searchStage = new Stage(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }*/

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            URL url = this.fxml.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(applicationContext::getBean); //ansvarlig for at forbinde PlayerController med SignUpView.fxml

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.setTitle(this.applicationTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public void switchToSearchView(ActionEvent event) throws IOException{
        ApplicationView.stageURL = "classpath:/SearchView.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setControllerFactory(applicationContext::getBean); //ansvarlig for at forbinde PlayerController med SignUpView.fxml

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle(this.applicationTitle);
        stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("SearchView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
}

