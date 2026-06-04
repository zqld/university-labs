package org.broker;

import java.util.*;

public class AntColonyOptimization {
    private double[][] distances;  // матрица расстояний
    private double[][] pheromones; // матрица феромонов
    private int numberOfCities;    // количество городов
    private int numberOfAnts;      // количество муравьев
    private double alpha;          // влияние феромона
    private double beta;           // влияние расстояния
    private double evaporation;    // коэффициент испарения
    private double Q;              // константа для обновления феромона
    private Random random;

    public AntColonyOptimization(int numberOfCities) {
        this.numberOfCities = numberOfCities;
        this.numberOfAnts = 40;
        this.alpha = 1.0;
        this.beta = 2.0;
        this.evaporation = 0.5;
        this.Q = 100;
        this.random = new Random();
        this.distances = new double[numberOfCities][numberOfCities];
        this.pheromones = new double[numberOfCities][numberOfCities];
    }

    // Инициализация матрицы расстояний
    public void setDistanceMatrix(double[][] distances) {
        this.distances = distances;
        // Инициализация феромонов начальными значениями
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i != j) {
                    pheromones[i][j] = 0.01;
                }
            }
        }
    }

    // Основной метод решения
    public int[] solve(int maxIterations) {
        int[] bestTour = null;
        double bestTourLength = Double.MAX_VALUE;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[][] ants = new int[numberOfAnts][numberOfCities];
            double[] tourLengths = new double[numberOfAnts];

            // Построение маршрутов всеми муравьями
            for (int ant = 0; ant < numberOfAnts; ant++) {
                ants[ant] = generateTour();
                tourLengths[ant] = calculateTourLength(ants[ant]);

                if (tourLengths[ant] < bestTourLength) {
                    bestTourLength = tourLengths[ant];
                    bestTour = ants[ant].clone();
                }
            }

            // Обновление феромонов
            updatePheromones(ants, tourLengths);
        }

        return bestTour;
    }

    // Генерация маршрута для одного муравья
    private int[] generateTour() {
        int[] tour = new int[numberOfCities];
        boolean[] visited = new boolean[numberOfCities];
        tour[0] = random.nextInt(numberOfCities);
        visited[tour[0]] = true;

        for (int i = 1; i < numberOfCities; i++) {
            int nextCity = selectNextCity(tour[i-1], visited);
            tour[i] = nextCity;
            visited[nextCity] = true;
        }
        return tour;
    }

    // Выбор следующего города на основе вероятностей
    private int selectNextCity(int currentCity, boolean[] visited) {
        double[] probabilities = new double[numberOfCities];
        double sum = 0;

        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromones[currentCity][i], alpha) *
                        Math.pow(1.0/distances[currentCity][i], beta);
                sum += probabilities[i];
            }
        }

        double value = random.nextDouble() * sum;
        double cumulative = 0;
        for (int i = 0; i < numberOfCities; i++) {
            if (!visited[i]) {
                cumulative += probabilities[i];
                if (cumulative >= value) {
                    return i;
                }
            }
        }
        return -1; // не должно произойти
    }

    // Вычисление длины маршрута
    private double calculateTourLength(int[] tour) {
        double length = 0;
        for (int i = 0; i < numberOfCities - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }
        length += distances[tour[numberOfCities - 1]][tour[0]];
        return length;
    }

    // Обновление феромонов
    private void updatePheromones(int[][] ants, double[] tourLengths) {
        // Испарение феромонов
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {

                pheromones[i][j] *= (1.0 - evaporation);
            }
        }

        // Добавление новых феромонов от муравьев
        for (int ant = 0; ant < numberOfAnts; ant++) {
            double contribution = Q / tourLengths[ant];
            for (int i = 0; i < numberOfCities - 1; i++) {
                pheromones[ants[ant][i]][ants[ant][i + 1]] += contribution;
                pheromones[ants[ant][i + 1]][ants[ant][i]] += contribution;
            }
            pheromones[ants[ant][numberOfCities - 1]][ants[ant][0]] += contribution;
            pheromones[ants[ant][0]][ants[ant][numberOfCities - 1]] += contribution;
        }
    }

    // Пример использования
    public static void main(String[] args) {
        int n = 5; // количество городов
        AntColonyOptimization aco = new AntColonyOptimization(n);

        // Пример матрицы расстояний
        double[][] distances = {
                {0, 10, 15, 20, 25},
                {10, 0, 35, 25, 30},
                {15, 35, 0, 30, 35},
                {20, 25, 30, 0, 40},
                {25, 30, 35, 40, 0}
        };

        aco.setDistanceMatrix(distances);
        int[] bestTour = aco.solve(100); // 100 итераций

        // Вывод результата
        System.out.println("Лучший маршрут:");
        for (int city : bestTour) {
            System.out.print(city + " -> ");
        }
        System.out.println(bestTour[0]);
        System.out.println("Длина маршрута: " + aco.calculateTourLength(bestTour));
    }
}
