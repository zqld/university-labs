package zoo;

import zookeeper.*;

public class Leo extends Animal implements EatMeat{
// Конкретный класс Leo, представляющий льва в зоопарке.

    public Leo(String name, int age) {
        // Конструктор для создания объекта Leo с именем и возрастом.
        super(name, age);
    }
    
     // Реализация абстрактного метода makeSounds из класса Animal.
    @Override
    public void makeSounds(){
        System.out.println("Лев по имени "+getName()+" рычит, он появился в зоопарке "+getAge()+" года назад.");
    }
      
}
