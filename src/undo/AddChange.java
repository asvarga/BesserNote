/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 *
 * @author avarga
 */
public class AddChange extends BChange {
    
    Node n;
    Pane p;
    
    public AddChange(Node n, Pane p) {
        this.n = n;
        this.p = p;
    }

    @Override
    void redo() {
        p.getChildren().add(n);
    }

    @Override
    void undo() {
        p.getChildren().remove(n);
    }
    
}
