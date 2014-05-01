/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import java.util.Optional;

/**
 *
 * @author avarga
 */
public interface BChange {

    abstract void redo();
    abstract void undo();

    Optional<BChange> mergeWith(BChange other);
};
