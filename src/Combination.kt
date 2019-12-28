/** k-комбинация */

import java.util.*

class Combination(size: Int) : ArrayList<Int>(size) {
  init {
    for (index in 0 until size)
      add(index)
  }
}