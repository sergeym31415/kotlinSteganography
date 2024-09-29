# Стеганография на Kotlin
## _sergeym31415, 2024-09_
[Стеганография]
В моем проекте стеганография - это тайное хранение сообщения в растровом файле BMP (битность 24). Такой файл можно создать самому с помощью Paint: нарисовать самостоятельно или сохранить готовую картинку формата JPEG как BMP.
Каждый пиксель в BMP(24 bit) файле хранится в виде 3 байт(Red(0-255), Green(0-255), Blue(0-255)).
Чтобы скрытно расположить информацию нам подходит младший бит каждого байта, т.к. младший бит(0000 0001) меньше всего влияет на цвет. Человеческому глазу будет трудно опередлить какой пиксель изменен, тем более на "пёстрой" картинке.
Пример использования в файле Main.kt. Перед запуском необходимо скопировать файл "2.bmp" из папки "blank_bmp_for_experiments" в корневую папку проекта.
###### Ограничения:
- Есть ограничение на длину сообщения. Т.к. сообщение записываем только в младший бит, то максимальным размером сообщения является количество байтов, отведенных на изображение(= ширина в пикселях x высота в пикселях x 3)

## License
MIT

**Free Software, Hell Yeah!**

 [Стеганография]: <https://ru.wikipedia.org/wiki/%D0%A1%D1%82%D0%B5%D0%B3%D0%B0%D0%BD%D0%BE%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D1%8F>
