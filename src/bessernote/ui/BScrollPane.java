/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 */
public class BScrollPane extends ScrollPane implements ChildSpecifier {
    
    public BWrapPane p;
    double padding;
        
    public BScrollPane() {
        p = new BWrapPane();
        this.setContent(p);
        
        this.boundsInParentProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                p.setPrefMinSize(getWidth(), getHeight());
            }
        });
    }
    
    @Override
    public List<Node> specifyChildren() {
        try {
            return p.specifyChildren();
        } catch (Exception e) {
            System.out.println("ERROR: BScrollPane's content must be of type Parent.");
            return new ArrayList<>();
        }
    }
    
//    @Override
//    public ObservableList<Node> getChildren() {
//        return ((Pane) this.getContent()).getChildren();
//    }
    
    @Override
    public Node specifySelf() {
        return p;
    }
    
    public void setPadding(double d) {
        padding = d;
        p.setPadding(d);
    }

}
