import java.io.File

class Utils {
    companion object {
        fun getFile(fileName: String): File {
            return File("D:\\projects\\AdventOfCode2024\\src\\" + fileName)
        }
    }
}