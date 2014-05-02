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
public class DeleteChange extends BChange {
    
    Node n;
    Pane p;
    
    public DeleteChange(Node n, Pane p) {
        this.n = n;
        this.p = p;
    }

    @Override
    void redo() {
        p.getChildren().remove(n);
    }

    @Override
    void undo() {
        p.getChildren().add(n);
    }
    
}
