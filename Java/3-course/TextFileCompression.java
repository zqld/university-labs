package com.mycompany.textfilecompressionnew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

//Пример
//Файл: file1.txt
/*Привет
  Привет
  хей
  хей
  один
  два
  пять
  десять
  деся
  привет
  привет
  привет*/
//Файл: file2.txt
/*Привет-Число повторений:2, Позиции:1 2
  хей-Число повторений:2, Позиции:3 4
  один-Число повторений:1, Позиции:5
  два-Число повторений:1, Позиции:6
  пять-Число повторений:1, Позиции:7
  десять-Число повторений:1, Позиции:8
  деся-Число повторений:1, Позиции:9
  привет-Число повторений:3, Позиции:10 11 12 */
//Файл: file3.txt
/*Привет
  Привет
  хей
  хей
  один
  два
  пять
  десять
  деся
  привет
  привет
  привет*/

public class TextFileCompression {
    public static void main(String[] args){
        // Определение путей к входному и выходному файлам
        String file1 = "file1.txt"; // Входной файл
        String file2 = "file2.txt"; // Временный файл для записи
        String file3 = "file3.txt"; // Конечный выходной файл

        // Выполнение операций сжатия и восстановления
        compression(file1, file2); // Сжатие данных из file1 и запись их в file2
        recovery(file2, file3);    // Восстановление данных из file2 и запись их в file3
    }

    public static void compression(String file1, String file2) {
        try {
            // Создание объектов File для чтения и записи
            File fileRead = new File(file1);
            File fileWrite = new File(file2);

            // Инициализация Scanner для чтения и PrintWriter для записи
            Scanner scanner = new Scanner(fileRead);
            PrintWriter writer = new PrintWriter(fileWrite);

            // Списки для хранения уникальных элементов и их позиций
            ArrayList<String> elements = new ArrayList<>();
            ArrayList<ArrayList<Integer>> positions = new ArrayList<>();
            int lineNumber = 1;

            // Чтение каждой строки из входного файла
            while (scanner.hasNextLine()) {
                String element = scanner.nextLine();
                int index = elements.indexOf(element);

                if (index == -1) {
                    // Если элемент отсутствует в списке, добавляем его и создаем новый список позиций
                    elements.add(element);
                    ArrayList<Integer> positionList = new ArrayList<>();
                    positionList.add(lineNumber);
                    positions.add(positionList);
                } else {
                    // Если элемент уже существует, добавляем текущий номер строки в список его позиций
                    positions.get(index).add(lineNumber);
                }

                lineNumber++;
            }

            // Запись сжатых данных в выходной файл
            for (int i = 0; i < elements.size(); i++) {
                writer.print(elements.get(i) + "-Число повторений:" + positions.get(i).size() + ", Позиции:");
                for (int position : positions.get(i)) {
                    writer.print(position + " ");
                }
                writer.println();
            }

            // Закрытие Scanner и PrintWriter
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден.");
        }
    }

    public static void recovery(String file2, String file3) {
        try {
            // Создание объектов File для чтения сжатых данных и записи восстановленных данных
            File fileRead = new File(file2);
            File fileWrite = new File(file3);

            // Инициализация Scanner для чтения и PrintWriter для записи
            Scanner scanner = new Scanner(fileRead);
            PrintWriter writer = new PrintWriter(fileWrite);

            // Списки для хранения позиций и строк
            ArrayList<Integer> arrayPositions = new ArrayList<>();
            ArrayList<String> arrayString = new ArrayList<>();

            // Чтение каждой строки из сжатого файла
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                int positionsIndex = line.indexOf("Позиции:");

                if (positionsIndex != -1) {
                    // Извлечение значений позиций из строки
                    positionsIndex += "Позиции:".length();
                    String numbersString = line.substring(positionsIndex);
                    String[] numbersArray = numbersString.split(" ");

                    for (String number : numbersArray) {
                        try {
                            int parsedNumber = Integer.parseInt(number);
                            arrayPositions.add(parsedNumber);
                        } catch (NumberFormatException e) {
                            // Обработка ошибок парсинга
                        }
                    }
                } else {
                    System.out.println("Цифры после \"Позиций:\" не найдены.");
                }

                // Извлечение количества повторений
                int startIndex = line.indexOf("-Число повторений:") + "-Число повторений:".length();
                /*Ограничители для выбора подстрок можно задать и другие, более короткие*/
                int endIndexRepetitions = line.indexOf(", Позиции:");
                int repetitions = 0;

                if (startIndex != -1 && endIndexRepetitions != -1) {
                    String numberSubstring = line.substring(startIndex, endIndexRepetitions);

                    try {
                        repetitions = Integer.parseInt(numberSubstring);
                    } catch (NumberFormatException e) {
                        System.out.println("Не удалось извлечь число.");
                    }
                } else {
                    System.out.println("Соответствие не найдено.");
                }

                // Извлечение подстроки и добавление ее в список несколько раз на основе количества повторений
                int endIndex = line.indexOf("-Число повторений:");

                if (endIndex != -1) {
                    int i = 0;
                    while (i < repetitions) {
                        String extractedSubstring = line.substring(0, endIndex).trim();
                        arrayString.add(extractedSubstring);
                        i++;
                    }
                } else {
                    System.out.println("Подстрока не найдена.");
                }
            }

            // Пузырьковая сортировка для переупорядочивания данных на основе позиций
            int numberPositions = arrayPositions.size();
            boolean swapped;
            do {
                swapped = false;
                for (int i = 1; i < numberPositions; i++) {
                    if (arrayPositions.get(i - 1) > arrayPositions.get(i)) {
                        // Обмен значениями позиций
                        int tempNumber = arrayPositions.get(i - 1);
                        arrayPositions.set(i - 1, arrayPositions.get(i));
                        arrayPositions.set(i, tempNumber);

                        // Обмен соответствующими строками
                        String tempString = arrayString.get(i - 1);
                        arrayString.set(i - 1, arrayString.get(i));
                        arrayString.set(i, tempString);

                        swapped = true;
                    }
                }
            } while (swapped);

            // Запись восстановленных данных в выходной файл
            for (String numString: arrayString){
                writer.println(numString);
            }

            // Закрытие Scanner и PrintWriter
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: Файл не найден.");
        }
    }
}

