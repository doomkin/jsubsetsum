/**
 * Class Combination creates a combination of a given size in initial state.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Combination extends ArrayList<Integer> 
{
    // Create the combination of a given size
    public Combination(int size) 
    {
        super(size);

        // get first elements of list
        for (int index = 0; index < size; index++)
            add(index);
    }
    
    // Create the combination by integer collection
    public Combination(Collection<? extends Integer> integerCollection) 
    {
        super(integerCollection);
    }
}
