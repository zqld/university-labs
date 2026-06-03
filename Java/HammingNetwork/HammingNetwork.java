package org.broker;

import java.util.Arrays;

public class HammingNetwork {
    private double[][] patterns; // Эталонные образцы
    private int numPatterns; // Количество образцов
    private int numNeurons; // Количество нейронов
    private double[][] w1; // Веса первого слоя
    private double b1; // Смещение первого слоя
    private double[][] w2; // Веса второго слоя
    private double[] y; // Выходные значения

    // Конструктор для инициализации с заданными образцами
    public HammingNetwork(double[][] patterns) {
        this.patterns = patterns;
        this.numPatterns = patterns.length;
        this.numNeurons = patterns[0].length;
        this.w1 = patterns;
        this.b1 = numNeurons / 2.0;
        this.w2 = new double[numPatterns][numPatterns];

        // Заполнение w2 (единичные элементы за исключением диагонали)
        for (int i = 0; i < numPatterns; i++) {
            for (int j = 0; j < numPatterns; j++) {
                if (i != j) {
                    w2[i][j] = 1;
                } else {
                    w2[i][j] = 0;
                }
            }
        }
        this.y = new double[numPatterns];
    }

    // Метод первого слоя: вычисление расстояний Хэмминга и нормализация
    public double[] layer1(double[] x) {
        double[] net = new double[numPatterns];
        for (int i = 0; i < numPatterns; i++) {
            net[i] = 0;
            for (int j = 0; j < numNeurons; j++) {
                net[i] += w1[i][j] * x[j];
            }
        }
        // Нормализация значений net
        double maxNet = Arrays.stream(net).max().getAsDouble();
        for (int i = 0; i < numPatterns; i++) {
            net[i] = (net[i] + 1) / (maxNet + 1);
        }
        System.out.println("Нормализованные расстояния Хэмминга для каждого образца: " + Arrays.toString(net));
        return net;
    }

    // Метод второго слоя: обновление выходных значений
    public int layer2(double[] net1) {
        y = Arrays.copyOf(net1, net1.length);
        double[] prevY = new double[y.length];
        int iterations = 0;
        System.out.println("Начальные значения y: " + Arrays.toString(y));

        while (!Arrays.equals(y, prevY) && iterations < 1000) {
            System.arraycopy(y, 0, prevY, 0, y.length);
            for (int i = 0; i < numPatterns; i++) {
                double inhibition = 0;
                for (int j = 0; j < numPatterns; j++) {
                    inhibition += w2[i][j] * y[j];
                }
                y[i] = Math.max(y[i] - inhibition * 0.015, 0);
            }
            iterations++;
            System.out.println("Итерация " + iterations + ", y: " + Arrays.toString(y));
        }

        // Нахождение индекса максимального значения
        int maxIndex = 0;
        double maxValue = y[0];
        for (int i = 1; i < y.length; i++) {
            if (y[i] > maxValue) {
                maxValue = y[i];
                maxIndex = i;
            }
        }

        System.out.println("Максимальное значение y: " + maxValue + " на индексе " + maxIndex);

        if (maxValue < 0.5) {
            return -1; // Нет совпадений
        }
        return maxIndex; // Возвращаем индекс совпавшего шаблона
    }

    // Метод распознавания: обработка входного вектора
    public int recognize(double[] x) {
        double[] net1 = layer1(x);
        System.out.println("Расстояния Хэмминга: " + Arrays.toString(net1));
        return layer2(net1);
    }

    // Преобразование 2D изображения в 1D вектор
    public static double[] imageToVector(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        double[] vector = new double[height * width];
        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                vector[index++] = image[i][j];
            }
        }
        return vector;
    }

    public static void main(String[] args) {
        // Пример эталонных образцов (можно расширить)
        double[][] referencePatterns = createReferencePatterns();

        // Создаем объект нейросети
        HammingNetwork hammingNetwork = new HammingNetwork(referencePatterns);

        // Восстановленное изображение для распознавания
        int[][] recoveredImage = new int[][] {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };

        // Преобразуем изображение в вектор
        double[] recoveredImageVector = imageToVector(recoveredImage);

        System.out.println("Размер восстановленного изображения: " + recoveredImageVector.length);

        // Распознаем образ
        int result = hammingNetwork.recognize(recoveredImageVector);
        System.out.println("Распознанный индекс шаблона: " + result);
    }

    // Создание эталонных образцов (например, для цифр 0-9)
    public static double[][] createReferencePatterns() {
        int[][] zero = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] one = {
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] two = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] three = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] four = {
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] five = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] six = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] seven = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] eight = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int[][] nine = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        return new double[][] {imageToVector(zero), imageToVector(one), imageToVector(two), imageToVector(three), imageToVector(four),
                imageToVector(five), imageToVector(six), imageToVector(seven), imageToVector(eight), imageToVector(nine)}; // Добавьте другие образцы здесь
    }
}
