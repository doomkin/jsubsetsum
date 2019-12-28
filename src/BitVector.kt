/** Битовый вектор */

import java.util.*

class BitVector(length: Int) : BitSet(length) {
  override fun toString(): String {
    var result = ""

    for (i in 0 until this.length())
      result += if (get(i)) "1" else "0"

    return result
  }
}