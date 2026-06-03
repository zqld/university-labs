package zoo;

// Абстрактный класс, представляющий животных в зоопарке.
public abstract class Animal {
    private String name; // Имя животного.
    private int age;    // Возраст животного.

    // Абстрактный метод для издания звуков животным.
    public abstract void makeSounds();

    // Конструктор для создания объекта Animal с именем и возрастом.
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Геттеры для получения имени и возраста животного.
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
