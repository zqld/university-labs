package zookeeper;

import zoo.Animal;

// Интерфейс для определения метода кормления животных.
public interface Feeding {
    // Метод feeding принимает объект Animal и используется для кормления животных.
    void feeding(Animal animal);
}
