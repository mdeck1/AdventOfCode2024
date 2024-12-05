import java.io.File
import kotlin.math.abs
import kotlin.math.min

class Day4 {

    companion object {
        private const val FILENAME:String = "day4\\day4data.txt"
        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: List<List<Char>> = lines.map { s -> s.toList() }
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1
            var count = 0
            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    //backwards
                    if (j >= 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i][j-1] == 'M' &&
                        matrix[i][j-2] == 'A' &&
                        matrix[i][j-3] == 'S') {
                        count++
                    }
                    //forwards
                    if (j <= rowLength - 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i][j+1] == 'M' &&
                        matrix[i][j+2] == 'A' &&
                        matrix[i][j+3] == 'S') {
                        count++
                    }
                    //up
                    if (i >= 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i-1][j] == 'M' &&
                        matrix[i-2][j] == 'A' &&
                        matrix[i-3][j] == 'S') {
                        count++
                    }
                    //down
                    if (i <= colLength - 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i+1][j] == 'M' &&
                        matrix[i+2][j] == 'A' &&
                        matrix[i+3][j] == 'S') {
                        count++
                    }
                    //upleft
                    if (i >= 3 && j >= 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i-1][j-1] == 'M' &&
                        matrix[i-2][j-2] == 'A' &&
                        matrix[i-3][j-3] == 'S') {
                        count++
                    }
                    //upright
                    if (i >= 3 && j <= rowLength - 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i-1][j+1] == 'M' &&
                        matrix[i-2][j+2] == 'A' &&
                        matrix[i-3][j+3] == 'S') {
                        count++
                    }
                    //downleft
                    if (i <= colLength - 3 && j >= 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i+1][j-1] == 'M' &&
                        matrix[i+2][j-2] == 'A' &&
                        matrix[i+3][j-3] == 'S') {
                        count++
                    }
                    //downright
                    if (i <= colLength - 3 && j <= rowLength - 3 &&
                        matrix[i][j] == 'X' &&
                        matrix[i+1][j+1] == 'M' &&
                        matrix[i+2][j+2] == 'A' &&
                        matrix[i+3][j+3] == 'S') {
                        count++
                    }
                }

            }
            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: List<List<Char>> = lines.map { s -> s.toList() }
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1
            var count = 0
            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] == 'A' && i > 0 && i < colLength && j > 0 && j < rowLength) {
                        if (matrix[i-1][j-1] == 'M' &&
                            matrix[i-1][j+1] == 'M' &&
                            matrix[i+1][j-1] == 'S' &&
                            matrix[i+1][j+1] == 'S') {
                            count++ //a
                        } else if (matrix[i-1][j-1] == 'M' &&
                            matrix[i-1][j+1] == 'S' &&
                            matrix[i+1][j-1] == 'M' &&
                            matrix[i+1][j+1] == 'S') {
                            count++ //a
                        } else if (matrix[i-1][j-1] == 'S' &&
                            matrix[i-1][j+1] == 'M' &&
                            matrix[i+1][j-1] == 'S' &&
                            matrix[i+1][j+1] == 'M') {
                            count++ //a
                        } else if (matrix[i-1][j-1] == 'S' &&
                            matrix[i-1][j+1] == 'S' &&
                            matrix[i+1][j-1] == 'M' &&
                            matrix[i+1][j+1] == 'M') {
                            count++ //a
                        }
                    }
                }

            }
            println(count)
        }
    }
}