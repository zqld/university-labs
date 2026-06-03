package org.broker;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixImpl;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import java.util.Arrays;
import java.util.Random;

public class NeuralNetworkComparison {
    public static void main(String[] args) {
        // Загрузка данных
        DigitsDataset dataset = new DigitsDataset(); // Предположим, класс для работы с набором цифр
        double[][] X = dataset.getNormalizedData();
        int[] y = dataset.getLabels();

        // Разделение данных
        TrainTestSplit split = TrainTestSplit.split(X, y, 0.8);
        double[][] X_train = split.getTrainFeatures();
        double[][] X_test = split.getTestFeatures();
        int[] y_train = split.getTrainLabels();
        int[] y_test = split.getTestLabels();

        // One-Hot кодирование меток
        double[][] y_train_onehot = Utils.oneHotEncode(y_train, 10);
        double[][] y_test_onehot = Utils.oneHotEncode(y_test, 10);

        // Нейронная сеть
        NeuralNetwork nn = new NeuralNetwork(64, 30, 10, 0.01);
        nn.train(X_train, y_train_onehot, 5000);
        double[][] y_pred_nn = nn.predict(X_test);
        int[] y_pred_nn_labels = Utils.argmax(y_pred_nn);
        double nnAccuracy = Utils.accuracy(y_test, y_pred_nn_labels);

        // Однослойный перцептрон
        SingleLayerPerceptron slp = new SingleLayerPerceptron(64, 10, 0.1);
        slp.train(X_train, y_train_onehot, 5000);
        double[][] y_pred_slp = slp.predict(X_test);
        int[] y_pred_slp_labels = Utils.argmax(y_pred_slp);
        double slpAccuracy = Utils.accuracy(y_test, y_pred_slp_labels);

        // Вывод результатов
        System.out.printf("Точность нейронной сети: %.2f%%%n", nnAccuracy * 100); // Точность для нейронной сети
        System.out.printf("Точность однослойного перцептрона: %.2f%%%n", slpAccuracy * 100); // Точность для однослойного перцептрона
    }
}

// Реализация нейронной сети
class NeuralNetwork {
    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;
    private double learningRate;
    private Random random = new Random();

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, double learningRate) {
        this.learningRate = learningRate;
        this.weightsInputHidden = Utils.randomMatrix(inputSize, hiddenSize);
        this.weightsHiddenOutput = Utils.randomMatrix(hiddenSize, outputSize);
    }

    public void train(double[][] X, double[][] y, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double[][] hiddenInput = Utils.dot(X, weightsInputHidden);
            double[][] hiddenOutput = Utils.sigmoid(hiddenInput);

            double[][] outputInput = Utils.dot(hiddenOutput, weightsHiddenOutput);
            double[][] output = Utils.softmax(outputInput);

            // Ошибки
            double[][] outputError = Utils.subtract(y, output);
            double[][] outputDelta = outputError;

            double[][] hiddenError = Utils.dot(outputDelta, Utils.transpose(weightsHiddenOutput));
            double[][] hiddenDelta = Utils.multiply(hiddenError, Utils.sigmoidDerivative(hiddenOutput));

            // Обновление весов
            weightsHiddenOutput = Utils.add(weightsHiddenOutput, Utils.scale(Utils.dot(Utils.transpose(hiddenOutput), outputDelta), learningRate));
            weightsInputHidden = Utils.add(weightsInputHidden, Utils.scale(Utils.dot(Utils.transpose(X), hiddenDelta), learningRate));

            if (epoch % 1000 == 0) {
                double loss = Utils.meanSquaredError(y, output);
                System.out.printf("Эпоха %d, Потери (Loss): %.4f%n", epoch, loss);
            }
        }
    }

    public double[][] predict(double[][] X) {
        double[][] hiddenInput = Utils.dot(X, weightsInputHidden);
        double[][] hiddenOutput = Utils.sigmoid(hiddenInput);

        double[][] outputInput = Utils.dot(hiddenOutput, weightsHiddenOutput);
        return Utils.softmax(outputInput);
    }
}

// Реализация однослойного перцептрона
class SingleLayerPerceptron {
    private double[][] weights;
    private double learningRate;

    public SingleLayerPerceptron(int inputSize, int outputSize, double learningRate) {
        this.learningRate = learningRate;
        this.weights = Utils.randomMatrix(inputSize, outputSize);
    }

    public void train(double[][] X, double[][] y, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double[][] predictions = Utils.sigmoid(Utils.dot(X, weights));
            double[][] error = Utils.subtract(y, predictions);
            double[][] adjustment = Utils.scale(Utils.dot(Utils.transpose(X), error), learningRate);
            weights = Utils.add(weights, adjustment);
        }
    }

    public double[][] predict(double[][] X) {
        return Utils.sigmoid(Utils.dot(X, weights));
    }
}

// Утилиты
class Utils {
    public static double[][] randomMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];
        double limit = Math.sqrt(6.0 / (rows + cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * 2 * limit - limit;
            }
        }
        return matrix;
    }

    public static double[][] dot(double[][] A, double[][] B) {
        RealMatrix a = new Array2DRowRealMatrix(A);
        RealMatrix b = new Array2DRowRealMatrix(B);
        return a.multiply(b).getData();
    }

    public static double[][] sigmoid(double[][] X) {
        double[][] result = new double[X.length][X[0].length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[0].length; j++) {
                result[i][j] = 1.0 / (1.0 + Math.exp(-X[i][j]));
            }
        }
        return result;
    }

    public static double[][] sigmoidDerivative(double[][] X) {
        double[][] result = sigmoid(X);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = result[i][j] * (1 - result[i][j]);
            }
        }
        return result;
    }

    public static double[][] softmax(double[][] X) {
        double[][] result = new double[X.length][X[0].length];
        for (int i = 0; i < X.length; i++) {
            double max = Arrays.stream(X[i]).max().orElse(0);
            double sum = 0.0;
            for (int j = 0; j < X[i].length; j++) {
                result[i][j] = Math.exp(X[i][j] - max);
                sum += result[i][j];
            }
            for (int j = 0; j < X[i].length; j++) {
                result[i][j] /= sum;
            }
        }
        return result;
    }

    public static double[][] oneHotEncode(int[] labels, int numClasses) {
        double[][] oneHot = new double[labels.length][numClasses];
        for (int i = 0; i < labels.length; i++) {
            oneHot[i][labels[i]] = 1.0;
        }
        return oneHot;
    }

    public static int[] argmax(double[][] X) {
        int[] indices = new int[X.length];
        for (int i = 0; i < X.length; i++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < X[0].length; j++) {
                if (X[i][j] > max) {
                    max = X[i][j];
                    indices[i] = j;
                }
            }
        }
        return indices;
    }

    public static double accuracy(int[] trueLabels, int[] predictedLabels) {
        int correct = 0;
        for (int i = 0; i < trueLabels.length; i++) {
            if (trueLabels[i] == predictedLabels[i]) {
                correct++;
            }
        }
        return (double) correct / trueLabels.length;
    }
}
