package com.gamersnation.gamersnationApplication;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot {
    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }
}
