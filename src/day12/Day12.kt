import java.io.File
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class Day12 {
    companion object {
        private const val FILENAME: String = "day12\\day12data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()

            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            val cellVisited: MutableList<MutableList<Boolean>> = lines.map { s -> s.map { _ -> false }.toMutableList() }.toMutableList()
            var sections: MutableList<MutableList<Point>> = mutableListOf()

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (!cellVisited[i][j]) {
                        sections.add(floodFill(matrix, cellVisited, i, j))
                    }
                }
            }

            var count:Long = 0L

            for (section in sections) {
                var sectionArea:Int = 0
                var sectionPerimeter:Int = 0
                for (space in section) {
                    sectionArea++
                    if (Point(space.x-1, space.y) !in section ) {
                        sectionPerimeter++
                    }
                    if (Point(space.x+1, space.y) !in section ) {
                        sectionPerimeter++
                    }
                    if (Point(space.x, space.y-1) !in section ) {
                        sectionPerimeter++
                    }
                    if (Point(space.x, space.y+1) !in section ) {
                        sectionPerimeter++
                    }
                }
                count += sectionArea*sectionPerimeter
            }

            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()

            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1

            val cellVisited: MutableList<MutableList<Boolean>> = lines.map { s -> s.map { _ -> false }.toMutableList() }.toMutableList()
            var sections: MutableList<Section> = mutableListOf()

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (!cellVisited[i][j]) {
                        sections.add(floodFillSection(matrix, cellVisited, i, j))
                    }
                }
            }

            var count:Long = 0L

            for (section in sections) {
                count += section.coalesceSegmentsAndCalculateValue()
            }

            println(count)
        }

        private fun floodFill(matrix:MutableList<MutableList<Char>>, cellVisited:MutableList<MutableList<Boolean>>, x: Int, y:Int) : MutableList<Point> {
            var adjacencyQueue: Queue<Point> = LinkedBlockingQueue()
            adjacencyQueue.add(Point(x, y))
            var section:MutableList<Point> = mutableListOf()
            while (adjacencyQueue.isNotEmpty()) {
                var curr:Point = adjacencyQueue.remove()
                if (curr.inBounds(matrix) && !cellVisited[curr.x][curr.y] && matrix[curr.x][curr.y] == matrix[x][y]) {
                    section.add(curr)
                    cellVisited[curr.x][curr.y] = true
                    adjacencyQueue.add(Point(curr.x, curr.y-1))
                    adjacencyQueue.add(Point(curr.x, curr.y+1))
                    adjacencyQueue.add(Point(curr.x-1, curr.y))
                    adjacencyQueue.add(Point(curr.x+1, curr.y))
                }
            }
            return section
        }

        private fun floodFillSection(matrix:MutableList<MutableList<Char>>, cellVisited:MutableList<MutableList<Boolean>>, x: Int, y:Int) : Section {
            val section:Section = Section()
            var adjacencyQueue: Queue<Point> = LinkedBlockingQueue()
            adjacencyQueue.add(Point(x, y))
            while (adjacencyQueue.isNotEmpty()) {
                var curr:Point = adjacencyQueue.remove()
                if (curr.inBounds(matrix) && !cellVisited[curr.x][curr.y] && matrix[curr.x][curr.y] == matrix[x][y]) {
                    section.pointList.add(curr)
                    cellVisited[curr.x][curr.y] = true
                    adjacencyQueue.add(Point(curr.x, curr.y-1))
                    adjacencyQueue.add(Point(curr.x, curr.y+1))
                    adjacencyQueue.add(Point(curr.x-1, curr.y))
                    adjacencyQueue.add(Point(curr.x+1, curr.y))
                }
            }
            for (curr:Point in section.pointList) {
                val adjacentPoints:List<Point> =
                    listOf(
                        Point(curr.x, curr.y-1),
                        Point(curr.x, curr.y+1),
                        Point(curr.x-1, curr.y),
                        Point(curr.x+1, curr.y))
                for (point:Point in adjacentPoints) {
                    if (!point.inBounds(matrix) || !cellVisited[point.x][point.y] ||
                        matrix[point.x][point.y] != matrix[curr.x][curr.y]) {
                        if (point.y == curr.y) {
                            val nX:Int = min(point.x, curr.x)
                            section.segmentList.addLast(
                                Segment(
                                    Point(nX, curr.y-1),
                                    Point(nX, curr.y)))
                        } else {
                            val nY:Int = min(point.y, curr.y)
                            section.segmentList.addLast(
                                Segment(
                                    Point(curr.x-1, nY),
                                    Point(curr.x, nY)))
                        }
                    }
                }
            }
            return section
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

            fun inBounds(matrix:List<List<Char>>): Boolean {
                return x in matrix.indices && y in matrix[0].indices
            }
        }

        private class Segment(p1: Point, p2: Point) {
            var p1:Point = p1
            var p2:Point = p2
        }

        private fun createSegment(p1:Point, p2:Point) : Segment {
            if (p1.x < p2.x || (p1.x == p2.x && p1.y < p2.y)) {
                return Segment(p1, p2)
            } else {
                return Segment(p2, p1)
            }
        }

        private class Section() {
            val pointList:MutableList<Point> = mutableListOf()
            val segmentList:MutableList<Segment> = mutableListOf()

            fun coalesceSegmentsAndCalculateValue() : Long {
                var verticalSegments:MutableList<Segment> = mutableListOf()
                var horizontalSegments:MutableList<Segment> = mutableListOf()
                for (segment:Segment in segmentList) {
                    if (segment.p1.x != segment.p2.x) {
                        horizontalSegments.addLast(segment)
                    } else {
                        verticalSegments.addLast(segment)
                    }
                }
                horizontalSegments = horizontalSegments.sortedWith(compareBy<Segment> {
                    it.p1.y
                }.thenBy {
                    it.p1.x
                }).toMutableList()
                verticalSegments = verticalSegments.sortedWith(compareBy<Segment> {
                    it.p1.x
                }.thenBy {
                    it.p1.y
                }).toMutableList()

                horizontalSegments = zip(horizontalSegments, true)
                verticalSegments = zip(verticalSegments, false)
                var changed:Boolean = true
                while (changed) {
                    changed = false
                    for (h in horizontalSegments) {
                        for (v in verticalSegments) {
                            if (h.p1.y > v.p1.y && h.p1.y < v.p2.y &&
                                        v.p1.x > h.p1.x && v.p1.x < h.p2.x) {
                                var newHPoint:Point = Point(v.p1.x, h.p1.y)
                                var newVPoint:Point = Point(v.p1.x, h.p1.y)
                                horizontalSegments.remove(h)
                                horizontalSegments.add(Segment(h.p1, newHPoint))
                                horizontalSegments.add(Segment(newHPoint, h.p2))
                                verticalSegments.remove(v)
                                verticalSegments.add(Segment(v.p1, newVPoint))
                                verticalSegments.add(Segment(newVPoint, v.p2))
                                changed = true
                                break
                            }
                        }
                        if (changed) break
                    }
                }
                return pointList.size.toLong() * (horizontalSegments.size.toLong() + verticalSegments.size.toLong())
            }

            private fun zip(segmentList:MutableList<Segment>, isHorizontal:Boolean) : MutableList<Segment> {
                var newList : MutableList<Segment> = mutableListOf()
                var i:Int = 0
                while (i < segmentList.size) {
                    var j:Int = i + 1
                    while (j < segmentList.size &&
                        ((isHorizontal && segmentList[j-1].p2.x == segmentList[j].p1.x && segmentList[j-1].p2.y == segmentList[j].p1.y) ||
                        (!isHorizontal && segmentList[j-1].p2.y == segmentList[j].p1.y && segmentList[j-1].p2.x == segmentList[j].p1.x))) {
                        j++
                    }
                    newList.addLast(createSegment(segmentList[i].p1, segmentList[j-1].p2))
                    i = j
                }
                return newList
            }
        }


    }
}