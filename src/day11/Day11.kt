import java.io.File

class Day11 {
    companion object {
        private const val FILENAME: String = "day11\\day11data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            var stoneList: MutableList<String> = lines[0].split(" ").toMutableList()
            for (i in 0..24) {
                stoneList = simulateBlink(stoneList)
            }

            println(stoneList.size)
        }

        private fun simulateBlink(stoneList: MutableList<String>) : MutableList<String> {
            val newList:MutableList<String> = mutableListOf()
            for (i in stoneList.indices) {
                val curr: String = stoneList[i]
                if (curr.toLong() == 0L) {
                    newList.addLast("1")
                } else if (curr.length % 2 == 0) {
                    newList.addLast(curr.substring(0, curr.length/2).toLong().toString())
                    newList.addLast(curr.substring(curr.length/2).toLong().toString())
                } else {
                    newList.addLast((curr.toLong()*2024L).toString())
                }
            }
            return newList
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            var stoneList: MutableList<String> = lines[0].split(" ").toMutableList()
            var stoneListMap: MutableMap<String, Long> = mutableMapOf()
            // Note: presumes uniqueness of numbers in the input list, which happens to be true
            for (elem in stoneList) {
                stoneListMap[elem] = 1
            }
            for (i in 0..74) {
                println("Starting iteration ($i) with list size: ${stoneList.size}")
                stoneListMap = simulateBlinkWithCounts(stoneListMap)
                println("Max num after ($i) steps is: " + stoneList.max())
            }

            var count:Long = 0L
            for (key in stoneListMap.keys) {
                count += stoneListMap[key]!!
            }

            println(count)
        }

        private fun simulateBlinkWithCounts(stoneListMap: MutableMap<String, Long>) : MutableMap<String, Long> {
            val newMap:MutableMap<String, Long> = mutableMapOf()
            for (i in stoneListMap.keys) {
                val curr: String = i
                if (curr.toLong() == 0L) {
                    newMap["1"] = newMap.getOrDefault("1", 0L) + stoneListMap[curr]!!
                } else if (curr.length % 2 == 0) {
                    val firstHalf:String = i.substring(0, curr.length/2).toLong().toString()
                    val secondHalf:String = i.substring(curr.length/2).toLong().toString()
                    newMap[firstHalf] = newMap.getOrDefault(firstHalf, 0L) + stoneListMap[curr]!!
                    newMap[secondHalf] = newMap.getOrDefault(secondHalf, 0L) + stoneListMap[curr]!!
                } else {
                    val newNum:String = (curr.toLong()*2024L).toString()
                    newMap[newNum] = newMap.getOrDefault(newNum, 0L) + stoneListMap[curr]!!
                }
            }
            return newMap
        }

    }
}