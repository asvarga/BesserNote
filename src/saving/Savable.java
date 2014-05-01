/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import java.util.List;
import javafx.scene.Parent;

/**
 *
 * @author ddliu
 */
public interface Savable {
    
    public Parent create();
    public List<Savable> getChildren();
    
    
}
