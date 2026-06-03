package com.mycompany.compositionandaggregation;
/*
В классе Transmission используется агрегация:
Поле private String type; представляет данные, которые агрегируются внутри объекта Transmission. 
Объект Transmission может существовать независимо и содержит данные о типе трансмиссии.
Метод Availability() использует данные type, чтобы вывести информацию о доступности трансмиссии. 
Он не создает объекты других классов и просто использует данные, которые уже находятся внутри объекта Transmission.
*/
// Класс, представляющий трансмиссию
public class Transmission {
    private String type;

    // Конструктор для установки типа трансмиссии
    public Transmission(String type) {
        this.type = type;
    }

    // Геттер для получения типа трансмиссии
    public String getType() {
        return type;
    }

    // Сеттер для установки типа трансмиссии (не используется в данной программе)
    public void setType(String type) {
        this.type = type;
    }

    // Метод для вывода информации о доступности трансмиссии (агрегация)
    public void Availability(){
        System.out.println(type + " можно посмотреть в каталоге трансмиссии типа.");
    }
}
