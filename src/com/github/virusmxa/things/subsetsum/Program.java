/**
 * PARALLEL STATISTICAL ALGORITHM FOR SOLVING SUBSET SUM PROBLEM
 * 
 * In computer science, the subset sum problem is an important problem in 
 * complexity theory. The problem is this: from a given set of natural numbers 
 * want to select elements whose sum is a given natural number. The problem is 
 * NP-complete.
 * 
 * The proposed algorithm is faster to find subset, if the arithmetic mean 
 * of the initial set close to the average of the desired subset. 
 * The algorithm sorts the set in ascending order of deviations from the mean, 
 * and creates computational processes for enumerating k-combinations. 
 * Processes are created in ascending order of deviations from the average size 
 * of the desired subset. Combinations are enumerated in the order, prefer 
 * a subsets with the most average items. 
 * 
 * Input: subset sum, set of natural numbers (Integer integers)
 *        from console or file if specified in first command line parameter 
 *
 * Example:
 * 35
 * 12
 * 7
 * 15
 * 36
 * 20
 * 
 * Output: desired subset if it exists, else 0
 *         to console or file if specified in second command line parameter
 * 
 * Example:
 * 20
 * 15
 *
 * @author Pavel Nikitin
 * License: GNU GPLv3
 * Modified: 2013/04/02
 */
package com.github.virusmxa.things.subsetsum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

// Input data from console, call SubsetSumSolver, output result to console 
public class Program 
{
    private static Integer _sum;    
    
    // Entry point
    public static void main(String[] args) 
    {
        try
        {
            // show title
            System.out.println("SUBSET SUM PROBLEM SOLVER by Pavel Nikitin");
            
            // input and check data
            List<Integer> _list = inputData(args.length > 0 ? args[0] : "");
            
            if (_list != null)
            {
	            // Show start time, processors and other
	            showTaskParameters(_list.size());
	            
	            // solve problem
	            SubsetSumSolver solver = 
	                    new SubsetSumSolver(_list, _sum, System.out);
	            
	            List<Integer> solution = solver.call();
	            
	            // output result
	            outputData(solution, args.length > 1 ? args[1] : "");
            }
        }
        catch (Exception e)
        {
            System.err.println("Unexpected exception: " + e.getMessage());
        }
    }
    
    // Format duration time
    public static String duration(Date start, Date done)
    {
        double seconds = (double)(done.getTime() - start.getTime()) / 1000;
        String result = "";
        
        if (seconds < 10)
            result = String.format("%.3f", seconds) + " seconds";
        else if (seconds < 100)
            result = String.format("%.0f", seconds) + " seconds";
        else if (seconds < 5000)
            result = String.format("%.0f", seconds / 60) + " minutes";
        else 
            result = String.format("%.0f", seconds / 3600) + " hours";
        
        return result;
    }
    
    // Input data
    private static List<Integer> inputData(String fileName)
    {
        System.out.println("");
        System.out.println("Reading subset sum and items of set...");
        List<Integer> list = null;
        InputStream in = System.in; // console input by default
        boolean useInputFile = fileName != ""; 
        
        try 
        {
            // use input file from first command line parameter
            if (useInputFile) 
                in = new FileInputStream(fileName);
            else
                System.out.println("empty string is the end of input");
            
            list = readData(in);
        } 
        catch (IOException e) 
        {
            if (useInputFile) 
                System.err.println("IOException: cannot read " + fileName);
            else
                System.err.println("IOException: cannot read console");
        
            return null;
        }
        catch (NumberFormatException e) 
        {
            System.err.println(e.getMessage());
            return null;
        } 
        catch (IllegalArgumentException e) 
        {
            System.err.println(e.getMessage());
            return null;
        } 
        finally 
        {
            if (useInputFile)
                try 
                {
                    in.close();
                }
                catch (IOException e) {}
        }
        
        return list;
    }
    
    // Output results
    private static void outputData(List<Integer> solution, String fileName)
    {
        System.out.println("");
        
        if (solution == null)
        {
            // add zero to output
            solution = new ArrayList<Integer>(1);
            solution.add(0);
        }
        
        System.out.println("Writing items of subset...");
        PrintStream out = System.out; // console output by default
        boolean useOutputFile = fileName != "";
        
        try 
        {
            // use output file from second command line parameter
            if (useOutputFile) 
                out = new PrintStream(new FileOutputStream(fileName));
            
            writeData(solution, out);
        }
        catch (IOException e) 
        {
            if (useOutputFile) 
                System.err.println("IOException: cannot write " + fileName);
            else
                System.err.println("IOException: cannot write to console");
        }
        finally 
        {
            if (useOutputFile) 
                out.close();
        }        
    }
    
    // Show start time, processors, subset sum, set size, average subset size
    private static void showTaskParameters(int listSize)
    {
        int processors = Runtime.getRuntime().availableProcessors();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("");
        System.out.println("Start time: " + dateFormat.format(date));
        System.out.println("Processors: " + processors);
        System.out.println("Subset sum: " + _sum);
        System.out.println("Set size: " + listSize);
        System.out.println("");
    }
    
    // Read subset sum and set of items
    private static List<Integer> readData(InputStream in) 
            throws IOException, NumberFormatException, IllegalArgumentException
    {
        List<Integer> list = new ArrayList<Integer>(64);
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        String line = "";
        
        try {
            // read subset sum
            line = input.readLine();
            if (line != null && !line.equals("")) 
            {
                _sum = Integer.parseInt(line);
                if (_sum < 1)
                    throw new IllegalArgumentException(
                            "IllegalArgumentException: " + line + 
                            " is not positive");
            }
            else
                throw new IllegalArgumentException(
                        "IllegalArgumentException: enter subset sum");                
    
            // read set
            do 
            {
                line = input.readLine();
                if (line != null && !line.equals("")) 
                {
                    Integer item = Integer.parseInt(line);
                    if (item < 1) 
                        throw new IllegalArgumentException(
                                "IllegalArgumentException: " + line + 
                                " is not positive");
                    list.add(item);
                }
            } 
            while (line != null && !line.equals(""));
            
            if (list.size() == 0)
                throw new IllegalArgumentException(
                        "IllegalArgumentException: enter at least one item");                
        } 
        catch (NumberFormatException e) 
        {
            throw new NumberFormatException(
                    "NumberFormatException: " + line + " is not Integer");
        } 
        
        return list;
    }
    
    // Write desired subset
    private static void writeData(List<Integer> solution, PrintStream out) 
            throws IOException
    {
        for (int item : solution)
            out.println(item);
    }
}