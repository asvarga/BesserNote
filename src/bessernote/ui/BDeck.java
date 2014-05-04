/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class BDeck extends BWrapPane {
    
    public BUndoManager undoManager;
    public List<Node> cards;
    public int index = -1;
    
    public Pane top;
    public Button shuffle;
    public Button prev;
    public Button next;
    public Button make;
//    public Button delete;

    public BDeck(BUndoManager undoManager) {
        super(undoManager);
        
        cards = new ArrayList<Node>();
        
        top = new Pane();
        shuffle = new Button("Shuff");
        shuffle.setPrefSize(55, 25);
        shuffle.setLayoutX(5);
        shuffle.setLayoutY(5);
        shuffle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Collections.shuffle(cards);
                showCard(index);
            }
        });
        prev = new Button("Prev");
        prev.setPrefSize(55, 25);
        prev.setLayoutX(65);
        prev.setLayoutY(5);
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                showCard((index-1+cards.size())%cards.size());
            }
        });
        next = new Button("Next");
        next.setPrefSize(55, 25);
        next.setLayoutX(125);
        next.setLayoutY(5);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                showCard((index+1)%cards.size());
            }
        });
        make = new Button("Make");
        make.setPrefSize(55, 25);
        make.setLayoutX(185);
        make.setLayoutY(5);
        make.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // Should copy current card instead
                BFlashCard card = new BFlashCard(undoManager);
                card.setPrefMinSize(getWidth()-2*padding, getHeight()-2*padding);
                card.setLayoutX(padding);
                card.setLayoutY(padding);
                card.setPadding(20);    // fix this
                card.setStyle("-fx-background-color: #CCCCCC");
                card.placeHolder.setStyle("-fx-background-color: #666666");
                getChildren().add(card);
            }
        });
//        delete = new Button("Del");
//        delete.setPrefSize(55, 20);
//        delete.setLayoutX(245);
//        delete.setLayoutY(5);
        top.getChildren().add(shuffle);
        top.getChildren().add(prev);
        top.getChildren().add(next);
        top.getChildren().add(make);
//        top.getChildren().add(delete);
        top.setPrefWidth(245);
        top.prefHeightProperty().bind(Bindings.add(10, prev.prefHeightProperty()));
        top.layoutYProperty().bind(Bindings.negate(top.heightProperty()));
        
        addOther(top);
        top.styleProperty().bind(this.styleProperty());
        //top.visibleProperty().bind(this.focusedProperty());
        
        final BWrapPane this2 = this;
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
                            if (!others.contains(remItem) && cards.contains(remItem)) {
                                cards.remove(remItem);
                                if (cards.size() == 0) {
                                    ((Pane) this2.getParent()).getChildren().remove(this2);
                                }  else {
                                    showCard(index%cards.size());
                                }
                            }
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            if (!others.contains(addItem)) {
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        cards.add(index+1, addItem);
                                        showCard(index+1);
                                    }                 
                                });
                            }
                        }
                        //fixOutline();
                    }
                }
                
//                if (getChildren().size() == 0) {
//                    ((Pane)this2.getParent()).getChildren().remove(this2);
//                } else {
//                    fixOutline();
//                }
                fixOutline();
            }
        });
        
    }
    
    public void showCard(int i) {
        index = i;
        for (Node n : cards) {
            n.setVisible(false);
        }
        cards.get(index).setVisible(true);
    }
    
    
}
