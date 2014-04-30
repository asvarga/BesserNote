/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 *
 * @author avarga
 */
public class BLabel extends Label implements ChildSpecifier {
    
    public BLabel(String s) {
        super(s);
    }

    @Override
    public List<Node> specifyChildren() {
        return new ArrayList<Node>();
    }

    @Override
    public Node specifySelf() {
        return this;
    }
    
}
