/** Сравнение со средним арифметическим */

import java.util.*
import kotlin.math.abs

class AverageComparator(private val average: Int) : Comparator<Int> {
  override fun compare(x: Int, y: Int): Int {
    return abs(x - average) - abs(y - average)
  }
}