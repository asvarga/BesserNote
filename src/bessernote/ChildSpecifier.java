/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote;

import java.util.List;
import javafx.scene.Node;

/**
 * Objects implementing this specify which children can be superClicked.
 * @author avarga
 */
public interface ChildSpecifier {
    public List<Node> specifyChildren();
    public Node specifySelf();
}
