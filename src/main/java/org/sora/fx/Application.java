package org.sora.fx;

import org.sora.fx.beans.ScreensConfiguration;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 02.09.2015
 * Time: 6:20
 */
@SpringBootApplication
public class Application extends javafx.application.Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static String[] args;

    public static void main(String[] args) {
        Application.args = args;

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        log.debug("Spring context load ok.");

        ScreensConfiguration screens = context.getBean(ScreensConfiguration.class);
        screens.setPrimaryStage(primaryStage);
        screens.showScreen(screens.mainForm(screens.mainScreenController()));
        log.debug("End start.");
    }

}
