package org.broker;

import java.util.Random;

public class Cognitron {
    private int inputSize;
    private int numClasses;
    private double learningRate;
    private double[][] weights;

    // Конструктор
    public Cognitron(int inputSize, int numClasses, double learningRate) {
        this.inputSize = inputSize;
        this.numClasses = numClasses;
        this.learningRate = learningRate;
        this.weights = new double[inputSize][numClasses];
        initializeWeights();
    }

    // Инициализация весов случайными значениями
    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < numClasses; j++) {
                weights[i][j] = rand.nextDouble();
            }
        }
    }

    // Функция предсказания
    public double[] predict(double[] input) {
        double[] output = new double[numClasses];
        for (int i = 0; i < numClasses; i++) {
            output[i] = 0;
            for (int j = 0; j < inputSize; j++) {
                output[i] += input[j] * weights[j][i];
            }
        }
        return output;
    }

    // Тренировка модели
    public void train(double[][] X, double[][] y, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < X.length; i++) {
                double[] output = predict(X[i]);
                int predictedClass = getMaxIndex(output);
                int actualClass = getMaxIndex(y[i]);

                // Обновление весов, если предсказанный класс не совпадает с истинным
                if (predictedClass != actualClass) {
                    for (int j = 0; j < inputSize; j++) {
                        weights[j][predictedClass] -= learningRate * X[i][j];
                        weights[j][actualClass] += learningRate * X[i][j];
                    }
                }
            }
        }
    }

    // Поиск индекса максимального значения в массиве
    private int getMaxIndex(double[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // Генерация произвольных образов
    public static double[][] generatePatterns(int numPatterns) {
        Random rand = new Random();
        double[][] patterns = new double[numPatterns][10];
        for (int i = 0; i < numPatterns; i++) {
            boolean isSquare = rand.nextBoolean();
            for (int j = 0; j < 10; j++) {
                patterns[i][j] = rand.nextDouble() + (isSquare ? 0.5 : 0);
            }
        }
        return patterns;
    }

    // Генерация меток для образов
    public static double[][] generateLabels(int numPatterns) {
        double[][] labels = new double[numPatterns][2];
        Random rand = new Random();
        for (int i = 0; i < numPatterns; i++) {
            boolean isSquare = rand.nextBoolean();
            labels[i][isSquare ? 0 : 1] = 1;
        }
        return labels;
    }

    public static void main(String[] args) {
        // Генерация обучающих данных
        int numPatterns = 200;
        double[][] X = generatePatterns(numPatterns);
        double[][] y = generateLabels(numPatterns);

        // Создание и обучение модели
        Cognitron cognitron = new Cognitron(10, 2, 0.1);
        cognitron.train(X, y, 1000);

        // Тестирование модели
        double[][] testPatterns = generatePatterns(10);
        double[][] testLabels = generateLabels(10);
        for (int i = 0; i < testPatterns.length; i++) {
            double[] prediction = cognitron.predict(testPatterns[i]);
            int predictedClass = cognitron.getMaxIndex(prediction);
            int actualClass = cognitron.getMaxIndex(testLabels[i]);
            System.out.println("Тест: " + java.util.Arrays.toString(testPatterns[i]) +
                    ", Предсказанный: " + predictedClass + ", Фактический: " + actualClass);
        }
    }
}

