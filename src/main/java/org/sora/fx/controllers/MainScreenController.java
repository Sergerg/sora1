package org.sora.fx.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TablePosition;
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

    @FXML
    private TableView <Contact> tableClient;

    private ScreensConfiguration screens;

    public MainScreenController(ScreensConfiguration screens) {
        log.debug("MainScreenController constructor");
        this.screens = screens;
    }

    public void showErrorDialog() {
        log.debug("MainScreenController.showErrorDialog() ");
        screens.errorDialog().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("MainScreenController.initialize() ");

        // TODO AutoCreating by Contact?!
        tableClient.getColumns().clear();
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Contact,String>("name")
        );
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Contact,String>("email")
        );
        TableColumn phoneCol = new TableColumn("Phone");
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Contact,String>("phone")
        );
        tableClient.getColumns().addAll(nameCol, emailCol, phoneCol);

        // bind!
        tableClient.setItems(contactService.getData());
    }

    public void addPerson() {
        log.debug("MainScreenController.addPerson() ");
        screens.getPersonController().setSave(false);
        screens.getPersonDialog().show();
    }

    public void refresh() {
        log.debug("MainScreenController.refresh() ");
        refreshTableClient();
    }

    public void refreshTableClient() {
        log.debug("MainScreenController.refreshTableClient() ");

        contactService.loadData();
    }

    public void editPerson() {
        log.debug("MainScreenController.editPerson() ");

        Contact contact = tableClient.getSelectionModel().getSelectedItem();
        if (contact != null) {
            screens.getPersonController().setContact(contact);
            screens.getPersonDialog().show();
        }
    }

    public void delPerson() {
        log.debug("MainScreenController.delPerson() ");
        Contact contact = tableClient.getSelectionModel().getSelectedItem();
        if (contact != null) {
            contactService.delContact(contact);
            contactService.loadData();
        }
    }
}
