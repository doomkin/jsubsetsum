/**
 * Class AverageComparator compares the deviations from the mean.
 * 
 * @author Pavel Nikitin
 * License: GNU GPLv3
 * Modified: 2013/04/02
 */ 
package com.github.virusmxa.things.subsetsum;

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
