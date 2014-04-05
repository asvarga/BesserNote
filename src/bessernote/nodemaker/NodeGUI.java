/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 *
 * @author avarga
 */
public class NodeGUI extends BaseGUI {

    ComboBox combo;
    ShowOneGUI show1;
    public Button createButton;
    
//    Pane target;
//    double tx;
//    double ty;

    public NodeGUI(double spacing) {

        super(spacing);

        Text t = new Text("--- Node Creation GUI ---");

        combo = new ComboBox();
        combo.getItems().addAll("Pane", "Label", "VBox");
        combo.setValue("Pane");
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                show1.showGUI(t1);
                autosize();
            }
        });

        show1 = new ShowOneGUI();
        show1.addGUI("Pane", new PaneGUI(spacing));
        show1.addGUI("Label", new LabelGUI(spacing));

        createButton = new Button("Create");

        getChildren().addAll(t, combo, show1, createButton);
    }
    
    @Override
    public Node getNode() {
        return show1.getNode();
    }
    
//    public void setTarget(Pane node) {
//        target = node;
//    }
//    public void setTarget(double x, double y) {
//        tx = x;
//        ty = y;
//    }
//    public void setTarget(Pane node, double x, double y) {
//        target = node;
//        tx = x;
//        ty = y;
//    }
}
