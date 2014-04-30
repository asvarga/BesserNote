/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author avarga
 */
public class BFlashCard extends BWrapPane {
    public Pane front = new Pane();
    boolean vis = true;
    boolean clicked = true;
    boolean mouseOver = false;
    
    public BFlashCard() {
        super();
        front.layoutXProperty().bind(placeHolder.layoutXProperty());
        front.layoutYProperty().bind(placeHolder.layoutYProperty());
        front.prefWidthProperty().bind(placeHolder.prefWidthProperty());
        front.prefHeightProperty().bind(placeHolder.prefHeightProperty());
        
        front.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver = true;
                setClickableVisibility(true);
            };
        });
        
        front.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver = false;
                setClickableVisibility(clicked);
            };
        });
        
        front.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clicked = !clicked;
                setClickableVisibility(clicked);
            };
        });
        
        getChildren().add(front);
        
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
        
    }
    
    private void setClickableVisibility(boolean b) {
        vis = b;
        for (Node n : clickable) {
            if (n != front) {
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
}
