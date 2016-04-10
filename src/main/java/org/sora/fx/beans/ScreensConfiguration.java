package org.sora.fx.beans;

import org.sora.fx.controllers.AddPersonController;
import org.sora.fx.controllers.ErrorController;
import org.sora.fx.controllers.MainScreenController;
import org.sora.fx.dialogs.FXMLDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:24
 */
@Configuration()
@Lazy // Чтоб наши бины грузились многим позже - по запросу!!!
public class ScreensConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ScreensConfiguration.class);

    @Value("${ui.main.viewName:main}")
    private String mainView;

    @Value("${ui.main.title:JavaFX приложение}")//
    private String windowTitle;

    public String nameFxmlConverter(String part) {
        return "/fxml/"+part+".fxml";
    }
    public String nameCssConverter(String part) {
        return "/css/"+part+".css";
    }

    @PostConstruct
    public void init() {
        log.info("      ScreensConfiguration init()");
    }

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        Scene scene = new Scene(screen);
        scene.getStylesheets().add(getClass().getResource(nameCssConverter(mainView)).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle(windowTitle);
        primaryStage.show();
    }

    @Bean
    public MainScreenController mainScreenController() {
        return new MainScreenController(this);
    }

    @Bean
    public Parent mainForm(MainScreenController controller) throws IOException {
        try {
            URL fxmlUrl = getClass().getResource(nameFxmlConverter(mainView));
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setControllerFactory(aClass -> {return controller;});
            Parent view = loader.load();
            return view;
        } catch (IOException e) {
            log.error("Can't load resource", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Scope("prototype") // Каждый раз новый
    public FXMLDialog errorDialog() {
        return new FXMLDialog(
                errorController(), // Каждый раз новый контроллер!!!
                "error", primaryStage); // , StageStyle.UNDECORATED
    }

    @Bean
    @Scope("prototype") // Каждый раз новый
    public ErrorController errorController() {
        return new ErrorController();
    }

    @Bean  //@Scope("prototype") // - нет ошибки при закрытии! // Scope == singleton - только в первый раз!
    public FXMLDialog getPersonDialog() {
        return new FXMLDialog(
                getPersonController(), // Каждый раз новый контроллер!!!
                "personform", primaryStage);
    }

    @Bean
    //@Scope("prototype")
    public AddPersonController getPersonController() {
        return new AddPersonController();
    }

    // Эквивалентно выше
//    private AddPersonController personController;
//    public AddPersonController getPersonController() {
//        if (personController == null)
//            personController = new AddPersonController();
//        return personController;
//    }


}
