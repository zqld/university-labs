package zookeeper;

import zoo.Animal;

// Интерфейс для определения метода для гигиенических процедур над животными.
public interface Wash {
    // Метод wash принимает объект Animal и используется для проведения гигиенических процедур над животными.
    void wash(Animal animal);
}
