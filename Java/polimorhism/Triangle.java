/*
Этот класс Triangle является подклассом SquareFigures и представляет 
собой конкретную реализацию треугольника. В конструкторе класса инициализируются 
поля base (основание) и height (высота), а метод CalculatingTheArea() переопределен для 
вычисления площади треугольника по формуле 0.5 * основание * высота.
*/
package com.mycompany.polymorphism;

// Класс Triangle расширяет класс SquareFigures, представляя собой треугольник.
class Triangle extends SquareFigures {
    private double base; // Основание треугольника
    private double height; // Высота треугольника

    // Конструктор класса Triangle принимает длину основания и высоту треугольника, и инициализирует поля base и height.
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // Переопределенный метод CalculatingTheArea() вычисляет площадь треугольника.
    @Override
    public double CalculatingTheArea() {
        return 0.5 * base * height; // Площадь треугольника = (0.5 * основание * высота)
    }
}