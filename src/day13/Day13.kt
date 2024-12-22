import java.io.File
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class Day13 {
    companion object {
        private const val FILENAME: String = "day13\\day13data.txt"

        fun part1() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()

            var count:Long = 0L
            var i:Int = 0
            while (i < lines.size) {
                var vec1:String = lines[i]
                var vec2:String = lines[i+1]
                var target:String = lines[i+2]
                i+=4

                var x1:Int = getVarValue(vec1, 'X')
                var y1:Int = getVarValue(vec1, 'Y')
                var x2:Int = getVarValue(vec2, 'X')
                var y2:Int = getVarValue(vec2, 'Y')
                var A:Int = getVarValue(target, 'X')
                var B:Int = getVarValue(target, 'Y')

                var b:Double = (x1*B - A*y1).toDouble() / (y2*x1-x2*y1)
                var a:Double = A.toDouble() / x1 - (x2*b)/x1

                if (
                    abs(b - b.roundToInt()) < 0.1 &&
                    abs(a - a.roundToInt()) < 0.1 &&
                    a.roundToInt()*x1+b.roundToInt()*x2 == A &&
                    a.roundToInt()*y1+b.roundToInt()*y2 == B &&
                    a.roundToInt() < 101 && a.roundToInt() > 0 &&
                    b.roundToInt() < 101 && b.roundToInt() > 0) {
                    count += 3*a.roundToLong() + b.roundToLong()
                }
            }

            println(count)

        }

        private fun getVarValue(str:String, c:Char) : Int {
            if (str.contains('+')) {
                var s:String = Regex("$c\\+[0-9]+").find(str)!!.value.substring(2)
                return s.toInt()
            } else {
                var s:String =  Regex("$c=[0-9]+").find(str)!!.value.substring(2)
                return s.toInt()
            }
        }

        fun part2() {
            val file: File = Utils.getFile(FILENAME)
            val lines: List<String> = file.readLines()

            var count:Long = 0L
            var i:Int = 0
            while (i < lines.size) {
                var vec1:String = lines[i]
                var vec2:String = lines[i+1]
                var target:String = lines[i+2]
                i+=4

                var x1:Long = getVarValue(vec1, 'X').toLong()
                var y1:Long = getVarValue(vec1, 'Y').toLong()
                var x2:Long = getVarValue(vec2, 'X').toLong()
                var y2:Long = getVarValue(vec2, 'Y').toLong()
                var A:Long = getVarValue(target, 'X') + 10000000000000
                var B:Long = getVarValue(target, 'Y') + 10000000000000

                var b:Double = (x1*B - A*y1).toDouble() / (y2*x1-x2*y1)
                var a:Double = A.toDouble() / x1 - (x2*b)/x1

                if (
                    abs(b - b.roundToLong()) < 0.1 &&
                    abs(a - a.roundToLong()) < 0.1 &&
                    a.roundToLong()*x1+b.roundToLong()*x2 == A &&
                    a.roundToLong()*y1+b.roundToLong()*y2 == B) {
                    count += 3*a.roundToLong() + b.roundToLong()
                }
            }

            println(count)
        }
    }
}