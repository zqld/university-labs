package org.multi_layer_perceptron;

import java.util.Random;

public class MultiLayerPerceptron {
    private double[][] inputToHiddenWeights; // Веса от входного слоя к скрытому слою
    private double[] hiddenToOutputWeights;  // Веса от скрытого слоя к выходному
    private double learningRate;             // Коэффициент обучения

    // Конструктор, инициализирующий веса и коэффициент обучения
    public MultiLayerPerceptron(int inputSize, int hiddenSize, double learningRate) {
        inputToHiddenWeights = new double[inputSize][hiddenSize]; // Веса между входным и скрытым слоями
        hiddenToOutputWeights = new double[hiddenSize];           // Веса между скрытым и выходным слоями
        this.learningRate = learningRate;                         // Установка коэффициента обучения
        initializeWeights();                                      // Инициализация весов случайными значениями
    }

    // Метод для инициализации весов случайными значениями в диапазоне от -1 до 1
    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < inputToHiddenWeights.length; i++) {
            for (int j = 0; j < inputToHiddenWeights[i].length; j++) {
                inputToHiddenWeights[i][j] = rand.nextDouble() * 2 - 1; // Генерация случайного числа от -1 до 1
            }
        }
        for (int i = 0; i < hiddenToOutputWeights.length; i++) {
            hiddenToOutputWeights[i] = rand.nextDouble() * 2 - 1; // Генерация случайного числа от -1 до 1
        }
    }

    // Сигмоидная функция активации
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x)); // 1 / (1 + e^(-x))
    }

    // Производная сигмоидной функции
    private double sigmoidDerivative(double x) {
        return x * (1 - x); // Производная от сигмоиды: f'(x) = f(x) * (1 - f(x))
    }

    // Метод для обучения перцептрона
    public void train(double[][] inputs, double[] targets, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) { // Проходим через все эпохи (итерации)
            for (int i = 0; i < inputs.length; i++) {  // Проходим через каждый обучающий пример
                // Прямое распространение
                double[] hiddenLayerOutputs = new double[inputToHiddenWeights[0].length];
                for (int j = 0; j < hiddenLayerOutputs.length; j++) {
                    double sum = 0;
                    for (int k = 0; k < inputs[i].length; k++) {
                        sum += inputs[i][k] * inputToHiddenWeights[k][j]; // Рассчитываем взвешенную сумму на скрытом слое
                    }
                    hiddenLayerOutputs[j] = sigmoid(sum); // Применяем сигмоидную функцию
                }

                // Рассчитываем выход
                double output = 0;
                for (int j = 0; j < hiddenLayerOutputs.length; j++) {
                    output += hiddenLayerOutputs[j] * hiddenToOutputWeights[j]; // Рассчитываем взвешенную сумму на выходном слое
                }
                output = sigmoid(output); // Применяем сигмоидную функцию для получения окончательного выхода

                // Обратное распространение
                double outputError = targets[i] - output; // Ошибка на выходном слое
                double outputDelta = outputError * sigmoidDerivative(output); // Дельта на выходном слое

                double[] hiddenDeltas = new double[hiddenLayerOutputs.length]; // Дельты на скрытом слое
                for (int j = 0; j < hiddenDeltas.length; j++) {
                    hiddenDeltas[j] = outputDelta * hiddenToOutputWeights[j] * sigmoidDerivative(hiddenLayerOutputs[j]);
                }

                // Обновление весов от скрытого слоя к выходному
                for (int j = 0; j < hiddenToOutputWeights.length; j++) {
                    hiddenToOutputWeights[j] += learningRate * outputDelta * hiddenLayerOutputs[j]; // Корректируем веса
                }

                // Обновление весов от входного слоя к скрытому
                for (int j = 0; j < inputToHiddenWeights.length; j++) {
                    for (int k = 0; k < inputToHiddenWeights[j].length; k++) {
                        inputToHiddenWeights[j][k] += learningRate * hiddenDeltas[k] * inputs[i][j]; // Корректируем веса
                    }
                }
            }
        }
    }

    // Метод для предсказания на основе входных данных
    public double predict(double[] input) {
        // Прямое распространение
        double[] hiddenLayerOutputs = new double[inputToHiddenWeights[0].length];
        for (int j = 0; j < hiddenLayerOutputs.length; j++) {
            double sum = 0;
            for (int k = 0; k < input.length; k++) {
                sum += input[k] * inputToHiddenWeights[k][j]; // Рассчитываем взвешенную сумму для скрытого слоя
            }
            hiddenLayerOutputs[j] = sigmoid(sum); // Применяем сигмоидную функцию
        }

        // Рассчитываем итоговый выход
        double output = 0;
        for (int j = 0; j < hiddenLayerOutputs.length; j++) {
            output += hiddenLayerOutputs[j] * hiddenToOutputWeights[j]; // Взвешенная сумма для выходного слоя
        }
        return sigmoid(output); // Применяем сигмоидную функцию для получения окончательного выхода
    }
}

