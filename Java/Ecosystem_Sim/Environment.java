package org.broker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment {
    // Списки для хранения объектов растений, травоядных и хищников
    private List<Plant> plants;
    private List<Herbivore> herbivores;
    private List<Predator> predators;

    // Конструктор, который инициализирует эти списки
    public Environment() {
        plants = new ArrayList<>();     // Инициализация списка для растений
        herbivores = new ArrayList<>(); // Инициализация списка для травоядных
        predators = new ArrayList<>();  // Инициализация списка для хищников
    }

    // Метод для добавления растения в среду
    public void addPlant(Plant plant) {
        plants.add(plant); // Добавляем растение в список
    }

    // Метод для добавления травоядного в среду
    public void addHerbivore(Herbivore herbivore) {
        herbivores.add(herbivore); // Добавляем травоядное в список
    }

    // Метод для добавления хищника в среду
    public void addPredator(Predator predator) {
        predators.add(predator); // Добавляем хищника в список
    }

    // Метод обновления состояния агентов
    public void update() {
        // Обновление состояния всех растений
        for (Plant plant : plants) {
            plant.update(); // Каждое растение обновляется (например, восстанавливает здоровье)
        }

        // Обновление состояния всех травоядных
        for (Herbivore herbivore : herbivores) {
            herbivore.update(); // Каждое травоядное обновляется (например, двигается)
        }

        // Обновление состояния всех хищников
        for (Predator predator : predators) {
            predator.update(); // Каждый хищник обновляется (например, двигается)
        }

        // Взаимодействие травоядных с растениями
        for (Herbivore herbivore : herbivores) {
            for (Plant plant : plants) {
                herbivore.interact(plant); // Травоядные взаимодействуют с растениями (например, едят)
            }
        }

        // Взаимодействие хищников с травоядными
        for (Predator predator : predators) {
            for (Herbivore herbivore : herbivores) {
                predator.interact(herbivore); // Хищники взаимодействуют с травоядными (например, едят)
            }
        }

        // Удаление мертвых агентов
        plants.removeIf(plant -> !plant.isAlive()); // Удаление мертвых растений
        herbivores.removeIf(herbivore -> !herbivore.isAlive()); // Удаление мертвых травоядных
        predators.removeIf(predator -> !predator.isAlive()); // Удаление мертвых хищников
    }

    // Метод отображения всех агентов на экране
    public void draw(Graphics g) {
        // Отображение всех растений
        for (Plant plant : plants) {
            plant.draw(g); // Рисуем каждое растение
        }

        // Отображение всех травоядных
        for (Herbivore herbivore : herbivores) {
            herbivore.draw(g); // Рисуем каждое травоядное
        }

        // Отображение всех хищников
        for (Predator predator : predators) {
            predator.draw(g); // Рисуем каждого хищника
        }
    }
}
