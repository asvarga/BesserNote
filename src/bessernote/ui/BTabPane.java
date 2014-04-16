/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
public class BTabPane extends BorderPane{
    
    /*
    ** CAUTION: TABS DON'T HAVE ANY CONTENT WHEN INITIALIZED!!!
    */
    
    final Button addButton = new Button("+");
    final TabPane tabPane = new TabPane();
    
    public BTabPane() {
        
      addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
          public void handle(ActionEvent event) {
            final EditableTab tab = new EditableTab("Tab " + (tabPane.getTabs().size() + 1));
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
          }
        });
        
        EditableTab tab = new EditableTab("Tab 1");
        tabPane.getTabs().add(tab);
        
        GridPane top = new GridPane();
        top.setConstraints(tabPane, 0, 0);
        top.setConstraints(addButton, 1, 0);
        top.getChildren().addAll(tabPane, addButton);
        
        this.setTop(top);
    }
    
    
}
