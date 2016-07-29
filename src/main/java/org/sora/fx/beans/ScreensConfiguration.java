package org.sora.fx.beans;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.sora.fx.controllers.AddContactController;
import org.sora.fx.controllers.ContactListController;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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

    @Value("${ui.main.title:JavaFX приложение}")
    private String windowTitle;

    @Value("${spring.messages.basename}") // Явно брать из настроек Spring!
    private String mainResource;
    //public

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

    @PreDestroy
    public void destroy() {
        log.info("      ScreensConfiguration destroy()");
    }

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showScreen(Parent screen) {
        log.info("ScreensConfiguration showScreen()");
        Scene scene = new Scene(screen);
        scene.getStylesheets().add(getClass().getResource(nameCssConverter(mainView)).toExternalForm());

        primaryStage.setOnCloseRequest(event -> {
            log.info("primaryStage.setOnCloseRequest");

            ResourceBundle lngBndl = ResourceBundle.getBundle(mainResource);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            try {
                a.setTitle(lngBndl.getString("exitconfirmation.title"));
                a.setHeaderText(lngBndl.getString("exitconfirmation.text"));
            } catch (MissingResourceException e) {
                log.warn("Resource not found: " + e.getMessage());
                a.setTitle("Confirmation");
                a.setHeaderText("Do you really want to leave?");
            }
            a.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Platform.exit();
                } else {
                    event.consume();
                }
            });
        });

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
        log.info("ScreensConfiguration mainForm");
        try {
            URL fxmlUrl = getClass().getResource(nameFxmlConverter(mainView));
            FXMLLoader loader = new FXMLLoader(fxmlUrl, ResourceBundle.getBundle(mainResource)); // Может как-то по-умолчанию из spring?
            loader.setControllerFactory(aClass -> controller);
            Parent view = loader.load();
            return view;
        } catch (IOException e) {
            log.error("Can't load resource", e);
            throw new RuntimeException(e);
        }
    }

    @Bean(destroyMethod="")
    //@Scope("singleton")
    public FXMLDialog errorDialog() {
        return new FXMLDialog(
                mainResource,
                errorController(), // Каждый раз новый контроллер!!!
                "error", primaryStage); // , StageStyle.UNDECORATED
    }

    @Bean
    //@Scope("singleton")
    public ErrorController errorController() {
        log.debug("ErrorController errorController()");
        return new ErrorController();
    }

    @Bean(destroyMethod="")
    //@Scope("prototype") // - нет ошибки при закрытии! // Scope == singleton - только в первый раз (по-умолч.)!
    public FXMLDialog getPersonDialog() {
        return new FXMLDialog(
                mainResource,
                getContactController(), // Каждый раз новый контроллер, если @Scope("prototype")!!!
                "contactform", primaryStage);
    }

    @Bean(destroyMethod="")
    //@Scope("prototype") // - нет ошибки при закрытии! // Scope == singleton - только в первый раз (по-умолч.)!
    public FXMLDialog getContactListDialog() {
        return new FXMLDialog(
                mainResource,
                getContactListController(), // Каждый раз новый контроллер, если @Scope("prototype")!!!
                "contactlistform", primaryStage);
    }

    @Bean
    //@Scope("prototype")
    public AddContactController getContactController() {
        return new AddContactController();
    }

    @Bean
    //@Scope("prototype")
    public ContactListController getContactListController() {
        return new ContactListController();
    }

    // Эквивалентно выше
//    private AddContactController personController;
//    public AddContactController getContactController() {
//        if (personController == null)
//            personController = new AddContactController();
//        return personController;
//    }


}
