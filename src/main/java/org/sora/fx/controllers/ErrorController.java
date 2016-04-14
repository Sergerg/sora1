package org.sora.fx.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.sora.fx.beans.ScreensConfiguration;
import org.sora.fx.dialogs.FXMLDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:28
 */
public class ErrorController implements DialogController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private ScreensConfiguration screens;

    private FXMLDialog dialog; // не нужен, через getScene получаю доступ...

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public ErrorController() {
        log.debug("ErrorController()");
    }

    @FXML
    Button btn;

    @FXML
    TextArea txtText;

    @FXML
    public void cancel() {
        //dialog.cancel();
        // TODO!!!
        ((Stage)btn.getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        log.debug("initialize");
    }

    public void show(String s, String s1) {
        log.debug("show");
        // Чтоб создать!
        screens.errorDialog().show();
        screens.errorDialog().setTitle(s1);
        // Теперь view создан!
        if (txtText != null) {
            txtText.setText(s); //    TODO!!!
        }
    }
}
