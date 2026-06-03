package com.mycompany.compositionandaggregation;
/*
Определим, где используется композиция и агрегация в классе Car:
Композиция:
Конструктор Car(PlushCubes plushCubes) представляет композицию. 
В этом конструкторе объект PlushCubes создается и используется исключительно внутри объекта Car. 
Объект PlushCubes является частью объекта Car и не может существовать независимо.
Агрегация:
Поле Transmission transmission; представляет агрегацию. 
Объект Transmission может быть создан и использован независимо от объекта Car. 
Объект Car просто содержит ссылку на объект Transmission, но не создает его самостоятельно.
Конструктор Car(String colorCar, String typeTransmission) также представляет агрегацию. 
В этом конструкторе объект Transmission создается исключительно внутри объекта Car, но объекты могут существовать независимо друг от друга.
*/
// Класс, представляющий автомобиль
public class Car {
    private String colorCar;
    Transmission transmission; // Ссылка на объект Transmission
    PlushCubes plushCubes; // Ссылка на объект PlushCubes

    // Конструктор, использующий композицию: принимает объект PlushCubes
    public Car(PlushCubes plushCubes) {
        this.plushCubes = plushCubes; // Устанавливаем ссылку на объект PlushCubes
    }

    // Метод для вывода информации о цвете кубиков внутри машины (композиция)
    public void CubesInTheCar(){
        System.out.println("Кубики будут "+plushCubes.getColor()+" цвета.");
    }

    // Конструктор, использующий агрегацию: принимает цвет и тип трансмиссии
    public Car(String colorCar, String typeTransmission) {
        this.colorCar = colorCar;
        this.transmission = new Transmission(typeTransmission); // Создаем объект Transmission
    }

    // Метод для вывода информации о типе трансмиссии и цвете машины (агрегация)
    public void printType(){
        System.out.println("Тип нашей трансмиссии "+transmission.getType()+" цвет машины "+colorCar+".");
    }
}
