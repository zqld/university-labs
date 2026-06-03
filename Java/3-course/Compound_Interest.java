package com.mycompany.compoundinterestnew;  // Объявление пакета (если необходимо).

import java.util.Scanner;  // Импорт класса Scanner для считывания ввода пользователя.

public class CompoundInterestNew {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "Windows-1251");  // Создание объекта Scanner для ввода пользователя.

        System.out.println("Введите номер операции:");  // Вывод сообщения пользователю.
        System.out.println("1. Найти через формулу сложного процента будущую стоимость вклада;");
        System.out.println("2. Посчитать каким должен быть процент, чтобы от Числа1 дойти до Числа2 за N периодов;");
        System.out.print("Выполнить операцию: ");  // Запрос ввода номера операции у пользователя.
        int operation = scanner.nextInt();  // Считывание номера операции от пользователя.
        System.out.print("\n");  // Вывод пустой строки для читаемости.

        if (operation == 1) {
            // Выполнение операции №1: вычисление будущей стоимости вклада.
            
            System.out.print("Введите начальную сумму (Число1): ");
            double principal = scanner.nextDouble();  // Считывание начальной суммы от пользователя и сохранение в переменной principal.

            System.out.print("Введите процентную ставку (Процент): ");
            double rate = scanner.nextDouble();  // Считывание процентной ставки от пользователя и сохранение в переменной rate.

            System.out.print("Введите количество периодов (N): ");
            double periods = scanner.nextDouble();  // Считывание количества периодов от пользователя и сохранение в переменной periods.

            double futureValue = calculateFutureValue(principal, rate, periods);  // Вычисление будущей стоимости с использованием функции calculateFutureValue.
            System.out.printf("Будущая стоимость: %.2f рублей.%n", futureValue);  // Вывод результата на экран.

        } else {
            if (operation == 2) {
                // Выполнение операции №2: вычисление необходимой процентной ставки.

                System.out.print("Введите желаемую будущую стоимость (Число2): ");
                double newPrincipal = scanner.nextDouble();  // Считывание желаемой будущей стоимости от пользователя и сохранение в переменной newPrincipal.

                System.out.print("Введите начальную сумму (Число1): ");
                double principal = scanner.nextDouble();  // Считывание начальной суммы от пользователя и сохранение в переменной principal.

                System.out.print("Введите количество периодов (N): ");
                int periods = scanner.nextInt();  // Считывание количества периодов от пользователя и сохранение в переменной periods.

                double requiredRate = reverseCompoundInterest(principal, newPrincipal, periods);  // Вычисление необходимой процентной ставки с использованием функции reverseCompoundInterest.
                System.out.printf("Необходимая процентная ставка: %.2f%%%n", requiredRate);  // Вывод результата на экран.
            } else {
                System.out.println("Такой операции нет!");  // Вывод сообщения, если номер операции некорректен.
            }
        }
    }

    // Функция для вычисления будущей стоимости (сложного процента)
    public static double calculateFutureValue(double principal, double rate, double periods) {
        double result = principal;

        for (int i = 0; i < periods; i++) {
            result *= (1 + (rate / 100));  // Вычисление будущей стоимости в цикле.
        }

        return result;  // Возврат результата (будущей стоимости).
    }

    // Функция для обратного вычисления процентной ставки
    public static double reverseCompoundInterest(double principal, double newPrincipal, int periods) {
        double low = 0;
        double high = 100;
        double precision = 0.01;

        while (low < high) {
            double mid = (low + high) / 2;
            double calculatedFutureValue = calculateFutureValue(principal, mid, periods);

            if (Math.abs(calculatedFutureValue - newPrincipal) < precision) {
                return mid;  // Возврат необходимой процентной ставки.
            } else if (calculatedFutureValue < newPrincipal) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return -1;  // Если процентная ставка не найдена в пределах заданной точности.
    }
}

