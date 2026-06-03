package zoo;

// Конкретный класс Elephant, представляющий слона в зоопарке.
public class Elephant extends Animal {
    
    // Конструктор для создания объекта Elephant с именем и возрастом.
    public Elephant(String name, int age) {
        super(name, age);
    }
    
    // Реализация абстрактного метода makeSounds из класса Animal.
    @Override
    public void makeSounds() {
        System.out.println("Слон по имени " + getName() + " дудит, он появился в зоопарке " + getAge() + " года назад.");
    }
}
