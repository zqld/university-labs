package com.mycompany.inheritance;
// Класс, представляющий мебель
public class Furniture {
    // Поля, характеризующие мебель: цена, материал каркаса и материал обивки
    int price;
    String frameMaterial;
    String upholsteryMaterial;

    // Конструктор класса для инициализации переменных мебели
    public Furniture(int price, String frameMaterial, String upholsteryMaterial) {
        this.price = price;
        this.frameMaterial = frameMaterial;
        this.upholsteryMaterial = upholsteryMaterial;
    }

    // Метод отображения информации о мебели
    public void displayInfo() {
        System.out.println("Этот каркас мебели сделан из " + frameMaterial + ", а её обивка сделана из " + upholsteryMaterial + " и стоит " + price + " рублей.");
    }

    // Метод, описывающий общее использование мебели
    public void Usage() {
        System.out.println("Зависит от типа мебели.");
    }
    // Метод, который в дочерних классах мы будем переопределять в зависимости от конкретного типа мебели
}
