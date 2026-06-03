package zookeeper;

import zoo.Animal;

// Класс, представляющий смотрителя зоопарка, обладающего функциями кормления и ухода за животными.
public class Zookeeper implements Feeding, Wash {
    // Реализация метода feeding интерфейса Feeding.
    @Override
    public void feeding(Animal animal) {
        System.out.println("Смотритель зоопарка кормит " + animal.getName() + ".");
    }

    // Реализация метода wash интерфейса Wash.
    @Override
    public void wash(Animal animal) {
        System.out.println("Смотритель зоопарка моет " + animal.getName() + ".");
    }
}
