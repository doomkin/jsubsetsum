/**
 * Class AverageComparator compares the deviations from the mean.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */ 
package com.github.doomkin.jsubsetsum;

import java.util.Comparator;

public class AverageComparator implements Comparator<Integer> 
{
    private int _average;
    
    public AverageComparator(int average) 
    {
        _average = average;
    }

    public int compare(Integer x, Integer y) 
    {
        return Math.abs(x - _average) - Math.abs(y - _average);
    }
}
