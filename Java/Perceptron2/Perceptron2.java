package org.broker;

import java.util.*;

public class Perceptron2 {

    // Класс для представления перцептрона
    static class Perceptron {
        double[] weights;
        double learningRate = 0.1;

        // Конструктор для инициализации случайных весов
        public Perceptron(int inputSize) {
            weights = new double[inputSize];
            Random rand = new Random();
            for (int i = 0; i < inputSize; i++) {
                weights[i] = rand.nextDouble() * 2 - 1; // случайные веса от -1 до 1
            }
        }

        // Функция активации (пороговая функция)
        public int predict(double[] inputs) {
            double sum = 0;
            for (int i = 0; i < inputs.length; i++) {
                sum += inputs[i] * weights[i];
            }
            return sum > 0 ? 1 : 0; // Если сумма больше 0, то 1, иначе 0
        }

        // Функция тренировки (обновление весов с учетом ошибки)
        public void train(double[] inputs, int target) {
            int prediction = predict(inputs);
            int error = target - prediction;
            for (int i = 0; i < weights.length; i++) {
                weights[i] += learningRate * error * inputs[i]; // Обновление весов
            }
        }

        // Получение фитнес-оценки перцептрона (по точности)
        public double fitness(List<TrainingData> data) {
            int correct = 0;
            for (TrainingData d : data) {
                if (predict(d.inputs) == d.target) {
                    correct++;
                }
            }
            return (double) correct / data.size(); // Точность как фитнес
        }
    }

    // Класс для данных тренировки
    static class TrainingData {
        double[] inputs;
        int target;

        public TrainingData(double[] inputs, int target) {
            this.inputs = inputs;
            this.target = target;
        }
    }

    // Класс для генетического алгоритма
    static class GeneticAlgorithm {
        List<Perceptron> population;
        int populationSize = 50;
        int generations = 100;
        double mutationRate = 0.1;
        Random rand = new Random();

        // Инициализация популяции
        public GeneticAlgorithm(int inputSize) {
            population = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                population.add(new Perceptron(inputSize));
            }
        }

        // Отбор лучших перцептронов (по фитнесу)
        public Perceptron selectParent(List<TrainingData> data) {
            Perceptron best = population.get(0);
            double bestFitness = best.fitness(data);
            for (Perceptron p : population) {
                double fitness = p.fitness(data);
                if (fitness > bestFitness) {
                    best = p;
                    bestFitness = fitness;
                }
            }
            return best;
        }

        // Кроссовер (пересечение весов двух перцептронов)
        public Perceptron crossover(Perceptron parent1, Perceptron parent2) {
            Perceptron offspring = new Perceptron(parent1.weights.length);
            for (int i = 0; i < parent1.weights.length; i++) {
                offspring.weights[i] = rand.nextBoolean() ? parent1.weights[i] : parent2.weights[i];
            }
            return offspring;
        }

        // Мутация (случайное изменение веса)
        public void mutate(Perceptron p) {
            for (int i = 0; i < p.weights.length; i++) {
                if (rand.nextDouble() < mutationRate) {
                    p.weights[i] += rand.nextGaussian() * 0.1; // Небольшое изменение
                }
            }
        }

        // Эволюция популяции
        public void evolve(List<TrainingData> data) {
            List<Perceptron> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                Perceptron parent1 = selectParent(data);
                Perceptron parent2 = selectParent(data);
                Perceptron offspring = crossover(parent1, parent2);
                mutate(offspring);
                newPopulation.add(offspring);
            }
            population = newPopulation;
        }

        // Получение лучшего перцептрона по фитнесу
        public Perceptron getBest(List<TrainingData> data) {
            Perceptron best = population.get(0);
            double bestFitness = best.fitness(data);
            for (Perceptron p : population) {
                double fitness = p.fitness(data);
                if (fitness > bestFitness) {
                    best = p;
                    bestFitness = fitness;
                }
            }
            return best;
        }
    }

    public static void main(String[] args) {
        // Пример задачи XOR
        List<TrainingData> trainingData = new ArrayList<>();
        trainingData.add(new TrainingData(new double[]{0, 0}, 0));
        trainingData.add(new TrainingData(new double[]{0, 1}, 1));
        trainingData.add(new TrainingData(new double[]{1, 0}, 1));
        trainingData.add(new TrainingData(new double[]{1, 1}, 0));

        // Инициализация генетического алгоритма
        GeneticAlgorithm ga = new GeneticAlgorithm(2); // 2 входа для задачи XOR

        // Эволюция популяции
        for (int gen = 0; gen < ga.generations; gen++) {
            ga.evolve(trainingData);
            Perceptron best = ga.getBest(trainingData);
            System.out.println("Поколение " + gen + ": лучший фитнес = " + best.fitness(trainingData));
        }

        // Результаты
        Perceptron bestPerceptron = ga.getBest(trainingData);
        System.out.println("Лучший перцептрон:");
        for (double weight : bestPerceptron.weights) {
            System.out.println(weight);
        }
    }
}
