package org.perceptron;

import java.util.Random;

public class Perceptron {

    private static double[] weights;  // Массив весов для входных данных
    private static double bias;       // Смещение (порог)
    private static double learningRate = 0.1;  // Скорость обучения

    // Метод обучения перцептрона
    public static void training(int[][] entranceDateAND, int[] dataWaiting, int iterations, int numberInputs) {

        // Инициализация весов и смещения
        parametrs(numberInputs);

        // Основной цикл обучения
        for (int i = 0; i < iterations; i++) {  // Количество итераций
            for (int j = 0; j < entranceDateAND.length; j++) {  // Проходим по каждому примеру данных

                int prediction = predict(entranceDateAND[j]);  // Делаем предсказание для текущего примера
                int difference = dataWaiting[j] - prediction;  // Вычисляем ошибку (разницу между ожидаемым и реальным значением)

                // Обновляем веса на основе ошибки
                for (int m = 0; m < weights.length; m++) {
                    weights[m] += learningRate * difference * entranceDateAND[j][m];  // Корректируем вес
                }
                bias += learningRate * difference;  // Корректируем смещение
            }
        }
    }

    // Метод для инициализации весов и смещения
    public static void parametrs(int numberInputs) {
        Random random = new Random();  // Создаем объект Random для генерации случайных чисел

        // Инициализируем массив весов
        weights = new double[numberInputs];  // Количество весов равно количеству входов
        bias = random.nextDouble();          // Смещение инициализируется случайным числом

        // Заполняем массив весов случайными значениями
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();  // Каждый вес - случайное число
        }
    }

    // Метод для предсказания результата на основе входных данных
    public static int predict(int[] inputs) {
        double sum = 0;  // Переменная для хранения взвешенной суммы

        // Суммируем входы, умноженные на соответствующие веса
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];  // Умножаем каждый вход на его вес и добавляем к общей сумме
        }
        sum += bias;  // Добавляем смещение

        // Возвращаем 1, если взвешенная сумма >= 0, иначе возвращаем 0 (функция активации - пороговая)
        return sum >= 0 ? 1 : 0;
    }
}
