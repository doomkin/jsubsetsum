/**
 * Class Combination creates a combination of a given size in initial state.
 * 
 * @author Pavel Nikitin
 * License: GNU GPLv3
 * Modified: 2013/04/02
 */
package com.github.virusmxa.things.subsetsum;

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
