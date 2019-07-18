/**
 * Class SubsetSumSolver implements parallel statistical solver of 
 * subset sum problem. The existence of solution is checked by 
 * Dynamic Programming.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class SubsetSumSolver implements Callable<ArrayList<Integer>>
{
    private List<Integer> _list;
    private int _sum;
    private PrintStream _log;
    
    public SubsetSumSolver(List<Integer> list, int sum, PrintStream log)
    {
        _list = new ArrayList<Integer>(list);
        _sum = sum;
        _log = log;
    }
    
    public ArrayList<Integer> call() 
    {
        Date start = new Date();

        // calculate the sum of elements of list
        int totalSum = 0;
        for (int integer : _list)
            totalSum += integer;
        
        // calculate the average
        int average = totalSum / _list.size();
        
        // calculate the average subset size
        int averageSize = _sum / average;
        
        // check solution existence by Dynamic Programming
        if (averageSize < 1 || averageSize > _list.size() ||
                CombinationsSequence.lg2(_sum) > 31 || 
                !isSubsetSumDP(_list, _sum))
        {
            if (_log != null)
                _log.println("\nNo solution in " + Program.duration(start, new Date()));

            return null;
        }
            
        if (_log != null)
            _log.println("");

        // sort the list in ascending order of deviations from the average
        Collections.sort(_list, new AverageComparator(average));

        // creates computational processes for enumerating k-combinations
        TaskQueue<Combination> tasks = null;
        ArrayList<Integer> solution = null;
        
        // solve the problem
        try 
        {
            tasks = new TaskQueue<Combination>(
                    Runtime.getRuntime().availableProcessors());
            
            // processes are created in ascending order of deviations from 
            // the average size of the desired subset
            for (int size : new SubsetSizesSequence(_list.size(), averageSize))
                tasks.addLast(new FixedSizeSolver(_list, _sum, size, _log));
            
            // find combination for the solution
            Combination combination = tasks.invokeAny();
            
            // collect the solution
            if (combination != null)
            {
                solution = new ArrayList<Integer>(combination.size());
                for (int index : combination)
                    solution.add(_list.get(index));
            }
        }
        finally 
        {
            tasks.close();
        }
        
        if (_log != null)
        {
            if (solution != null)
                _log.println("\n" + solution.size() + " items found in " + 
                        Program.duration(start, new Date()));
            else
                _log.print("No solution in " + 
                        Program.duration(start, new Date()));
        }
        
        return solution;
    }
    
    // Dynamic Programming of Subset Sum Problem
    private boolean isSubsetSumDP(List<Integer> list, int sum)
    {
        if (_log != null)
            _log.print("Check solution existence");
        
        // the value of subset[i][j] will be true 
        // if there is a subset of set[0..j-1] with sum equal to i
        int n = list.size();
        BitMatrix subset = new BitMatrix(sum + 1, n + 1);
     
        // if sum is 0, then answer is true
        for (int i = 0; i <= n; i++)
            subset.set(0, i, true);
     
        // if sum is not 0 and set is empty, then answer is false
        for (int i = 1; i <= sum; i++)
            subset.set(i, 0, false);
     
        // fill the subset table
        for (int i = 1, step = Math.max(sum / 10, 1); i <= sum; i++)
        {
            if (_log != null && i % step == 0)
                _log.print(".");
            
            for (int j = 1; j <= n; j++)
            {
                subset.set(i, j, subset.get(i, j - 1));
                if (i >= list.get(j - 1))
                    subset.set(i, j, 
                            subset.get(i, j) || 
                            subset.get(i - list.get(j - 1), j - 1));
             }
        }
        
        if (_log != null)
            _log.println("");
     
        return subset.get(sum, n);
    }
}
