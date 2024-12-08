import org.w3c.dom.events.MutationEvent
import java.io.File

class Day7 {
    companion object {
        private const val FILENAME:String = "day7\\day7data.txt"
        private const val ADD:Long = 0;
        private const val MULT:Long = 1;
        private const val CONCAT:Long = 2;

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            var count:Long = 0
            for (line in lines) {
                val calibrationResult: Long = line.substringBefore(':').toLong()
                val operands: MutableList<Long> =
                    Regex("[0-9]+").findAll(line.substringAfter(": "))
                        .map(MatchResult::value).toList().map{ s -> s.toLong() }.toMutableList()
                if (startTestList(calibrationResult, operands)) {
                    count += calibrationResult
                }
            }

            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            var count:Long = 0
            for (line in lines) {
                val calibrationResult: Long = line.substringBefore(':').toLong()
                val operands: MutableList<Long> =
                    Regex("[0-9]+").findAll(line.substringAfter(": "))
                        .map(MatchResult::value).toList().map{ s -> s.toLong() }.toMutableList()
                if (startTestListWithConcat(calibrationResult, operands)) {
                    count += calibrationResult
                }
            }

            println(count)
        }

        private fun startTestList(target: Long, operands: MutableList<Long>): Boolean {
            return testList(target, operands, ADD) || testList(target, operands, MULT)
        }

        private fun testList(target: Long, operands: MutableList<Long>, operation: Long): Boolean {
            if (operands.size == 1) {
                return operands[0] == target
            }
            var newOperands:MutableList<Long> = operands.toMutableList()
            val a = newOperands.removeFirst()
            val b = newOperands.removeFirst()
            if (operation == ADD) {
                newOperands.addFirst(a + b)
            } else {
                newOperands.addFirst(a * b)
            }
            return testList(target, newOperands, ADD) || testList(target, newOperands, MULT)
        }

        private fun startTestListWithConcat(target: Long, operands: MutableList<Long>): Boolean {
            return testListWithConcat(target, operands, ADD) || testListWithConcat(target, operands, MULT) || testListWithConcat(target, operands, CONCAT)
        }

        private fun testListWithConcat(target: Long, operands: MutableList<Long>, operation: Long): Boolean {
            if (operands.size == 1) {
                return operands[0] == target
            }
            var newOperands:MutableList<Long> = operands.toMutableList()
            val a = newOperands.removeFirst()
            val b = newOperands.removeFirst()
            if (operation == ADD) {
                newOperands.addFirst(a + b)
            } else if (operation == MULT) {
                newOperands.addFirst(a * b)
            } else {
                newOperands.addFirst((a.toString() + b.toString()).toLong())
            }
            return testListWithConcat(target, newOperands, ADD) || testListWithConcat(target, newOperands, MULT) || testListWithConcat(target, newOperands, CONCAT)
        }
    }
}