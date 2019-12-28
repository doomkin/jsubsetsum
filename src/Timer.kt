import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.Date

class Timer
constructor(private val log: PrintStream?) {
  private var start = Date()

  override fun toString(): String {
    val stop = Date()
    val milliseconds = (stop.time - start.time).toDouble()
    val seconds = milliseconds / 1000
    return when {
      seconds < 1 -> String.format("%.0f", milliseconds) + " мсек"
      seconds < 10 -> String.format("%.2f", seconds) + " сек"
      seconds < 100 -> String.format("%.0f", seconds) + " сек"
      seconds < 5000 -> String.format("%.0f", seconds / 60) + " мин"
      else -> String.format("%.0f", seconds / 3600) + " ч"
    }
  }

  companion object {
    @JvmStatic
    fun now(format: String = "dd.MM.yyyy HH:mm"): String {
      val dateFormat = SimpleDateFormat(format)
      return dateFormat.format(Date())
    }
  }
}