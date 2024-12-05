import java.io.File

class Day5 {

    companion object {
        private const val FILENAME:String = "day5\\day5data.txt"
        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val map: MutableMap<Int, MutableList<Int>> = mutableMapOf()
            var rules = true
            var count = 0
            for (i in lines.indices) {
                if (lines[i] == "") {
                    rules = false
                } else if (rules) {
                    val a:Int = lines[i].split("|")[0].toInt()
                    val b:Int = lines[i].split("|")[1].toInt()
                    if (!map.contains(a)) {
                        map[a] = mutableListOf(b)
                    } else {
                        map.getOrDefault(a, mutableListOf()).add(b)
                    }
                } else {
                    val nums:List<Int> = lines[i].split(",").map({s -> s.toInt()})
                    var valid = true
                    for (m in nums.indices) {
                        for (n in (m+1)..<nums.size) {
                            if (map.getOrDefault(nums[n], mutableListOf()).contains(nums[m])) {
                                valid = false
                                break
                            }
                        }
                        if (!valid) {
                            break
                        }
                    }
                    if (valid) {
                        count += nums[nums.size / 2]
                    }
                }
            }
            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val map: MutableMap<Int, MutableList<Int>> = mutableMapOf()
            var rules = true
            var count = 0
            for (i in lines.indices) {
                if (lines[i] == "") {
                    rules = false
                } else if (rules) {
                    val a:Int = lines[i].split("|")[0].toInt()
                    val b:Int = lines[i].split("|")[1].toInt()
                    if (!map.contains(a)) {
                        map[a] = mutableListOf(b)
                    } else {
                        map.getOrDefault(a, mutableListOf()).add(b)
                    }
                } else {
                    val nums:MutableList<Int> = lines[i].split(",").map({s -> s.toInt()}).toMutableList()
                    var valid = true
                    var m = 0
                    while (m < nums.size) {
                        for (n in (m+1)..<nums.size) {
                            if (map.getOrDefault(nums[n], mutableListOf()).contains(nums[m])) {
                                valid = false
                                val temp = nums[n]
                                nums[n] = nums[m]
                                nums[m] = temp
                                m = -1
                                break
                            }
                        }
                        m++
                    }
                    if (!valid) {
                        count += nums[nums.size / 2]
                    }
                }
            }
            println(count)
        }
    }
}