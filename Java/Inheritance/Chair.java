package com.mycompany.inheritance;
// Класс Chair (Стул), является подклассом Armchair.
class Chair extends Armchair {
    private String color; // Цвет спинки стула.

    // Конструктор класса Chair, вызывает конструктор родительского класса Armchair.
    public Chair(int price, String frameMaterial, String upholsteryMaterial, String color, int height) {
        super(price, frameMaterial, upholsteryMaterial, height);
        // Обращаемся к полям и методам родительского класса.
        this.color = color; // Инициализируем цвет спинки стула.
    }

    // Переопределенный метод Back для стула
    @Override
    public void Back() {
        System.out.println("Спинка средняя и цвет спинки " + color);
    }
}
