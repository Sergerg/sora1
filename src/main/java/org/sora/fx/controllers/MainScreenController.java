package org.sora.fx.controllers;

import javafx.event.ActionEvent;
import org.sora.fx.beans.ScreensConfiguration;
import org.sora.fx.entity.Contact;
import org.sora.fx.services.ContactService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private ContactService contactService;

    private ScreensConfiguration screens;

    public MainScreenController(ScreensConfiguration screens) {
        log.debug("MainScreenController()");
        this.screens = screens;
    }

    public void showErrorDialog() {
        log.debug("showErrorDialog() ");
        screens.errorController();
        screens.errorController().show("Error text", "Error title");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("initialize() ");
    }

    public void listContacts(ActionEvent actionEvent) {
        screens.getContactListDialog().show();
    }

    public void addContacts(ActionEvent actionEvent) {
        log.debug("addContacts() ");
        screens.getPersonController().add();
    }
}
