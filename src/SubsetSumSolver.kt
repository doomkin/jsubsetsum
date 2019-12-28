/** Параллельный статистический алгоритм для решения задачи о сумме подмножества */

import java.io.PrintStream
import java.util.ArrayList
import java.util.Collections
import java.util.concurrent.Callable

import java.lang.Integer.max

class SubsetSumSolver(list: List<Int>, private val sum: Int, private val log: PrintStream?) :
  Callable<ArrayList<Int>> {
  private val list: List<Int> = ArrayList(list)

  override fun call(): ArrayList<Int>? {
    var timer = Timer(log)

    // Сумма элементов исходного набора
    var totalSum = 0
    for (integer in list)
        totalSum += integer

    // Среднее арифметическое исходного набора
    val average = totalSum / list.size

    // Средний размер искомого подмножества
    val averageSize = max(sum / average, 1)

    // Проверка существования решения
    if (averageSize < 1 || averageSize > list.size ||
      CombinationsSequence.log2(sum.toLong()) > 31 ||
      !solutionExists(list, sum)
    ) {
      log?.println(" Решения не существует ($timer)")
      return null
    }

    log?.println(" OK ($timer)\n")
    timer = Timer(log)

    // Сортировка исходного набора в порядке возрастания отклонения от среднего
    Collections.sort(list, AverageComparator(average))

    // Создание процессов для перечисления k-комбинаций
    var tasks: TaskQueue<Combination>? = null
    var solution: ArrayList<Int>? = null

    // Решение задачи
    try {
      tasks = TaskQueue(Runtime.getRuntime().availableProcessors())

      // Процессы созданы в порядке возрастания отклонения от среднего размера искомого подмножества
      for (size in SubsetSizesSequence(list.size, averageSize))
        tasks.addLast(FixedSizeSolver(list, sum, size, log))

      // Поиск решающей задачу комбинации
      val combination = tasks.invokeAny()

      // Формирование решения
      if (combination != null) {
        solution = ArrayList(combination.size)
        for (index in combination)
          solution.add(list[index])
      }
    } finally {
      tasks!!.close()
    }

    if (log != null) {
      if (solution != null)
        log.println("\nНайдено ${solution.size} элементов ($timer)")
      else
        log.print("Решения не существует ($timer)")
    }

    return solution
  }

  // Проверка существования решения методом динамического программирования
  private fun solutionExists(list: List<Int>, sum: Int): Boolean {
    log?.print("Проверка существования решения")

    // Значение subset[i][j] истинно, если существует подмножество subset[0..j-1] с суммой, равной i
    val n = list.size
    val subset = BitMatrix(sum + 1, n + 1)

    // Для нулевой суммы есть решение - пустое подмножество
    for (i in 0..n)
      subset[0, i] = true

    // Для пустого исходного набора решения нет
    for (i in 1..sum)
      subset[i, 0] = false

    // Заполнение таблицы подмножеств
    var i = 1
    val step = max(sum / 10, 1)
    while (i <= sum) {
      if (log != null && i % step == 0)
        log.print(".")

      for (j in 1..n) {
        subset[i, j] = subset[i, j - 1]
        if (i >= list[j - 1])
          subset[i, j] = subset[i, j] || subset[i - list[j - 1], j - 1]
      }
      i++
    }

    return subset[sum, n]
  }
}