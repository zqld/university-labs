package com.mycompany.inheritance;
/* Класс Armchair (Кресло), является подклассом Furniture */
class Armchair extends Furniture {
    private int height; // Высота кресла

    // Конструктор класса Armchair, вызывает конструктор родительского класса Furniture
    public Armchair(int price, String frameMaterial, String upholsteryMaterial, int height) {
        super(price, frameMaterial, upholsteryMaterial);
        // Обращаемся к полям и методам родительского класса
        this.height = height; // Инициализируем высоту кресла
        // Добавление нового параметра - высоты кресла
    }

    // Переопределенный виртуальный метод Usage для кресла
    @Override
    public void Usage() {
        System.out.println("На кресле сидят.");
    }

    // Метод Back для описания спинки кресла
    public void Back() {
        System.out.println("Спинка высокая.");
    }

    // Метод для вывода информации о размере кресла в зависимости от высоты
    public void ChairSize() {
        String chairSize = height >= 80 ? " большой " : height == 70 ? " средний " : "маленький";
        System.out.printf("Высота стула равна %d, размер стула %s", height, chairSize);
    }
}
