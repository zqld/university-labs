package org.broker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SimulatedAnnealingVisualization extends JPanel {
    private Random random;
    private double currentX, currentY;
    private double bestX, bestY;
    private double bestValue;
    private double minBound, maxBound;
    private int totalIterations;
    private double temperature;
    private double stepSize;

    // Конструктор
    public SimulatedAnnealingVisualization(double minBound, double maxBound) {
        this.random = new Random();
        this.minBound = minBound;
        this.maxBound = maxBound;
        this.currentX = minBound + (maxBound - minBound) * random.nextDouble();
        this.currentY = minBound + (maxBound - minBound) * random.nextDouble();
        this.bestX = currentX;
        this.bestY = currentY;
        this.bestValue = evaluate(currentX, currentY);
        this.totalIterations = 0;

        // Настройка панели
        setPreferredSize(new Dimension(500, 500));
        setupGUI();
    }

    // Целевая функция
    private double evaluate(double x, double y) {
        return 1.0 / (1.0 + x * x + y * y);
    }

    // Генерация соседа
    private double[] generateNeighbor(double x, double y, double stepSize) {
        double newX = x + (random.nextDouble() - 0.5) * 2 * stepSize;
        double newY = y + (random.nextDouble() - 0.5) * 2 * stepSize;
        newX = Math.max(minBound, Math.min(maxBound, newX));
        newY = Math.max(minBound, Math.min(maxBound, newY));
        return new double[]{newX, newY};
    }

    // Оптимизация с визуализацией
    public void optimize(double initialTemp, double finalTemp, double coolingRate,
                         int iterationsPerTemp, double initialStepSize) {
        this.temperature = initialTemp;
        this.stepSize = initialStepSize;
        totalIterations = 0;

        Timer timer = new Timer(50, new ActionListener() {
            int iteration = 0;
            int tempIter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (temperature <= finalTemp) {
                    ((Timer)e.getSource()).stop();
                    System.out.println("\nОптимизация завершена!");
                    System.out.printf("Всего итераций: %d%n", totalIterations);
                    System.out.printf("Лучшее решение: x=%.6f, y=%.6f%n", bestX, bestY);
                    System.out.printf("Максимальное значение: %.6f%n", bestValue);
                    return;
                }

                if (tempIter < iterationsPerTemp) {
                    totalIterations++;
                    tempIter++;

                    double[] neighbor = generateNeighbor(currentX, currentY, stepSize);
                    double newX = neighbor[0];
                    double newY = neighbor[1];

                    double currentValue = evaluate(currentX, currentY);
                    double newValue = evaluate(newX, newY);
                    double delta = newValue - currentValue;

                    if (delta > 0) {
                        currentX = newX;
                        currentY = newY;
                        if (newValue > bestValue) {
                            bestX = newX;
                            bestY = newY;
                            bestValue = newValue;
                        }
                    } else {
                        double prob = Math.exp(delta / temperature);
                        if (random.nextDouble() < prob) {
                            currentX = newX;
                            currentY = newY;
                        }
                    }

                    repaint();  // Обновление визуализации
                } else {
                    temperature *= coolingRate;
                    stepSize *= 0.99;
                    tempIter = 0;

                    if (totalIterations % 1000 == 0) {
                        System.out.printf("Итерация %d, T=%.4f, Лучшее значение=%.6f " +
                                        "(x=%.6f, y=%.6f)%n",
                                totalIterations, temperature, bestValue, bestX, bestY);
                    }
                }
            }
        });
        timer.start();
    }

    // Отрисовка
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Отрисовка фона с градиентом значений функции
        for (int px = 0; px < width; px += 5) {
            for (int py = 0; py < height; py += 5) {
                double x = minBound + (maxBound - minBound) * px / width;
                double y = minBound + (maxBound - minBound) * py / height;
                double value = evaluate(x, y);
                int color = (int)(255 * value);
                g2d.setColor(new Color(color, color, color));
                g2d.fillRect(px, py, 5, 5);
            }
        }

        // Преобразование координат в пиксели
        int currPx = (int)((currentX - minBound) * width / (maxBound - minBound));
        int currPy = (int)((currentY - minBound) * height / (maxBound - minBound));
        int bestPx = (int)((bestX - minBound) * width / (maxBound - minBound));
        int bestPy = (int)((bestY - minBound) * height / (maxBound - minBound));

        // Текущая точка (синяя)
        g2d.setColor(Color.BLUE);
        g2d.fillOval(currPx - 5, currPy - 5, 10, 10);

        // Лучшая точка (красная)
        g2d.setColor(Color.RED);
        g2d.fillOval(bestPx - 5, bestPy - 5, 10, 10);

        // Информационная панель
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("Итераций: %d", totalIterations), 10, 20);
        g2d.drawString(String.format("Текущие: x=%.3f, y=%.3f", currentX, currentY), 10, 40);
        g2d.drawString(String.format("Лучшие: x=%.3f, y=%.3f, f=%.6f",
                bestX, bestY, bestValue), 10, 60);
    }

    // Настройка GUI
    private void setupGUI() {
        JFrame frame = new JFrame("Simulated Annealing Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);  // Добавляем текущий объект как панель
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Геттеры
    public double getBestX() { return bestX; }
    public double getBestY() { return bestY; }
    public double getBestValue() { return bestValue; }
    public int getTotalIterations() { return totalIterations; }

    // Главный метод
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            double minBound = -5.0;
            double maxBound = 5.0;
            double initialTemp = 1000.0;
            double finalTemp = 0.01;
            double coolingRate = 0.995;
            int iterationsPerTemp = 100;
            double initialStepSize = 1.0;

            SimulatedAnnealingVisualization sa =
                    new SimulatedAnnealingVisualization(minBound, maxBound);
            sa.optimize(initialTemp, finalTemp, coolingRate,
                    iterationsPerTemp, initialStepSize);
        });
    }
}