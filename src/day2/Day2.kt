import java.io.File
import kotlin.math.abs
import kotlin.math.min

class Day2 {
    companion object {
        fun part1() {
            val file: File = Utils.getFile("day2\\day2data.txt")
            val lines: List<String> = file.readLines()
            var sum = 0
            for (line in lines) {
                val nums: List<Int> = line.split("\\s+".toRegex()).map { s -> s.toInt() }
                if (checkLine(nums) == 0) {
                    sum++
                }
            }
            println(sum)
        }

        fun part2() {
            val file: File = Utils.getFile("day2\\day2data.txt")
            val lines: List<String> = file.readLines()
            var sum = 0
            for (line in lines) {
                val nums:  MutableList<Int> = line.split("\\s+".toRegex()).map { s -> s.toInt() }.toMutableList()
                var index = checkLine(nums)
                if (index != 0) {
                    var nums2 = nums.toMutableList()
                    nums.removeAt(index)
                    nums2.removeAt(index - 1)
                    index = min(checkLine(nums), checkLine(nums2))
                }
                if (index == 0) {
                    sum++
                }
            }
            println(sum)
        }

        /**
         * returns 0 if a line is ok, otherwise returns the index of the first problematic number in the list
         */
        private fun checkLine(nums:List<Int>): Int {
            val isIncreasing:Boolean = nums[0] < nums[nums.lastIndex]
            val endIndex = nums.lastIndex-1
            for (i in 0..endIndex) {
                if (
                    (isIncreasing && nums[i] >= nums[i+1]) ||
                    (!isIncreasing && nums[i] <= nums[i+1]) ||
                    abs(nums[i+1] - nums[i]) > 3) {
                    return i+1
                }
            }
            return 0
        }
    }
}