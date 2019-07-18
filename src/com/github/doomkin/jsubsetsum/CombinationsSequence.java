/**
 * Class CombinationsSequence enumerates k-combinations.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

import java.util.AbstractCollection;
import java.util.Iterator;

public class CombinationsSequence extends AbstractCollection<Combination> 
{
    private int _setSize;
    private int _subsetSize;

    public CombinationsSequence(int setSize, int subsetSize)
    {
        _setSize = setSize;
        _subsetSize = subsetSize;
    }

    @Override
    public Iterator<Combination> iterator() 
    {
        return new Iterator<Combination>() 
        {
            private Combination _combination;
            private int _index = 0;
            private boolean _computed = false;
            
            {
                // create initial combination
                _combination = new Combination(_subsetSize);
                
                // reduce the last index for first iteration of computeNext()
                _combination.set(_subsetSize - 1, _subsetSize - 2);
            }
            
            public boolean hasNext() 
            {
                if (!_computed)
                    computeNext();
      
                return _index < _subsetSize;
            }
            
            public Combination next() 
            {
                if (hasNext())
                    _computed = false;
                else
                    throw new ArrayIndexOutOfBoundsException(
                            "End of a sequence of combinations.");
                
                return _combination;
            }
            
            public void remove() 
            { 
                throw new UnsupportedOperationException(
                        "Cannot remove combination from sequence."); 
            }
            
            // Compute next combination
            private void computeNext()
            {
                // find the first item that has not reached its maximum value
                _index = _subsetSize;
                
                for (int i = _subsetSize - 1; i >= 0; i--)
                    if (_combination.get(i) < _setSize - _subsetSize + i)
                    {
                        _index = i;
                        break;
                    }
                
                // there is an item which has not reached its maximum value
                if (_index < _subsetSize)
                {    
                    // generate the next combination in lexographical order
                    _combination.set(_index, _combination.get(_index) + 1);
    
                    for (int i = _index + 1; i < _subsetSize; i++)
                        _combination.set(i, _combination.get(i-1) + 1);
                }
                
                _computed = true;
            }
        };
    }

    // Size of index of the binomial coefficient (in bits)
    @Override
    public int size() 
    {
        return lg2(bc());
    }
    
    // Compute the logarithm of number n to the base 2
    public static int lg2(long n)
    {
        int result;
        for (result = 0; n > 0; n >>= 1, result++);
        return result; 
    }

    // Compute the binomial coefficient indexed by setSize and subsetSize
    public long bc()
    {
        return bc(_setSize, _subsetSize);
    }

    // Compute the binomial coefficient indexed by n and k
    private static long bc(int n, int k)
    {
        return k == 0 ? 1 : (n * bc(n - 1, k - 1)) / k;
    }
}
