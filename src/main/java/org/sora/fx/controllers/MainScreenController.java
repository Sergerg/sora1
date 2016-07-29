package org.sora.fx.controllers;

import org.sora.fx.beans.ScreensConfiguration;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:42
 */
public class MainScreenController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(MainScreenController.class);

    private ScreensConfiguration screens;

    public MainScreenController(ScreensConfiguration screens) {
        log.debug("MainScreenController()");
        this.screens = screens;
    }

    public void showErrorDialog() {
        log.debug("showErrorDialog() ");
        //screens.errorController();
        screens.errorController().show("Error text", "Error title");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("initialize() ");
    }

    public void listContacts() {
        screens.getContactListDialog().show();
    }

    public void addContacts() {
        log.debug("addContacts() ");
        screens.getContactController().add();
    }
}
