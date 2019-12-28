/** Битовая матрица */

class BitMatrix(private val rows: Int, cols: Int) {
  private val matrix: Array<BitVector?> = arrayOfNulls(rows)

  init {
    for (row in 0 until rows)
      matrix[row] = BitVector(cols)
  }

  operator fun get(row: Int, col: Int): Boolean {
    return matrix[row]!!.get(col)
  }

  operator fun set(row: Int, col: Int, value: Boolean) {
    matrix[row]!!.set(col, value)
  }

  override fun toString(): String {
    var result = ""

    for (row in 0 until rows)
      result += "${matrix[row].toString()}\n"

    return result
  }
}