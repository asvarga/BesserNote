/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class BFlashCard extends BWrapPane {
    boolean vis = false;
    boolean clicked = false;
    boolean mouseOver = false;
    
    public BFlashCard(BUndoManager undoManager) {
        super(undoManager);
        
        placeHolder.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver = true;
                setClickableVisibility(true);
            };
        });
        
        placeHolder.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver = false;
                setClickableVisibility(clicked);
            };
        });
        
        placeHolder.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clicked = !clicked;
                setClickableVisibility(clicked);
            };
        });
        
        clickable.add(placeHolder);
        
        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Node> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                             //permutate
                        }
                    } else if (c.wasUpdated()) {
                             //update item
                    } else {
                        for (Node remItem : c.getRemoved()) {
                            remItem.setVisible(true);
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            addItem.setVisible(vis);
                        }
                        //fixOutline();
                    }
                }
            }
        });
        
//        BTextArea t = new BTextArea();
//        t.setLayoutX(10);
//        t.setLayoutY(10);
//        t.setPrefSize(placeHolder.getWidth()-20, placeHolder.getHeight()-20);
//        placeHolder.getChildren().add(t);
        
        Pane p2 = new Pane();
        //p2.setPrefWidth(p.placeHolder.getPrefWidth());
        //p2.setPrefHeight(p.placeHolder.getPrefHeight());
        //p2.setLayoutX(p.placeHolder.getLayoutX()+p.placeHolder.getPrefWidth()+pad);
        //p2.setLayoutY(p.placeHolder.getLayoutY());
        p2.prefWidthProperty().bind(placeHolder.prefWidthProperty());
        p2.prefHeightProperty().bind(placeHolder.prefHeightProperty());
        p2.layoutXProperty().bind(Bindings.add(20, // fix the 20, should be padding property
                Bindings.add(placeHolder.layoutXProperty(), placeHolder.prefWidthProperty())));
        p2.layoutYProperty().bind(placeHolder.layoutYProperty());
        p2.styleProperty().bind(placeHolder.styleProperty());
//        p2.setStyle("-fx-background-color: #"+cp2.getValue().toString().substring(2, cp2.getValue().toString().length()-2));
        getChildren().add(p2);
        
    }
    
    private void setClickableVisibility(boolean b) {
        vis = b;
        for (Node n : clickable) {
            if (n != placeHolder) {
                n.setVisible(b);
            }
        }
    }
    
    @Override
    public void setPadding(double d) {
        super.setPadding(d);
    }
    
    @Override
    public void setPrefMinSize(double w, double h) {
        super.setPrefMinSize(w, h);
    }
    
    @Override
    public void removePlaceholder() {  }
}
