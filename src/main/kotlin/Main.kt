import bmpFormat.BmpFormat
import java.io.File

fun main() {
    //Пример использования программы

    print("Введите текст сообщения: ")
    val message1 = readln()
    println()

    //запись сообщения в файл
    val file1 = File("2.bmp")
    val bmp1 = BmpFormat(file1)
    bmp1.wrireTextToPixelsArray(message1)

    //чтение сообщения из файла
    val file2 = File("2.bmp")
    val bmp2 = BmpFormat(file2)
    val message2 = bmp2.readTextFromPixelsArray()

    println("Текст сообщения, находящийся в файле ${file2.name}: $message2")
}