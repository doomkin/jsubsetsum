/** Перечисление размеров подмножеств в порядке возрастания отклонения от среднего размера */

import java.util.AbstractCollection

class SubsetSizesSequence(private val setSize: Int, private val averageSize: Int) : AbstractCollection<Int>() {
  override fun iterator(): MutableIterator<Int> {
    return object : MutableIterator<Int> {
      private var deviation = 0 // отклонение от среднего размера
      private var direction = 1 // направо по умолчанию

      override fun hasNext(): Boolean {
        // Не достигнута одна из границ (левая или правая)
        return averageSize - deviation >= 1 || averageSize + deviation <= setSize
      }

      override fun next(): Int {
        val value = averageSize + deviation * direction

        // Увеличение отколнения
        if (direction == 1 ||
          averageSize - deviation < 1 ||
          averageSize + deviation > setSize
        )
          deviation++

        // Вычисление направления
        if (hasNext()) {
          // Смена направления, если обе границы не достигнуты
          if (averageSize - deviation >= 1 && averageSize + deviation <= setSize)
            direction *= -1
          // Направо, если левая граница достигнута
          else if (averageSize - deviation < 1)
            direction = 1
          // Налево, если правая граница достигнута
          else if (averageSize + deviation > setSize)
            direction = -1
        }

        return value
      }

      override fun remove() {
        throw UnsupportedOperationException("Ошибка удаления размера подмножества из посделовательности")
      }
    }
  }

  override val size: Int
    get() = setSize
}