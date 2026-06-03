package zoo;

import zookeeper.*;

// Конкретный класс Cougar, представляющий пуму в зоопарке.
public class Cougar extends Animal implements EatMeat {

    // Конструктор для создания объекта Cougar с именем и возрастом.
    public Cougar(String name, int age) {
        super(name, age);
    }

    // Реализация абстрактного метода makeSounds из класса Animal.
    @Override
    public void makeSounds() {
        System.out.println("Пума по имени " + getName() + " рычит, она появилась в зоопарке " + getAge() + " года назад.");
    }
}
