import java.io.File
import kotlin.math.abs

class Day1 {
    companion object {
        fun part1() {
            val file: File = Utils.getFile("day1\\day1data.txt")
            val lines: List<String> = file.readLines()
            val leftLine: MutableList<Int> = ArrayList()
            val rightLine: MutableList<Int> = ArrayList()
            for (line in lines) {
                val nums: List<String> = line.split("\\s+".toRegex())
                leftLine.add(nums[0].toInt())
                rightLine.add(nums[1].toInt())
            }
            leftLine.sort()
            rightLine.sort()
            var sum = 0
            leftLine.zip(rightLine).forEach { pair: Pair<Int, Int> ->
                sum += abs(pair.first - pair.second)
//                println("|" + pair.first + " - " + pair.second + "| = " + abs(pair.first - pair.second))
            }
            println(sum)
        }

        fun part2() {
            val file: File = Utils.getFile("day1\\day1data.txt")
            val lines: List<String> = file.readLines()
            val leftLine: MutableList<Int> = ArrayList()
            val rightLine: MutableList<Int> = ArrayList()
            for (line in lines) {
                val nums: List<String> = line.split("\\s+".toRegex())
                leftLine.add(nums[0].toInt())
                rightLine.add(nums[1].toInt())
            }
            val map: MutableMap<Int, Int> = HashMap()
            for (num in rightLine) {
                map.put(num, (map.getOrDefault(num, 0) + 1))
            }
            var sum = 0
            for (int in leftLine) {
                sum += int * map.getOrDefault(int, 0)
            }
            println(sum)
        }
    }
}