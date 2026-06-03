package org.perceptron;

import java.util.Arrays;

public class Main {

    private static Perceptron perceptron;  // Переменная для объекта перцептрона

    public static void main(String[] args){

        perceptron = new Perceptron();  // Создаем объект перцептрона

        // Реализация логического И
        System.out.println("Реализация логического И: ");

        // Входные данные для операции AND (логическое И)
        int[][] entranceDateAND = {
                {0, 0},  // Вход: 0 И 0
                {0, 1},  // Вход: 0 И 1
                {1, 0},  // Вход: 1 И 0
                {1, 1}   // Вход: 1 И 1
        };

        // Ожидаемые результаты для операции AND
        int[] dataWaitingAND = {0, 0, 0, 1};

        int iterationsAND = 100;      // Количество итераций для обучения
        int numberInputsAND = 2;      // Количество входов

        // Обучение перцептрона для операции AND
        perceptron.training(entranceDateAND, dataWaitingAND, iterationsAND, numberInputsAND);

        // Тестирование перцептрона на данных AND
        test(entranceDateAND);

        // Реализация логического ИЛИ
        System.out.println("Реализация логического ИЛИ: ");

        // Входные данные для операции OR (логическое ИЛИ)
        int[][] entranceDateOR = {
                {0, 0},  // Вход: 0 ИЛИ 0
                {0, 1},  // Вход: 0 ИЛИ 1
                {1, 0},  // Вход: 1 ИЛИ 0
                {1, 1}   // Вход: 1 ИЛИ 1
        };

        // Ожидаемые результаты для операции OR
        int[] dataWaitingOR = {0, 1, 1, 1};

        int iterationsOR = 100;       // Количество итераций для обучения
        int numberInputsOR = 2;       // Количество входов

        // Обучение перцептрона для операции OR
        perceptron.training(entranceDateOR, dataWaitingOR, iterationsOR, numberInputsOR);

        // Тестирование перцептрона на данных OR
        test(entranceDateOR);

        // Реализация логического НЕ
        System.out.println("Реализация логического НЕ: ");

        // Входные данные для операции NOT (логическое НЕ)
        int[][] entranceDateNO = {
                {0},  // Вход: НЕ 0
                {1}   // Вход: НЕ 1
        };

        // Ожидаемые результаты для операции NOT
        int[] dataWaitingNO = {1, 0};

        int iterationsNO = 100;       // Количество итераций для обучения
        int numberInputsNO = 1;       // Количество входов

        // Обучение перцептрона для операции NOT (создается новый перцептрон)
        perceptron = new Perceptron();
        perceptron.training(entranceDateNO, dataWaitingNO, iterationsNO, numberInputsNO);

        // Тестирование перцептрона на данных NOT
        test(entranceDateNO);
    }

    // Метод для тестирования перцептрона
    public static void test(int[][] testData) {
        // Проходим по каждому тестовому примеру
        for (int[] inputs : testData) {
            // Выводим входные данные и предсказанный результат
            System.out.println(Arrays.toString(inputs) + " => " + perceptron.predict(inputs));
        }
    }
}
