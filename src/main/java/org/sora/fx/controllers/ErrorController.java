package org.sora.fx.controllers;

import org.sora.fx.dialogs.FXMLDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:28
 */
public class ErrorController implements DialogController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    private FXMLDialog dialog; // не нужен, через getScene получаю доступ...

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public ErrorController() {
        log.debug("ErrorController()");
    }

    @FXML
    private Button btn;

    @FXML
    public void cancel() {
        //dialog.cancel();
        ((Stage)btn.getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        log.debug("initialize");
    }
}
