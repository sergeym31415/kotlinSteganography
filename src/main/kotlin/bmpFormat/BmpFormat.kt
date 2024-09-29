package bmpFormat

import extensions.convertBytesToInt
import extensions.convertIntToBytes
import extensions.readBitFromByte
import extensions.writeBitToByte
import java.io.File

class BmpFormat(val file: File) {
    private val _content = file.readBytes()
    val content: ByteArray
        get() {
            return _content.copyOf()
        }
    private val fileSize = _content.size
    private val signatureBmp = listOf(_content[0], _content[1])
    private val stardAddressPixelsArray =
        convertBytesToInt(listOf(_content[10], _content[11], _content[12], _content[13]), 4)
    private val widthImage = convertBytesToInt(listOf(_content[18], _content[19], _content[20], _content[21]), 4)
    private val heightImage = convertBytesToInt(listOf(_content[22], _content[23], _content[24], _content[25]), 4)
    private val lengthPixelsArray = convertBytesToInt(listOf(_content[34], _content[35], _content[36], _content[37]), 4)
    private var messageSize: Int = convertBytesToInt(listOf(_content[6], _content[7], _content[8], _content[9]), 4)
        get() {
            return convertBytesToInt(listOf(_content[6], _content[7], _content[8], _content[9]), 4)
        }
        set(value) {
            field = value
            val byteArray = convertIntToBytes(value, 4)
            _content[6] = byteArray[0]
            _content[7] = byteArray[1]
            _content[8] = byteArray[2]
            _content[9] = byteArray[3]
        }



    fun wrireTextToPixelsArray(text: String, numberOfBit: Int = 0) {
        val textAsBytes = text.toByteArray()
        val textSizeInBytes = textAsBytes.size
        val step = lengthPixelsArray / textSizeInBytes / 8
        if (step < 1) throw Exception("Too small image for this text")
        var position = stardAddressPixelsArray
        for (byte in textAsBytes) {
            var bitString = Integer.toBinaryString(byte.toUByte().toInt())
            bitString = "0".repeat(8 - bitString.length) + bitString
            for (bit in bitString) {
                val newByte = writeBitToByte(_content[position], Integer.parseInt(bit.toString()), numberOfBit)
                _content[position] = newByte
                position += step
            }
        }
        messageSize = textSizeInBytes
        saveToFile()
    }

    fun readTextFromPixelsArray(numberOfBit: Int = 0): String {
        val textSizeInBytes = messageSize
        val textAsBytes = ByteArray(textSizeInBytes)
        val step = lengthPixelsArray / textSizeInBytes / 8
        var position = stardAddressPixelsArray
        for (i in 0..<textSizeInBytes) {
            val bitsString = StringBuilder()
            for (j in 0..<8) {
                bitsString.append(readBitFromByte(_content[position], numberOfBit).toString())
                position += step
            }
            val byte = Integer.parseInt(bitsString.toString(), 2).toByte()
            textAsBytes[i] = byte
        }
        return textAsBytes.toString(Charsets.UTF_8)
    }

    private fun saveToFile() {
        file.writeBytes(_content)
    }

    fun showInfo() {
        println("-".repeat(50))
        println("-".repeat(40))
        println("Заголовок файла растровой графики (14 байт)")
        println("-".repeat(40))
        println("Сигнатура файла BMP (2 байт): ${content[0]} ${content[1]}")
        println("Размер файла (4 байт): ${convertBytesToInt(listOf(content[2],content[3],content[4],content[5]), 4)}")
        println("Не используется (2 байт)")
        println("Не используется (2 байт)")
        println("Местонахождение данных растрового массива (4 байт): ${convertBytesToInt(listOf(content[10],content[11],content[12],content[13]), 4)}")
        println("-".repeat(40))
        println("Информационный заголовок растрового массива (40 байт)")
        println("-".repeat(40))
        println("Длина этого заголовка (4 байт): ${convertBytesToInt(listOf(content[14],content[15],content[16],content[17]), 4)}")
        println("Ширина изображения (4 байт): ${convertBytesToInt(listOf(content[18],content[19],content[20],content[21]), 4)}")
        println("Высота изображения (4 байт): ${convertBytesToInt(listOf(content[22],content[23],content[24],content[25]), 4)}")
        println("Число цветовых плоскостей (2 байт): ${convertBytesToInt(listOf(content[26],content[27]), 2)}")
        println("Бит/пиксель (2 байт): ${convertBytesToInt(listOf(content[28],content[29]), 2)}")
        println("Метод сжатия (4 байт): ${convertBytesToInt(listOf(content[30],content[31],content[32],content[33]), 4)}")
        println("Длина растрового массива (4 байт): ${convertBytesToInt(listOf(content[34],content[35],content[36],content[37]), 4)}")
        println("Горизонтальное разрешение (4 байт): ${convertBytesToInt(listOf(content[38],content[39],content[40],content[41]), 4)}")
        println("Вертикальное разрешение (4 байт): ${convertBytesToInt(listOf(content[42],content[43],content[44],content[45]), 4)}")
        println("Число цветов изображения (4 байт): ${convertBytesToInt(listOf(content[46],content[47],content[48],content[49]), 4)}")
        println("Число основных цветов (4 байт): ${convertBytesToInt(listOf(content[50],content[51],content[52],content[53]), 4)}")
        println("-".repeat(50))
    }

    override fun toString(): String {
        return "BmpFormat(file=$file, _content=${_content.contentToString()}, fileSize=$fileSize, signatureBmp=$signatureBmp, stardAddressPixelsArray=$stardAddressPixelsArray, widthImage=$widthImage, heightImage=$heightImage, lengthPixelsArray=$lengthPixelsArray, messageSize=$messageSize)"
    }


}