package org.sora.fx.controllers;

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
public class AddPersonController implements DialogController {

    private static final Logger log = LoggerFactory.getLogger(AddPersonController.class);

    @Autowired private ContactService contactService;

    @FXML TextField txtName;
    @FXML TextField txtPhone;
    @FXML TextField txtEmail;

    private FXMLDialog dialog;

    @Override
    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }

    public void cancel() {
        close();
    }

    public void add() {
        Contact contact = new Contact(txtName.getText(), txtEmail.getText(), txtPhone.getText());
        contactService.addContact(contact);
        //log.info("   Contact size = " + contactService.getContactList().size());
        close();
    }

    // TODO - move upper, auto clean!!!
    private void close() {
        log.debug("AddPersonController.close()");
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        dialog.close();
        // sign to change data
        contactService.loadData();
    }

}
