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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
    private final ColorPicker cp = new ColorPicker();
    
    public BTabPane() {
        
      addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
          public void handle(ActionEvent event) {
            final BEditableTab tab = new BEditableTab("Tab " + (tabPane.getTabs().size() + 1));
            //add.setPrefSize(tabPane.getTabMaxHeight(), tabPane.getTabMaxWidth());
            Pane addMe = new BWrapPane();
            addMe.setPrefSize(BTabPane.this.getPrefWidth(), BTabPane.this.getPrefHeight());
            addMe.setStyle("-fx-background-color: #FF7F50;");
            tab.setContent(addMe);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
          }
        });
        
        BEditableTab tab = new BEditableTab("Tab 1");
        Pane addMe = new BWrapPane();
        System.out.println(this.getPrefWidth());
        addMe.setPrefSize(this.getPrefWidth(), this.getPrefHeight());
        addMe.setStyle("-fx-background-color: #FF7F50;");
        tab.setContent(addMe);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        
        /*
        GridPane top = new GridPane();
        top.setPrefHeight(this.getPrefHeight());
        GridPane.setConstraints(tabPane, 0, 0);
        GridPane.setConstraints(addButton, 1, 0);
        cp.setPrefWidth(addButton.getPrefWidth());
        GridPane.setConstraints(cp, 1, 1);
        top.getChildren().addAll(tabPane, addButton, cp);
        */
        this.setCenter(tabPane);
        GridPane top = new GridPane();
        top.setConstraints(addButton, 0, 0);
        top.setConstraints(cp, 1, 0);
        top.setPrefHeight(25);
        top.getChildren().addAll(addButton, cp);
        this.setTop(top);
        
                
        //// Color Picker ////
        cp.setOnAction(new EventHandler(){
            @Override
            public void handle(Event e){
                Color c = cp.getValue();
                tabPane.getSelectionModel().getSelectedItem().getContent().setStyle("-fx-background-color: #"+c.toString().substring(2, c.toString().length()-2));
            }
        });
                
                
    }
    
    
    @Override
    public List<Node> specifyChildren() {
        //List<Node> children = tabPane.getSelectionModel().getSelectedItem().getContent().getChildren();//new ArrayList<Node>();
        Node content = tabPane.getSelectionModel().getSelectedItem().getContent();
        //children.add(content);
        Parent contentz = (Parent) content;
        return contentz.getChildrenUnmodifiable();
    }

//    @Override
//    public ObservableList<Node> getChildren() {
//        // TODO: return shown pane's children
//        return (new Pane()).getChildren();
//    }
    
    @Override
    public Node specifySelf() {
        return this.tabPane.getSelectionModel().getSelectedItem().getContent();
    }
    
    public List<Tab> getTabs(){
        return tabPane.getTabs();
    }
}
