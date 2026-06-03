package com.mycompany.carddeck;

import java.util.ArrayList; // Импортируем класс ArrayList для работы с динамическими массивами.
import java.util.Collections; // Импортируем класс Collections для перемешивания колоды.
import java.util.List; // Импортируем класс List для работы с списками.

// Класс CardDeck представляет собой модель колоды карт.
public class CardDeck {
    private List<Card> deck; // Хранилище для карт.

    // Конструктор класса CardDeck инициализирует колоду и заполняет ее картами.
    public CardDeck() {
        deck = new ArrayList<>(); // Создаем пустой список для хранения карт и инициализируем колоду.
        initializeDeck(); // Вызываем метод для заполнения колоды картами.
    }

    // Метод initializeDeck() заполняет колоду всеми 52 картами.
    private void initializeDeck() {
        for (Suit suit : Suit.values()) { // Перебираем масти карт.
            for (Rank rank : Rank.values()) { // Перебираем достоинства карт.
                deck.add(new Card(rank, suit)); // Создаем карту и добавляем ее в колоду.
            }
        }
    }

    // Метод shuffle() перемешивает карты в колоде случайным образом.
    public void shuffle() {
        Collections.shuffle(deck); // Используем Collections.shuffle() для перемешивания колоды.
    }

    // Метод dealCard() извлекает и возвращает верхнюю карту из колоды.
    public Card dealCard() {
        if (!deck.isEmpty()) { // Проверяем, не пуста ли колода.
            return deck.remove(0); // Извлекаем и возвращаем первую карту из колоды.
        } else {
            return null; // Если колода пуста, возвращаем null.
        }
    }

    // Метод returnCardToDeck() возвращает карту в колоду.
    public void returnCardToDeck(Card card) {
        deck.add(card); // Добавляем карту обратно в колоду.
    }

    // Метод getDeckSize() возвращает текущее количество карт в колоде.
    public int getDeckSize() {
        return deck.size(); // Возвращаем размер колоды (количество карт).
    }

    // Метод printDeck() выводит все карты в колоде на экран.
    public void printDeck() {
        for (Card card : deck) { // Перебираем все карты в колоде.
            System.out.println(card); // Выводим каждую карту на экран.
        }
    }

    // Этот метод представляет собой пример использования класса CardDeck.
    public static void main(String[] args) {
        CardDeck cardDeck = new CardDeck(); // Создаем объект класса CardDeck.
        cardDeck.shuffle(); // Перемешиваем колоду.

        System.out.println("Колода перед раздачей карт:");
        cardDeck.printDeck(); // Выводим состояние колоды до раздачи карт.

        Card dealtCard = cardDeck.dealCard(); // Раздаем карту.
        if (dealtCard != null) {
            System.out.println("Сданная карта: " + dealtCard); // Выводим сданную карту.
        }

        System.out.println("Колода после сдачи карты:");
        cardDeck.printDeck(); // Выводим состояние колоды после раздачи.

        Card returnedCard = new Card(Rank.ACE, Suit.SPADES); // Создаем карту для возврата.
        cardDeck.returnCardToDeck(returnedCard); // Возвращаем карту в колоду.

        System.out.println("Колода после возврата карты:");
        cardDeck.printDeck(); // Выводим состояние колоды после возврата карты.
    }
}

