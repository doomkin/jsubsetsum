/** Перечисления k-комбинации в порядке, предпочитающем средние элементы */

import java.io.PrintStream
import java.util.concurrent.Callable

class FixedSizeSolver
constructor(
  private val list: List<Int>,
  private val sum: Int?,
  private val subsetSize: Int,
  private val log: PrintStream?
) : Callable<Combination> {

  override fun call(): Combination? {
    val timer = Timer(log)
    val combinationsSeq = CombinationsSequence(list.size, subsetSize)
    log?.println("Перебор подмножеств из $subsetSize элемент${ending(subsetSize)}...")
    var solution: Combination? = null

    // Проверка суммы комбинации
    for (combination in combinationsSeq) {
      if (Thread.currentThread().isInterrupted)
        break

      var sum = 0
      for (index in combination)
        sum += list[index]

      if (sum == this.sum) {
        solution = combination
        break
      }
    }

    if (!Thread.currentThread().isInterrupted)
      log?.println("Завершён перебор подмножеств из $subsetSize элемент${ending(subsetSize)} ($timer)")

    return solution
  }

  private fun ending(integer: Int): String {
    return when (integer % 10) {
      1 -> "а"
      else -> "ов"
    }
  }
}