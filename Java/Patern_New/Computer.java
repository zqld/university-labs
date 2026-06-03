package patern;

//Computer.java:
//
//Объявлены обязательные и необязательные параметры.
//Конструктор Computer приватный и доступен только через ComputerBuilder.
//Класс ComputerBuilder содержит методы для установки каждого необязательного параметра и возвращает текущий объект Builder для цепочки вызовов.
//Метод build() создает объект Computer с текущими параметрами Builder.
// Computer.java
public class Computer {
    // Обязательные параметры
    private String CPU;
    private String RAM;

    // Необязательные параметры
    private String GPU;
    private String HDD;
    private String SSD;
    private boolean isGraphicsCardEnabled;
    private boolean isBluetoothEnabled;

    // Приватный конструктор, чтобы только Builder мог создавать объекты Computer
    private Computer(ComputerBuilder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.GPU = builder.GPU;
        this.HDD = builder.HDD;
        this.SSD = builder.SSD;
        this.isGraphicsCardEnabled = builder.isGraphicsCardEnabled;
        this.isBluetoothEnabled = builder.isBluetoothEnabled;
    }

    // Геттеры для получения значений полей
    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getGPU() {
        return GPU;
    }

    public String getHDD() {
        return HDD;
    }

    public String getSSD() {
        return SSD;
    }

    public boolean isGraphicsCardEnabled() {
        return isGraphicsCardEnabled;
    }

    public boolean isBluetoothEnabled() {
        return isBluetoothEnabled;
    }

    // Реализация паттерна Builder
    public static class ComputerBuilder {
        // Обязательные параметры
        private String CPU;
        private String RAM;

        // Необязательные параметры
        private String GPU;
        private String HDD;
        private String SSD;
        private boolean isGraphicsCardEnabled;
        private boolean isBluetoothEnabled;

        // Конструктор Builder с обязательными параметрами
        public ComputerBuilder(String CPU, String RAM) {
            this.CPU = CPU;
            this.RAM = RAM;
        }

        // Методы для установки необязательных параметров
        public ComputerBuilder setGPU(String GPU) {
            this.GPU = GPU;
            return this;
        }

        public ComputerBuilder setHDD(String HDD) {
            this.HDD = HDD;
            return this;
        }

        public ComputerBuilder setSSD(String SSD) {
            this.SSD = SSD;
            return this;
        }

        public ComputerBuilder setGraphicsCardEnabled(boolean isGraphicsCardEnabled) {
            this.isGraphicsCardEnabled = isGraphicsCardEnabled;
            return this;
        }

        public ComputerBuilder setBluetoothEnabled(boolean isBluetoothEnabled) {
            this.isBluetoothEnabled = isBluetoothEnabled;
            return this;
        }

        // Метод для создания объекта Computer
        public Computer build() {
            return new Computer(this);
        }
    }

    // Метод для представления объекта Computer в строковом формате
    @Override
    public String toString() {
        return "Computer [CPU=" + CPU + ", RAM=" + RAM + ", GPU=" + GPU + ", HDD=" + HDD + ", SSD=" + SSD
                + ", isGraphicsCardEnabled=" + isGraphicsCardEnabled + ", isBluetoothEnabled=" + isBluetoothEnabled + "]";
    }
}

