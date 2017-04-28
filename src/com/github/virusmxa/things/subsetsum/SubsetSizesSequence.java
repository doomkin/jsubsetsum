/**
 * Class SubsetSizeSequence enumerates the sizes of subsets 
 * in ascending order of deviation from the average subset size.
 * 
 * Input: average subset size, set size
 * Output: subset size sequence
 * 
 * Examples: 
 * 
 * for averageSize = 3, setSize = 6 the sequence is {3, 2, 4, 1, 5, 6}
 * for averageSize = 5, setSize = 6 the sequence is {5, 4, 6, 3, 2, 1}
 * 
 * @author Pavel Nikitin
 * License: GNU GPLv3
 * Modified: 2013/04/02
 */
package com.github.virusmxa.things.subsetsum;

import java.util.AbstractCollection;
import java.util.Iterator;

public class SubsetSizesSequence extends AbstractCollection<Integer> 
{
    private int _setSize;
    private int _averageSize;

    public SubsetSizesSequence(int setSize, int averageSize)
    {
        _setSize = setSize;
        _averageSize = averageSize;
    }

    @Override
    public Iterator<Integer> iterator() 
    {
        return new Iterator<Integer>() 
        {
            private int _deviation = 0; // deviation from the average
            private int _direction = 1; // right direction by default

            public boolean hasNext() 
            {
                // one of the boundaries is not reached
                return _averageSize - _deviation >= 1 || 
                       _averageSize + _deviation <= _setSize;
            }
            
            public Integer next() 
            {
                // calculate the current value
                int value = _averageSize + _deviation * _direction;

                // increase the deviation, if right direction 
                // or one of the boundaries is reached
                if (_direction == 1 || 
                        _averageSize - _deviation < 1 ||
                        _averageSize + _deviation > _setSize)
                    _deviation++;

                // calculate the direction
                if (hasNext())
                {
                    // change direction, if both boundaries is not reached
                    if (_averageSize - _deviation >= 1 && 
                            _averageSize + _deviation <= _setSize)
                        _direction *= -1;
                    
                    // move right, if left boundary is reached
                    else if (_averageSize - _deviation < 1)
                        _direction = 1;
    
                    // move left, if right boundary is reached
                    else if (_averageSize + _deviation > _setSize)
                        _direction = -1;
                }
                
                return value;
            }
            
            public void remove() 
            { 
                throw new UnsupportedOperationException(
                        "Cannot remove subset size from sequence.");
            }
        };
    }

    @Override
    public int size() 
    {
        return _setSize;
    }
}
