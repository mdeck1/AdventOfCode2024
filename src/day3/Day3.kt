import java.io.File
import kotlin.math.abs
import kotlin.math.min

class Day3 {

    companion object {
        private const val FILENAME:String = "day3\\day3data.txt"
        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val line: String = file.readText()
            val matches = Regex("mul\\((\\d){1,3},(\\d){1,3}\\)").findAll(line)
            val muls: MutableList<String> = matches.map { it.groupValues[0] }.toMutableList()
            var sum = 0
            for (mul in muls) {
                val a: Int = Regex("\\(\\d{1,3},").find(mul)!!.value.substring(1).dropLast(1).toInt()
                val b: Int = Regex(",\\d{1,3}\\)").find(mul)!!.value.substring(1).dropLast(1).toInt()
                sum += a * b
            }
            println(sum)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val line: String = file.readText()
            val matches = Regex("(mul\\((\\d){1,3},(\\d){1,3}\\))|(do\\(\\))|(don't\\(\\))").findAll(line)
            val commands: MutableList<String> = matches.map { it.groupValues[0] }.toMutableList()
            var sum = 0
            var enabled = true
            for (command in commands) {
                if (enabled && Regex("mul\\((\\d){1,3},(\\d){1,3}\\)").findAll(command).any()) {
                    val a: Int = Regex("\\(\\d{1,3},").find(command)!!.value.substring(1).dropLast(1).toInt()
                    val b: Int = Regex(",\\d{1,3}\\)").find(command)!!.value.substring(1).dropLast(1).toInt()
                    sum += a * b
                } else if (Regex("do\\(\\)").findAll(command).any()) {
                    enabled = true
                } else {
                    enabled = false
                }
            }
            println(sum)
        }
    }
}