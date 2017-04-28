/**
 * Class TaskQueue implements a multi-threaded execution of the task queue.
 * 
 * @author Pavel Nikitin
 * License: GNU GPLv3
 * Modified: 2013/04/02
 */
package com.github.virusmxa.things.subsetsum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskQueue<R> // implements AutoCloseable // since 1.7
{
    private ExecutorService _executor;
    private CompletionService<R> _completionService;
    private List<Future<R>> _futures;

    public TaskQueue(int nThreads)
    {
        super();
        _executor = Executors.newFixedThreadPool(nThreads); 
        _completionService = new ExecutorCompletionService<R>(_executor);
        _futures = new ArrayList<Future<R>>();
    }

    public void addLast(Callable<R> task)
    {
        _futures.add(_completionService.submit(task));
    }
    
    public R invokeAny()
    {
        R result = null;

        // wait until a result is available
        try 
        {
            for (int i = 0; i < _futures.size() && result == null; i++)
                result = _completionService.take().get();
        }
        catch (InterruptedException e) {} 
        catch (ExecutionException e) {}
        
        // cancel all tasks
        finally 
        {
            for (Future<R> future : _futures)
                future.cancel(true);
        }
        
        return result;        
    }

    public void close() 
    {
        _executor.shutdownNow();
    }
}
