import java.io.File

class Day9 {
    companion object {
        private const val FILENAME: String = "day9\\day9data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val line: String = file.readLines()[0]
            var totalSize:Int = 0
            for (i in line.indices) {
                totalSize += line[i].digitToInt()
            }
            var memoryMap:MutableList<String> = mutableListOf()
            for (i in line.indices) {
                for (j in 0..<line[i].digitToInt()) {
                    if (i%2 == 0) {
                        memoryMap.add((i/2).toString())
                    } else {
                        memoryMap.add(".")
                    }
                }
            }
            var left:Int = 0
            var right:Int = memoryMap.size - 1
            while (left < right) {
                while (left < memoryMap.size && memoryMap[left] != ".") {
                    left++
                }
                while (right >= 0 && memoryMap[right] == ".") {
                    right--
                }
                if (left >= right) {
                    break
                }
                memoryMap[left] = memoryMap[right]
                memoryMap[right] = "."
                left++
                right--
            }
            var count:Long = 0
            for (i in memoryMap.indices) {
                if (memoryMap[i].toIntOrNull() != null) {
                    count += memoryMap[i].toInt()*i
                }
            }
            println(count)
        }

        private fun printCharList(list:List<Char>) {
            for (c in list) {
                print(c.toString())
            }
            println()
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val line: String = file.readLines()[0]
            var totalSize:Int = 0
            for (i in line.indices) {
                totalSize += line[i].digitToInt()
            }
            var memoryLinkedListHead:MemoryChunkNode =
                MemoryChunkNode(line[0].digitToInt(), 0, false, 0, null, null)
            var currNode:MemoryChunkNode = memoryLinkedListHead
            var index:Int = memoryLinkedListHead.size
            var nums:MutableList<MemoryChunkNode> = mutableListOf()
            var gaps:MutableList<MemoryChunkNode> = mutableListOf()
            for (i in 1..<line.length) {
                var newNode:MemoryChunkNode =
                    MemoryChunkNode(
                        line[i].digitToInt(),
                         if (i%2 == 0) (i/2) else null,
                        i%2!=0,
                        index,
                        currNode,
                        null)
                index += newNode.size
                currNode.next = newNode
                if (i%2 == 0) {
                    nums.addLast(newNode)
                } else {
                    gaps.addLast(newNode)
                }
                currNode = newNode
            }
//            printMemoryChunkNodeLinkedList(memoryLinkedListHead)

            try {
                while (nums.isNotEmpty()) {
                    val currChunk: MemoryChunkNode = nums.removeLast()
                    for (gap in gaps) {
                        if (gap.size >= currChunk.size) {
                            if (gap.startIndex > currChunk.startIndex) {
                                break
                            }
                            gap.size -= currChunk.size
                            val newGap: MemoryChunkNode =
                                MemoryChunkNode(currChunk.size, null, true, currChunk.startIndex, currChunk.prev, currChunk.next)
                            currChunk.prev!!.next = newGap
                            if (currChunk.next != null) {
                                currChunk.next!!.prev = newGap
                            }
                            currChunk.prev = gap.prev
                            currChunk.next = gap
                            currChunk.startIndex = gap.startIndex
                            gap.startIndex += currChunk.size
                            gap.prev!!.next = currChunk
                            gap.prev = currChunk
                            break
                        }
                    }
                }
            } catch (e:Exception) {
                println(e)
            }

//            printMemoryChunkNodeLinkedList(memoryLinkedListHead)

            println(calculateChecksum(memoryLinkedListHead))

//            var count:Long = 0
//
//            println(count)
        }

        private fun printMemoryChunkNodeLinkedList(head:MemoryChunkNode) {
            var curr:MemoryChunkNode? = head
            while (curr != null) {
                for (i in 0..<curr.size) {
                    if (curr.value != null) {
                        print(String.format("%4d,", curr.value))
                    } else {
                        print("....,")
                    }
                }
                curr = curr.next
            }
            println()
        }

        private fun calculateChecksum(head:MemoryChunkNode): Long {
            var count:Long = 0
            var index:Int = 0
            var curr:MemoryChunkNode? = head
            while (curr != null) {
                for (i in 0..<curr.size) {
                    if (!curr.isFree) {
                        count += index * curr.value!!
                    }
                    index++
                }
                curr = curr.next
            }
            return count
        }


        private class MemoryChunkNode(size:Int, value:Int?, isFree:Boolean, startIndex:Int, prev: MemoryChunkNode?, next:MemoryChunkNode?) {
            var size:Int = size
            val value:Int? = value
            val isFree:Boolean = isFree
            var startIndex:Int = startIndex
            var prev:MemoryChunkNode? = prev
            var next:MemoryChunkNode? = next
        }
    }
}