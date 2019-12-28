/** Перебор k-комбинаций */

import java.util.AbstractCollection

class CombinationsSequence(private val setSize: Int, private val subsetSize: Int) :
  AbstractCollection<Combination>() {

  override fun iterator(): MutableIterator<Combination> {
    return object : MutableIterator<Combination> {
      private var combination: Combination = Combination(subsetSize)
      private var index = 0
      private var computed = false

      init {
        combination[subsetSize - 1] = subsetSize - 2
      }

      override fun hasNext(): Boolean {
        if (!computed)
          computeNext()

        return index < subsetSize
      }

      override fun next(): Combination {
        if (hasNext())
          computed = false
        else
          throw ArrayIndexOutOfBoundsException("Конец перебора комбинаций")

        return combination
      }

      override fun remove() {
        throw UnsupportedOperationException("Ошибка удаления комбинации из последовательности комбинаций")
      }

      private fun computeNext() {
        // Поиск первого элемента, не достигшего своего максимального значения
        index = subsetSize

        for (i in subsetSize - 1 downTo 0)
          if (combination[i] < setSize - subsetSize + i) {
            index = i
            break
          }

        // Найден элемент, не достигший своего максимального значения
        if (index < subsetSize) {
          // Поиск следующей комбинации в лексикографическом порядке
          combination[index] = combination[index] + 1

          for (i in index + 1 until subsetSize)
            combination[i] = combination[i - 1] + 1
        }

        computed = true
      }
    }
  }

  // Размер биномиального коэффициента в битах
  override val size: Int
    get() = log2(binomialCoefficient(setSize, subsetSize))

  // Число сочетаний из n по k
  private fun binomialCoefficient(n: Int, k: Int): Long =
    if (k == 0) 1 else n * binomialCoefficient(n - 1, k - 1) / k

  companion object {
    @JvmStatic
    fun log2(n: Long): Int {
      var m = n
      var result = 0
      while (m > 0) {
        m = m shr 1
        result++
      }
      return result
    }
  }
}