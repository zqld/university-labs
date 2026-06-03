package org.broker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    // Функция для минимизации
    public static double fitnessFunction(double x, double y) {
        return 1 / (1 + x * x + y * y);
    }

    // Класс для представления индивидов
    static class Individual {
        double x, y;
        double fitness;

        // Конструктор индивида, инициализирует значения x и y и рассчитывает приспособленность
        public Individual(double x, double y) {
            this.x = x;
            this.y = y;
            this.fitness = fitnessFunction(x, y);
        }
    }

    // Метод кроссовера (пересечение двух индивидов)
    public static Individual crossover(Individual parent1, Individual parent2) {
        double x = (parent1.x + parent2.x) / 2;  // Среднее значение x
        double y = (parent1.y + parent2.y) / 2;  // Среднее значение y
        return new Individual(x, y);  // Создаем нового потомка
    }

    // Метод мутации
    public static Individual mutate(Individual individual, double mutationRate) {
        Random rand = new Random();
        // Если случайное число меньше коэффициента мутации, то происходит мутация
        if (rand.nextDouble() < mutationRate) {
            individual.x += rand.nextGaussian() * 0.1;  // Мутируем x
            individual.y += rand.nextGaussian() * 0.1;  // Мутируем y
            individual.fitness = fitnessFunction(individual.x, individual.y);  // Пересчитываем приспособленность
        }
        return individual;
    }

    // Метод для выбора индивида по методу рулетки
    public static Individual rouletteSelection(List<Individual> population) {
        Random rand = new Random();
        double totalFitness = population.stream().mapToDouble(ind -> ind.fitness).sum();  // Общая приспособленность
        double selectionPoint = rand.nextDouble() * totalFitness;  // Точка для выбора индивида

        double cumulativeFitness = 0;
        // Проходим по популяции и выбираем индивида с вероятностью пропорциональной его приспособленности
        for (Individual ind : population) {
            cumulativeFitness += ind.fitness;
            if (cumulativeFitness >= selectionPoint) {
                return ind;  // Возвращаем выбранного индивида
            }
        }
        return population.get(population.size() - 1);  // Если что-то пошло не так, возвращаем последнего
    }

    // Метод для выбора индивида по методу элит
    public static Individual eliteSelection(List<Individual> population) {
        // Сортируем популяцию по убыванию приспособленности
        population.sort((ind1, ind2) -> Double.compare(ind2.fitness, ind1.fitness));
        return population.get(0);  // Возвращаем самого лучшего
    }

    public static void main(String[] args) {
        int populationSize = 100;  // Размер популяции
        int generations = 1000;  // Количество поколений
        double mutationRate = 0.1;  // Коэффициент мутации
        Random rand = new Random();

        // Создание начальной популяции
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double x = rand.nextDouble() * 10 - 5;  // случайное значение от -5 до 5
            double y = rand.nextDouble() * 10 - 5;  // случайное значение от -5 до 5
            population.add(new Individual(x, y));
        }

        // Генетический алгоритм с методом рулетки
        List<Individual> populationWithRoulette = new ArrayList<>(population);
        for (int generation = 0; generation < generations; generation++) {
            List<Individual> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                Individual parent1 = rouletteSelection(populationWithRoulette);  // Выбор первого родителя методом рулетки
                Individual parent2 = rouletteSelection(populationWithRoulette);  // Выбор второго родителя методом рулетки
                Individual offspring = crossover(parent1, parent2);  // Кроссовер
                newPopulation.add(mutate(offspring, mutationRate));  // Мутация и добавление потомка в новое поколение
            }
            populationWithRoulette = new ArrayList<>(newPopulation);  // Обновление популяции
        }

        // Генетический алгоритм с методом элит
        List<Individual> populationWithElite = new ArrayList<>(population);
        for (int generation = 0; generation < generations; generation++) {
            List<Individual> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                Individual parent1 = eliteSelection(populationWithElite);  // Выбор первого родителя методом элит
                Individual parent2 = eliteSelection(populationWithElite);  // Выбор второго родителя методом элит
                Individual offspring = crossover(parent1, parent2);  // Кроссовер
                newPopulation.add(mutate(offspring, mutationRate));  // Мутация и добавление потомка в новое поколение
            }
            populationWithElite = new ArrayList<>(newPopulation);  // Обновление популяции
        }

        // Вывод результатов для метода рулетки
        Individual bestWithRoulette = populationWithRoulette.stream()
                .max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness))  // Находим лучшего индивида
                .get();
        System.out.println("Лучший индивид с использованием метода рулетки:");
        System.out.println("x = " + bestWithRoulette.x + ", y = " + bestWithRoulette.y + ", фитнес = " + bestWithRoulette.fitness);

        // Вывод результатов для метода элит
        Individual bestWithElite = populationWithElite.stream()
                .max((ind1, ind2) -> Double.compare(ind1.fitness, ind2.fitness))  // Находим лучшего индивида
                .get();
        System.out.println("Лучший индивид с использованием метода элит:");
        System.out.println("x = " + bestWithElite.x + ", y = " + bestWithElite.y + ", фитнес = " + bestWithElite.fitness);
    }
}
