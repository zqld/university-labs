package org.multi_layer_perceptron;

public class XORTest {
    public static void main(String[] args) {
        // Определяем обучающие данные для функции XOR
        double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        double[] targets = {0, 1, 1, 0}; // Ожидаемые выходы для функции XOR

        // Создаем многослойный перцептрон с 2 входами, 2 нейронами в скрытом слое и коэффициентом обучения 0.5
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(2, 2, 0.5);

        // Обучаем сеть 10 000 эпох
        mlp.train(inputs, targets, 10000);

        // Прогнозируем результаты для входных данных
        System.out.println("Тестирование для XOR:");
        for (int i = 0; i < inputs.length; i++) {
            double prediction = mlp.predict(inputs[i]);
            // Преобразуем предсказание в целое число (0 или 1)
            int roundedPrediction = (int) Math.round(prediction);
            System.out.println(inputs[i][0] + " XOR " + inputs[i][1] + " = " + roundedPrediction); // Выводим результаты
        }
    }
}
