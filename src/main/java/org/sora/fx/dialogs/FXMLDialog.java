package org.sora.fx.dialogs;

import org.sora.fx.controllers.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Serger
 * Date: 03.09.2015
 * Time: 8:34
 */
public class FXMLDialog extends Stage {

    private static final Logger log = LoggerFactory.getLogger(FXMLDialog.class);

    public FXMLDialog() {
        super();
        // for manual form - Autowired!
    }

    public FXMLDialog(DialogController controller, String name, Window owner) {
        this(controller, name, owner, Modality.WINDOW_MODAL, StageStyle.DECORATED, false);
    }

    public FXMLDialog(DialogController controller, String name, Window owner, StageStyle style) {
        this(controller, name, owner, Modality.WINDOW_MODAL, style, false);
    }

    public FXMLDialog(DialogController controller, String name, Window owner, Modality modality) {
        this(controller, name, owner, modality, StageStyle.DECORATED, true);
    }

    public FXMLDialog(final DialogController controller, String name, Window owner, Modality modality, StageStyle style, boolean resizable) {
        super(style);
        log.debug("FXMLDialog");

        URL fxml = getClass().getResource("/fxml/"+name+".fxml");
        URL css = getClass().getResource("/css/" + name + ".css");
        log.debug("FXMLDialog.css = " + css);
        initOwner(owner);
        initModality(modality);
        setResizable(resizable);
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(aClass -> controller);
            controller.setDialog(this);
            Scene s = new Scene((Parent) loader.load());
            if (css != null) {
                s.getStylesheets().add(css.toExternalForm());
            }
            setScene(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
