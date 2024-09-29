package extensions

import java.io.File

fun convertBytesToInt(bytes: List<Byte>, countBytes: Int): Int {
    //Первый байт является младшим разрядом. Последний байт является старшим разрядом. Поэтому используется функция reversed
    if (bytes.size != countBytes) throw Exception("Not 4 bytes for converting")
    val binaryStrings = bytes
        .reversed()
        .map { it.toUByte() }
        .map { it.toInt() }
        .map { Integer.toBinaryString(it) }
        .map { "0".repeat(8 - it.length) + it }
    val result = StringBuilder()
    for (binaryString in binaryStrings) {
        result.append(binaryString)
    }
    return Integer.parseInt(result.toString(), 2)
}

fun convertIntToBytes(number: Int, countBytes: Int): ByteArray {
    val bytes = ByteArray(countBytes)
    bytes[0] = (number shr 0).toByte()
    bytes[1] = (number shr 8).toByte()
    bytes[2] = (number shr 16).toByte()
    bytes[3] = (number shr 24).toByte()
    return bytes
}

fun writeBitToByte(byte: Byte, bit: Int, bitPosition: Int): Byte {
    //position=0 - старший разряд, postion=7 - младший разряд. Поэтому создаем другую переменную
    //truePosition=0 - младший разряд, postion=7 - старший разряд.
    val trueBitPosition = 7 - bitPosition
    val shortBitString = Integer.toBinaryString(byte.toUByte().toInt())
    val bitString = "0".repeat(8 - shortBitString.length) + shortBitString
    val newBitString =
        bitString.substring(0..<trueBitPosition) + bit.toString() + bitString.substring(trueBitPosition + 1)
    return Integer.parseInt(newBitString, 2).toByte()
}

fun readBitFromByte(byte: Byte, bitPosition: Int): Int {
    //position=0 - старший разряд, postion=7 - младший разряд. Поэтому создаем другую переменную
    //truePosition=0 - младший разряд, postion=7 - старший разряд.
    val trueBitPosition = 7 - bitPosition
    val shortBitString = Integer.toBinaryString(byte.toUByte().toInt())
    val bitString = "0".repeat(8 - shortBitString.length) + shortBitString
    return Integer.parseInt(bitString[trueBitPosition].toString())
}

fun writeWhiteColorToFile(file: File) {
    //Закрасить весь файл белым цветом
    val content = file.readBytes()
    for (i in 54..content.size - 1) {
        content[i] = -1
    }
    file.writeBytes(content)
}