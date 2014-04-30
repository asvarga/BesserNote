/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import bessernote.nodemaker.placement.EasyBind;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 *
 * @author avarga
 */
public class PlacementChange implements Change {
    
    protected final Region node;
    
    protected final String oldMode;
    protected final double oldPad;
    protected final double oldX;
    protected final double oldY;
    protected final double oldWidth;
    protected final double oldHeight;
    protected final String newMode;
    protected final double newPad;
    protected final double newX;
    protected final double newY;
    protected final double newWidth;
    protected final double newHeight;

    protected PlacementChange(
            Region node,
            String oldMode, double oldPad, 
            double oldX, double oldY, double oldWidth, double oldHeight,
            String newMode, double newPad, 
            double newX, double newY, double newWidth, double newHeight) {
        this.node = node;
        this.oldMode = oldMode;
        this.oldPad = oldPad;
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newMode = newMode;
        this.newPad = newPad;
        this.newX = newX;
        this.newY = newY;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public void redo() {
        node.setLayoutX(newX);
        node.setLayoutY(newY);
        if (oldMode.equals("Manual")) {
            node.setPrefWidth(newWidth);
            node.setPrefHeight(newHeight);
        } else {
            Region parent = (Region) node.parentProperty().getValue();
            node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*newPad), 0));
            node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*newPad), 0));
        }
        
    };
    
    @Override
    public void undo() {
        node.setLayoutX(oldX);
        node.setLayoutY(oldY);
        if (oldMode.equals("Manual")) {
            node.setPrefWidth(oldWidth);
            node.setPrefHeight(oldHeight);
        } else {
            Region parent = (Region) node.parentProperty().getValue();
            node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*oldPad), 0));
            node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*oldPad), 0));
        }
    };

    @Override
    public Optional<Change> mergeWith(Change other) {
        if (other instanceof PlacementChange) {
            PlacementChange pc = (PlacementChange) other;
            if (node == pc.node) {
                return Optional.of((Change) new PlacementChange(
                    node, oldMode, oldPad, oldX, oldY, oldWidth, oldHeight,
                    pc.oldMode, pc.oldPad, 
                    pc.oldX, pc.oldY, pc.oldWidth, pc.oldHeight));
            }
        }
        return Optional.empty();
    }

}
