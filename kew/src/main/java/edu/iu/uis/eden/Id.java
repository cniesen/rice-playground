package edu.iu.uis.eden;

import java.io.Serializable;

/**
 * Superinterface of UserId and GroupId
 * @author Aaron Hamid (arh14 at cornell dot edu)
 */
public interface Id extends Serializable {
    /**
     * Returns true if this Id has an empty value. Empty Ids can't be used as keys in a Hash,
     * among other things.
     *
     * @return true if this instance doesn't have a value
     */
    public boolean isEmpty();
}