import java.io.File

class Day6 {

    companion object {
        private const val FILENAME:String = "day6\\day6data.txt"
        private const val UP:Int = 0
        private const val RIGHT:Int = 1
        private const val DOWN:Int = 2
        private const val LEFT:Int = 3
        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1
            var guardX:Int = -1
            var guardY:Int = -1
            var guardDir:Int = -1
            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] == '^') {
                        guardX = j
                        guardY = i
                        guardDir = UP
                        break
                    } else if (matrix[i][j] == '>') {
                        guardX = j
                        guardY = i
                        guardDir = RIGHT
                        break
                    } else if (matrix[i][j] == 'v') {
                        guardX = j
                        guardY = i
                        guardDir = DOWN
                        break
                    } else if (matrix[i][j] == '<') {
                        guardX = j
                        guardY = i
                        guardDir = LEFT
                        break
                    }
                }
            }
            while (guardX in 0..colLength &&
                   guardY in 0..rowLength) {
                if (guardDir == UP) {
                    if (guardY > 0 && matrix[guardY-1][guardX] == '#') {
                        guardDir = RIGHT
                    } else {
                        if (guardY > 0) {
                            matrix[guardY - 1][guardX] = '^'
                        }
                        matrix[guardY][guardX] = 'X'
                        guardY -= 1
                    }
                } else if (guardDir == RIGHT) {
                    if (guardX < rowLength && matrix[guardY][guardX+1] == '#') {
                        guardDir = DOWN
                    } else {
                        if (guardX < rowLength) {
                            matrix[guardY][guardX + 1] = '>'
                        }
                        matrix[guardY][guardX] = 'X'
                        guardX += 1
                    }
                } else if (guardDir == DOWN) {
                    if (guardY < colLength && matrix[guardY+1][guardX] == '#') {
                        guardDir = LEFT
                    } else {
                        if (guardY < colLength) {
                            matrix[guardY + 1][guardX] = 'v'
                        }
                        matrix[guardY][guardX] = 'X'
                        guardY += 1
                    }
                } else if (guardDir == LEFT) {
                    if (guardX > 0 && matrix[guardY][guardX-1] == '#') {
                        guardDir = UP
                    } else {
                        if (guardX > 0) {
                            matrix[guardY][guardX - 1] = '<'
                        }
                        matrix[guardY][guardX] = 'X'
                        guardX -= 1
                    }

                }
            }
            var count: Int = 0

            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    print(matrix[i][j])
                    if (matrix[i][j] == 'X') {
                        ++count
                    }
                }
                println()
            }
            println(count)
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()
            val matrix: MutableList<MutableList<Char>> = lines.map { s -> s.toMutableList() }.toMutableList()
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1
            var count: Int = 0
            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] != '^' &&
                        matrix[i][j] != '>' &&
                        matrix[i][j] != 'v' &&
                        matrix[i][j] != '<' &&
                        matrix[i][j] != '#') {
                        var newMatrix = copyMatrix(matrix)
                        newMatrix[i][j] = '#'
                        if (!doesGuardExitMatrixInSteps(newMatrix, matrix.size * matrix.size)) {
                            count++
                        }
                    }
                }
            }
            println(count)
        }

        private fun copyMatrix(matrix: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
            var newMatrix: MutableList<MutableList<Char>> = mutableListOf()
            for (i in 0..<matrix.size) {
                newMatrix.add(matrix[i].toMutableList())
            }
            return newMatrix
        }

        private fun doesGuardExitMatrixInSteps(matrix: MutableList<MutableList<Char>>, steps: Int) : Boolean {
            val rowLength = matrix[0].size - 1
            val colLength = matrix.size - 1
            var guardX:Int = -1
            var guardY:Int = -1
            var guardDir:Int = -1
            for (i in 0..colLength) {
                for (j in 0..rowLength) {
                    if (matrix[i][j] == '^') {
                        guardX = j
                        guardY = i
                        guardDir = UP
                        break
                    } else if (matrix[i][j] == '>') {
                        guardX = j
                        guardY = i
                        guardDir = RIGHT
                        break
                    } else if (matrix[i][j] == 'v') {
                        guardX = j
                        guardY = i
                        guardDir = DOWN
                        break
                    } else if (matrix[i][j] == '<') {
                        guardX = j
                        guardY = i
                        guardDir = LEFT
                        break
                    }
                }
            }
            var stepCount = 0
            while (guardX in 0..colLength &&
                guardY in 0..rowLength &&
                stepCount < steps) {
                if (guardDir == UP) {
                    if (guardY > 0 && matrix[guardY-1][guardX] == '#') {
                        guardDir = RIGHT
                    } else {
                        if (guardY > 0) {
                            matrix[guardY - 1][guardX] = '^'
                        }
                        guardY -= 1
                    }
                } else if (guardDir == RIGHT) {
                    if (guardX < rowLength && matrix[guardY][guardX+1] == '#') {
                        guardDir = DOWN
                    } else {
                        if (guardX < rowLength) {
                            matrix[guardY][guardX + 1] = '>'
                        }
                        guardX += 1
                    }
                } else if (guardDir == DOWN) {
                    if (guardY < colLength && matrix[guardY+1][guardX] == '#') {
                        guardDir = LEFT
                    } else {
                        if (guardY < colLength) {
                            matrix[guardY + 1][guardX] = 'v'
                        }
                        guardY += 1
                    }
                } else if (guardDir == LEFT) {
                    if (guardX > 0 && matrix[guardY][guardX-1] == '#') {
                        guardDir = UP
                    } else {
                        if (guardX > 0) {
                            matrix[guardY][guardX - 1] = '<'
                        }
                        guardX -= 1
                    }
                }
                stepCount++
            }
            return stepCount < steps
        }
    }
}