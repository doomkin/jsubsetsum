/**
 * Class FixedSizeSolver implements computational process for enumerating 
 * k-combinations.Combinations are enumerated in the order, prefer 
 * a subsets with the most average items. Performed in the task queue.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.Date;
import java.util.List;

public class FixedSizeSolver implements Callable<Combination> 
{
    private List<Integer> _list;
    private Integer _sum;
    private int _subsetSize;
    private PrintStream _log;
    
    public FixedSizeSolver() {}
    
    public FixedSizeSolver(List<Integer> list, int sum, int subsetSize, 
            PrintStream log)
    {
        _list = list;
        _sum = sum;
        _subsetSize = subsetSize;
        _log = log;
    }
    
    public Combination call() 
    {
        Date start = new Date();

        CombinationsSequence combinations =
                new CombinationsSequence(_list.size(), _subsetSize);
        
        if (_log != null)
            _log.println(String.format("%1$" + 2 + "s", _subsetSize) + 
                    "  start    bc " + 
                    String.format("%1$-" + 20 + "s", combinations.bc()) + 
                    String.format("%1$-" + 2 + "s", combinations.size()) + 
                    " bit");
        
        Combination solution = null;

        // check the combinations of a given size
        for (Combination combination : combinations)
        {
            if (Thread.currentThread().isInterrupted())
                break;
            
            // calculate sum by combination
            int sum = 0;
            for (int index : combination)
                sum += _list.get(index);
            
            // check solution
            if (sum == _sum)
            {
                solution = combination;
                break;
            }
        }
        
        
        if (_log != null && !Thread.currentThread().isInterrupted())
            _log.println("    done  " + 
                    String.format("%1$-" + 2 + "s", _subsetSize) + 
                    " in " + Program.duration(start, new Date()));
        
        return solution;
    }
}
