/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker.placement;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Bind any property to any double.
 * Good for restricting drag to 0-1 dimensions.
 * Please note that this is the hackiest sh!t ever.
 * I blame javafx.
 * @author avarga
 */

public class EasyBind {
    
    static Pane pane = new Pane();
    
    static void bind(Property p, double d) {
        p.bind(pane.layoutXProperty().add(d));
    }
    
}
