package com.mycompany.inheritance;

// Дочерний класс, представляющий диван и наследующий от родительского класса Furniture
// Этот класс демонстрирует принцип наследования и повторного использования кода
class Sofa extends Furniture {

    // Конструктор класса, вызывает конструктор родительского класса Furniture
    public Sofa(int price, String frameMaterial, String upholsteryMaterial) {
        super(price, frameMaterial, upholsteryMaterial);
        // Обращаемся к полям и методам родительского класса
    }

    // Переопределенный метод Usage, предоставляющий специфичное поведение для дивана
    @Override
    public void Usage() {
        System.out.println("На диване сидят.");
        // Пример упрощения поддержки кода: добавляем новый метод
    }
}
