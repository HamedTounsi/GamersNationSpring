package com.gamersnation.gamersnationApplication;

import com.gamersnation.gamersnationApplication.player.Player;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;


public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {

        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                        applicationContext.registerBean(Application.class, ()-> JavaFXApplication.this);
                        applicationContext.registerBean(Parameters.class, this::getParameters);
                        applicationContext.registerBean(HostServices.class, this::getHostServices);
                    };

        this.context = new SpringApplicationBuilder()
        .sources(SpringBoot.class)
        .initializers(initializer)
        .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}