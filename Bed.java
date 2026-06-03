package com.mycompany.inheritance;
/* Подкласс Bed расширяет функциональность базового класса Furniture */
class Bed extends Furniture {
    /* Количество спальных мест на кровати */
    public int numberOfSeats;

    // Конструктор класса Bed, вызывает конструктор родительского класса Furniture
    public Bed(int numberOfSeats, int price, String frameMaterial, String upholsteryMaterial) {
        super(price, frameMaterial, upholsteryMaterial);
        // Обращаемся к полям и методам родительского класса
        this.numberOfSeats = numberOfSeats; // Инициализируем количество спальных мест
    }

    // Метод для отображения информации о вместимости кровати
    public void Сapacity() {
        System.out.println("На этой кровати может спать " + numberOfSeats);
    }

    // Переопределенный метод Usage для кровати
    @Override
    public void Usage() {
        System.out.println("Лежат на кровати.");
        /* Полиморфизм: Метод Usage() в классе Bed переопределен. Это пример полиморфизма, где одно и то же имя метода используется
           для разных классов, и метод выполняется в соответствии с конкретной реализацией класса. Это улучшает гибкость кода. */
    }
}
