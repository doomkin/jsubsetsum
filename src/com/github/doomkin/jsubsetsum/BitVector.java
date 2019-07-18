/**
 * Class BitVector implements bit vector using BitSet.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

import java.util.BitSet;

@SuppressWarnings("serial")
public class BitVector extends BitSet 
{
    private int _length;
    
    public BitVector(int length) 
    {
        super(length);
        _length = length;
    }
    
    public BitVector(BitVector v) 
    {
        super(v.length());

        _length = v.length();
        
        for (int i = 0; i < _length; i++)
            set(i, v.get(i));
    }
    
    public int length() 
    { 
        return _length; 
    }
    
    public String toString() 
    {
        String result = "";
        
        for (int i = 0; i < _length; i++)
            if (get(i))
                result += "1 ";
            else
                result += "0 ";
        
        return result;
    }
}
