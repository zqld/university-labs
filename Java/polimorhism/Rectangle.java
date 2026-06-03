/*
Этот класс Rectangle расширяет класс SquareFigures и представляет 
собой конкретную реализацию прямоугольника. В конструкторе класса инициализируются поля 
length и width, а метод CalculatingTheArea() переопределен для вычисления площади 
прямоугольника по формуле длина * ширина.
*/
package com.mycompany.polymorphism;

// Класс Rectangle расширяет класс SquareFigures и представляет собой прямоугольник.
class Rectangle extends SquareFigures {
    private double length; // Длина прямоугольника
    private double width;  // Ширина прямоугольника

    // Конструктор класса Rectangle принимает длину и ширину прямоугольника и инициализирует поля length и width.
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Переопределенный метод CalculatingTheArea() вычисляет площадь прямоугольника.
    @Override
    public double CalculatingTheArea() {
        return length * width; // Площадь прямоугольника = длина * ширина
    }
}