/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
public class BTabPane extends BorderPane implements ChildSpecifier {
    
    /*
    ** CAUTION: TABS DON'T HAVE ANY CONTENT WHEN INITIALIZED!!!
    */
    
    final Button addButton = new Button("+");
    final TabPane tabPane = new TabPane();
    
    public BTabPane() {
        
      addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
          public void handle(ActionEvent event) {
            final BEditableTab tab = new BEditableTab("Tab " + (tabPane.getTabs().size() + 1));
            System.out.println(tabPane.getHeight() + ", " + tabPane.getWidth());
            //add.setPrefSize(tabPane.getTabMaxHeight(), tabPane.getTabMaxWidth());
            tab.setContent(new Pane());
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
          }
        });
        
        BEditableTab tab = new BEditableTab("Tab 1");
        tab.setContent(new Pane());
        tabPane.getTabs().add(tab);
        
        GridPane top = new GridPane();
        top.setConstraints(tabPane, 0, 0);
        top.setConstraints(addButton, 1, 0);
        top.getChildren().addAll(tabPane, addButton);
        
        this.setTop(top);
    }
    
    
    @Override
    public List<Node> specifyChildren() {
        List<Node> children = new ArrayList<Node>();
        Node content = tabPane.getSelectionModel().getSelectedItem().getContent();
        children.add(content);
        return children;
    }

//    @Override
//    public ObservableList<Node> getChildren() {
//        // TODO: return shown pane's children
//        return (new Pane()).getChildren();
//    }
    
    @Override
    public Node specifySelf() {
        return this;
    }
}
