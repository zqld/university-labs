package patern;

//Main.java:
//
//Демонстрируется создание двух объектов Computer: один с полным набором параметров (игровой ПК) и один с минимальным набором параметров (офисный ПК).
//Выводятся данные о созданных объектах Computer.
// Main.java
public class Main {
    public static void main(String[] args) {
        // Создание объекта Computer с помощью Builder
        Computer gamingPC = new Computer.ComputerBuilder("Intel i9", "32GB")
                .setGPU("NVIDIA RTX 3080")
                .setSSD("1TB")
                .setGraphicsCardEnabled(true)
                .setBluetoothEnabled(true)
                .build();

        // Создание объекта Computer с минимальным набором параметров
        Computer officePC = new Computer.ComputerBuilder("Intel i5", "8GB")
                .setHDD("1TB")
                .build();

        // Вывод информации о созданных компьютерах
        System.out.println(gamingPC);
        System.out.println(officePC);
    }
}
