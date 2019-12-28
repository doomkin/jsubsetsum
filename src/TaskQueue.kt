/** Многопоточная очередь задач */

import java.util.*
import java.util.concurrent.*

class TaskQueue<R>
  (nThreads: Int) {
  private val executor = Executors.newFixedThreadPool(nThreads)
  private val completionService: CompletionService<R>
  private val futures: MutableList<Future<R>>

  init {
    completionService = ExecutorCompletionService(executor)
    futures = ArrayList()
  }

  fun addLast(task: Callable<R>) {
    futures.add(completionService.submit(task))
  }

  fun invokeAny(): R? {
    var result: R? = null

    try {
      var i = 0
      while (i < futures.size && result == null) {
        result = completionService.take().get()
        i++
      }
    } catch (e: InterruptedException) {
    } catch (e: ExecutionException) {
    } finally {
      for (future in futures)
        future.cancel(true)
    }

    return result
  }

  fun close() {
    executor.shutdownNow()
  }
}