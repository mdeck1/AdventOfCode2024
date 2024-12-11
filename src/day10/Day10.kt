import java.io.File

class Day10 {
    companion object {
        private const val FILENAME: String = "day10\\day10data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Int>> = lines.map {
                s -> s.toMutableList().map { c -> c.digitToInt() }.toMutableList()
            }.toMutableList()
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            var count:Long = 0

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] == 0) {
                        count += computeScores(matrix, Point(i, j), 0).size
                    }
                }
            }

            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Int>> = lines.map {
                    s -> s.toMutableList().map { c -> c.digitToInt() }.toMutableList()
            }.toMutableList()
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            var count:Long = 0

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] == 0) {
                        count += computeRatings(matrix, Point(i, j), 0)
                    }
                }
            }

            println(count)
        }

        private fun computeRatings(matrix:List<List<Int>>, curr:Point, value:Int) : Int {
            if (!curr.inBounds(matrix) || matrix[curr.x][curr.y] != value) {
                return 0
            }
            if (value == 9) {
                return 1
            }
            val pointUp:Point = Point(curr.x, curr.y-1)
            val pointDown:Point = Point(curr.x, curr.y+1)
            val pointLeft:Point = Point(curr.x-1, curr.y)
            val pointRight:Point = Point(curr.x+1, curr.y)

            var ratings:Int = computeRatings(matrix, pointUp, value+1)
            ratings += computeRatings(matrix, pointDown, value+1)
            ratings += computeRatings(matrix, pointLeft, value+1)
            ratings += computeRatings(matrix, pointRight, value+1)

            return ratings
        }

        private fun computeScores(matrix:List<List<Int>>, curr:Point, value:Int) : MutableSet<Point> {
            if (!curr.inBounds(matrix) || matrix[curr.x][curr.y] != value) {
                return mutableSetOf()
            }
            if (value == 9) {
                return mutableSetOf(curr)
            }
            val pointUp:Point = Point(curr.x, curr.y-1)
            val pointDown:Point = Point(curr.x, curr.y+1)
            val pointLeft:Point = Point(curr.x-1, curr.y)
            val pointRight:Point = Point(curr.x+1, curr.y)

            var points:MutableSet<Point> = computeScores(matrix, pointUp, value+1)
            points.addAll(computeScores(matrix, pointDown, value+1))
            points.addAll(computeScores(matrix, pointLeft, value+1))
            points.addAll(computeScores(matrix, pointRight, value+1))

            return points
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

            fun inBounds(rowLen:Int, colLen:Int): Boolean {
                return x in 0..rowLen && y in 0..colLen
            }

            fun inBounds(matrix:List<List<Int>>): Boolean {
                return x in matrix[0].indices && y in matrix.indices
            }
        }
    }
}