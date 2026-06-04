#include <iostream>
using namespace std;

// Функция для проверки, является ли число палиндромом
bool isPalindrome(int number) {
    int original = number;  // Сохраняем оригинальное число
    int reversed = 0;  // Переменная для хранения перевернутого числа

    // Разворачиваем число
    while (number > 0) {
        int digit = number % 10;  // Получаем последнюю цифру
        reversed = reversed * 10 + digit;  // Добавляем цифру в перевернутое число
        number /= 10;  // Убираем последнюю цифру из исходного числа
    }

    // Сравниваем исходное и перевернутое число
    return original == reversed;
}

int main() {
    int number;

    // Ввод числа
    cout << "Enter a number: ";
    cin >> number;

    // Проверяем, является ли число палиндромом
    if (isPalindrome(number)) {
        cout << number << " is a palindrome" << endl;
    } else {
        cout << number << " is not a palindrome" << endl;
    }

    return 0;
}