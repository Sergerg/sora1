package org.sora.fx.controllers;

import org.sora.fx.beans.ScreensConfiguration;
import org.sora.fx.dialogs.FXMLDialog;
import org.sora.fx.entity.Contact;
import org.sora.fx.services.ContactService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 08.09.2015
 * Time: 2:32
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class AddContactController implements DialogController {

    private static final Logger log = LoggerFactory.getLogger(AddContactController.class);

    @Autowired private ContactService contactService;

    @FXML TextField txtNick;
    @FXML TextField txtName;
    @FXML TextField txtPhone;
    @FXML TextField txtEmail;

    private FXMLDialog dialog;

    private boolean save;

    @Autowired
    private ScreensConfiguration screens;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public void cancel() {
        close();
    }

    public void ok() {
        Contact contact = new Contact(txtNick.getText(), txtName.getText(), txtEmail.getText(), txtPhone.getText());
        try {
            if (!save) {
                contactService.addContact(contact);
            } else {
                contactService.edit(contact);
            }
        } catch (Exception e) {
            screens.errorController().show(e.getMessage(), "Error title");
        }
        //log.debug("   Contact size = " + contactService.getContactList().size());
        close();
    }

    // TODO - move upper, auto clean!!!
    private void close() {
        log.debug("AddContactController.close()");
        txtNick.clear();
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        dialog.close();
        // sign to change data
        contactService.loadData();
    }

    public void add() {
        log.debug("AddContactController.add()");
        save = false;
        screens.getPersonDialog().show();
    }

    public void edit(Contact contact) {
        log.debug("AddContactController.edit()");
        txtNick.setText(contact.getNick());
        txtName.setText(contact.getName());
        txtEmail.setText(contact.getEmail());
        txtPhone.setText(contact.getPhone());
        save = true;
        screens.getPersonDialog().show();
    }
}
