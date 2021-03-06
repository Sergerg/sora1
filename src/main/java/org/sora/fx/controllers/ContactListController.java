package org.sora.fx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sora.fx.beans.ScreensConfiguration;
import org.sora.fx.dialogs.FXMLDialog;
import org.sora.fx.entity.Contact;
import org.sora.fx.services.ContactService;
//import org.springframework.beans.factory.annotation.Autowired;
import javax.inject.Inject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 15.04.2016
 * Time: 15:06
 */
public class ContactListController implements DialogController, Initializable {

    private static final Logger log = LoggerFactory.getLogger(MainScreenController.class);

    //@Autowired
    @Inject
    private ContactService contactService;

    //@Autowired
    @Inject
    private ScreensConfiguration screens;

    private FXMLDialog dialog;

    @FXML
    private TableView<Contact> tableClient;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug("initialize() ");

        // TODO AutoCreating by Contact?!
        tableClient.getColumns().clear();
        TableColumn nickCol = new TableColumn("Nick");
        nickCol.setCellValueFactory(
                new PropertyValueFactory<Contact,String>("nick")
        );
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
        tableClient.getColumns().addAll(nickCol, nameCol, emailCol, phoneCol);

        // bind!
        tableClient.setItems(contactService.getData());
    }

    public void addPerson() {
        log.debug("addPerson() ");
        screens.getContactController().add();
    }

    public void refresh() {
        log.debug("refresh() ");
        refreshTableClient();
    }

    public void refreshTableClient() {
        log.debug("refreshTableClient() ");

        contactService.loadData();
    }

    public void editPerson() {
        log.debug("editPerson() ");

        Contact contact = tableClient.getSelectionModel().getSelectedItem();
        if (contact != null) {
            screens.getContactController().edit(contact);
        }
    }

    public void delPerson() {
        log.debug("delPerson() ");
        Contact contact = tableClient.getSelectionModel().getSelectedItem();
        if (contact != null) {
            contactService.delContact(contact);
            contactService.loadData();
        }
    }

}
