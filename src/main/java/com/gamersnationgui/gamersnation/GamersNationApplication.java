package com.gamersnationgui.gamersnation;

import com.gamersnationgui.gamersnation.player.PlayerController;
import com.gamersnationgui.gamersnation.player.PlayerRepository;
import com.gamersnationgui.gamersnation.player.PlayerService;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;


public class GamersNationApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {

        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                        applicationContext.registerBean(Application.class, ()-> GamersNationApplication.this);
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

class StageReadyEvent extends ApplicationEvent {
    public static Stage getStage(){
        return Stage.class.cast(getStage());
    }

    public StageReadyEvent(Stage source) {
        super(source);
    }
}