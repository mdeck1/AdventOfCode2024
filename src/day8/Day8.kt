import java.io.File
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Day8 {
    companion object {
        private const val FILENAME: String = "day8\\day8data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()

            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            var antennaMap: MutableMap<Char, MutableList<Point>> = mutableMapOf()

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j].isLetterOrDigit()) {
                        if (antennaMap.contains(matrix[i][j])) {
                            antennaMap[matrix[i][j]]?.add(Point(i, j))
                        } else {
                            var newList:MutableList<Point> = mutableListOf(Point(i, j))
                            antennaMap[matrix[i][j]] = newList
                        }
                    }
                }
            }

            var antinodePositions:MutableSet<Point> = mutableSetOf()

            for (antennaList in antennaMap.values) {
                for (i in 0..<antennaList.size) {
                    for (j in (i+1)..<antennaList.size) {
                        val a = antennaList[i]
                        val b = antennaList[j]
                        // before a
                        val beforeA: Point = Point(a.x - (b.x-a.x), a.y - (b.y-a.y))
                        if (beforeA.inBounds(rowLength, colLength) &&
                            abs(2 * beforeA.dist(a) - beforeA.dist(b)) < 0.0001) {
                            antinodePositions.add(beforeA)
                        }
                        // after b
                        val afterB: Point = Point(b.x + (b.x-a.x), b.y + (b.y-a.y))
                        if (afterB.inBounds(rowLength, colLength) &&
                            abs(afterB.dist(a) - 2 * afterB.dist(b)) < 0.0001) {
                            antinodePositions.add(afterB)
                        }
                        // between, closest to a
                        val betweenNearA:Point = Point(a.x + (b.x-a.x)/3, a.y + (b.y-a.y)/3)
                        if (betweenNearA.inBounds(rowLength, colLength) &&
                            (b.x - a.x) % 3 == 0 &&
                            (b.y - a.y) % 3 == 0 &&
                            abs(betweenNearA.dist(a) + betweenNearA.dist(b) - a.dist(b)) < 0.0001) {
                            antinodePositions.add(betweenNearA)
                        }
                        // between, closest to b
                        val betweenNearB:Point = Point(a.x + 2*(b.x-a.x)/3, a.y + 2*(b.y-a.y)/3)
                        if (betweenNearB.inBounds(rowLength, colLength) &&
                            (2*(b.x - a.x)) % 3 == 0 &&
                            (2*(b.y - a.y)) % 3 == 0 &&
                            abs(betweenNearB.dist(a) + betweenNearB.dist(b) - a.dist(b)) < 0.0001) {
                            antinodePositions.add(betweenNearB)
                        }
                    }
                }
            }

            println(antinodePositions.size)

//            println()
//            for (i in 0..colLength) {
//                for (j in 0..rowLength) {
//                    if (matrix[i][j].isLetterOrDigit()) {
//                        print(matrix[i][j])
//                    } else if (antinodePositions.contains(Point(i, j))) {
//                        print('#')
//                    } else {
//                        print('.')
//                    }
//                }
//                println()
//            }
        }

        fun part2() {

            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()

            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            var antennaMap: MutableMap<Char, MutableList<Point>> = mutableMapOf()

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j].isLetterOrDigit()) {
                        if (antennaMap.contains(matrix[i][j])) {
                            antennaMap[matrix[i][j]]?.add(Point(i, j))
                        } else {
                            var newList:MutableList<Point> = mutableListOf(Point(i, j))
                            antennaMap[matrix[i][j]] = newList
                        }
                    }
                }
            }

            var antinodePositions:MutableSet<Point> = mutableSetOf()

            for (antennaList in antennaMap.values) {
                for (i in 0..<antennaList.size) {
                    for (j in (i+1)..<antennaList.size) {
                        val a = antennaList[i]
                        val b = antennaList[j]
                        var xDist:Int = a.x - b.x
                        var yDist:Int = a.y - b.y
                        xDist /= gcd(xDist, yDist)
                        yDist /= gcd(xDist, yDist)
                        var mult:Int = 0
                        while (true) {
                            val newPoint = Point(a.x - (mult*xDist), a.y - (mult*yDist))
                            if (newPoint.inBounds(rowLength, colLength)) {
                                antinodePositions.add(newPoint)
                                mult += 1
                            } else {
                                break
                            }
                        }
                        mult = 1
                        while (true) {
                            val newPoint = Point(a.x + (mult*xDist), a.y + (mult*yDist))
                            if (newPoint.inBounds(rowLength, colLength)) {
                                antinodePositions.add(newPoint)
                                mult += 1
                            } else {
                                break
                            }
                        }
                    }
                }
            }

            println(antinodePositions.size)

//            println()
//            for (i in 0..colLength) {
//                for (j in 0..rowLength) {
//                    if (matrix[i][j].isLetterOrDigit()) {
//                        print(matrix[i][j])
//                    } else if (antinodePositions.contains(Point(i, j))) {
//                        print('#')
//                    } else {
//                        print('.')
//                    }
//                }
//                println()
//            }
        }

        private class Point(x:Int, y:Int) {
            val x:Int = x
            val y:Int = y

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Point) return false
                return this.x == other.x && this.y == other.y
            }

            override fun hashCode(): Int {
                return "($x,$y)".hashCode()
            }

            fun dist(other: Point) : Float {
                return sqrt((this.x - other.x).toFloat().pow(2) + (this.y - other.y).toFloat().pow(2))
            }

            fun inBounds(rowLen:Int, colLen:Int): Boolean {
                return x in 0..rowLen && y in 0..colLen
            }
        }

        private fun gcd(a:Int, b:Int) :Int {
            var num1 = a
            var num2 = b
            while (num2 != 0) {
                val temp = num2
                num2 = num1 % num2
                num1 = temp
            }
            return abs(num1)
        }
    }
}