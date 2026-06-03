package com.mycompany.inheritance;

// Основной класс для демонстрации принципов наследования в Java
public class FurnitureApp {
    // Основной метод программы
    public static void main(String[] args) {
        // Создаем объект основного (родительского) класса Furniture
        Furniture furniture = new Furniture(2000, "дерева", "ткани");
        // Выводим информацию о мебели с использованием метода displayInfo
        furniture.displayInfo();
        // Вызываем метод Usage из родительского класса
        furniture.Usage();

        // Создаем объект дочернего класса Sofa
        Sofa sofa = new Sofa(1900, "дерева", "ткани");
        // Выводим информацию о диване
        sofa.displayInfo();
        // Вызываем метод Usage из дочернего класса Sofa
        sofa.Usage();

        // Создаем объект дочернего класса Bed
        Bed bed = new Bed(4, 4000, "сталь", "пух");
        // Выводим информацию о кровати
        bed.displayInfo();
        // Вызываем метод Capacity из дочернего класса Bed
        bed.Capacity();

        // Создаем объект дочернего класса Armchair
        Armchair armchair = new Armchair(19000, "дерева", "кожы", 81);
        // Выводим информацию о кресле
        armchair.displayInfo();
        // Вызываем метод Usage из дочернего класса Armchair
        armchair.Usage();
        // Вызываем метод ChairSize из класса Armchair
        armchair.ChairSize();

        // Создаем объект дочернего класса Chair, который является подклассом Armchair, а Armchair - подклассом Furniture
        // Это пример повторного использования кода
        Chair chair = new Chair(19000, "дерева", "кожы", "красный", 90);
        // Вызываем метод Usage из дочернего класса Chair
        chair.Usage();
        // Вызываем метод Back из класса Chair
        chair.Back();
    }
}
