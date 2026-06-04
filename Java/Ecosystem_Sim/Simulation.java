package org.broker;

import javax.swing.*;
import java.awt.*;

public class Simulation extends JFrame {
    // Экземпляр класса Environment, представляющий всю среду симуляции
    private Environment environment;

    // Конструктор класса Simulation
    public Simulation() {
        // Инициализация среды симуляции
        environment = new Environment();

        // Добавляем растения в среду
        // Здесь создаются несколько экземпляров класса Plant (растений) с разными координатами
        environment.addPlant(new Plant(10, 10)); // Растение на позиции (10, 10)
        environment.addPlant(new Plant(15, 15)); // Растение на позиции (15, 15)
        environment.addPlant(new Plant(15, 10)); // Растение на позиции (15, 10)
        environment.addPlant(new Plant(10, 15)); // Растение на позиции (10, 15)
        environment.addPlant(new Plant(15, 17)); // Растение на позиции (15, 17)

        // Добавляем травоядных в среду
        // Здесь создаются несколько экземпляров класса Herbivore (травоядных) с разными координатами
        environment.addHerbivore(new Herbivore(20, 20)); // Травоядное на позиции (20, 20)
        environment.addHerbivore(new Herbivore(25, 25)); // Травоядное на позиции (25, 25)
        environment.addHerbivore(new Herbivore(25, 25)); // Травоядное на позиции (25, 25)
        environment.addHerbivore(new Herbivore(25, 25)); // Травоядное на позиции (25, 25)
        environment.addHerbivore(new Herbivore(25, 25)); // Травоядное на позиции (25, 25)

        // Добавляем хищников в среду
        // Здесь создаются несколько экземпляров класса Predator (хищников) с разными координатами
        environment.addPredator(new Predator(30, 30)); // Хищник на позиции (30, 30)
        environment.addPredator(new Predator(35, 35)); // Хищник на позиции (35, 35)
        environment.addPredator(new Predator(35, 35)); // Хищник на позиции (35, 35)
        environment.addPredator(new Predator(35, 35)); // Хищник на позиции (35, 35)
        environment.addPredator(new Predator(35, 35)); // Хищник на позиции (35, 35)

        // Устанавливаем размеры окна (500x500 пикселей)
        setSize(500, 500);
        // Устанавливаем операцию закрытия окна (выход из программы при закрытии окна)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Делаем окно видимым
        setVisible(true);
    }

    // Переопределяем метод paint, чтобы рисовать объекты на экране
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Отображаем состояние среды на экране
        environment.draw(g);
    }

    // Главный метод, запускающий симуляцию
    public static void main(String[] args) {
        // Создаем и запускаем экземпляр симуляции
        Simulation simulation = new Simulation();

        // Бесконечный цикл, который обновляет и перерисовывает состояние среды
        while (true) {
            // Обновляем состояние среды (движение агентов, взаимодействие и т.д.)
            simulation.environment.update();
            // Перерисовываем окно
            simulation.repaint();
            try {
                // Пауза между обновлениями, чтобы обновления не происходили слишком быстро
                Thread.sleep(100); // Пауза в 100 миллисекунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
