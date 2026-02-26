#include <iostream>
#include <algorithm>  // Для использования функции min
using namespace std;

int main() {
    int ages[5];  // Массив для хранения возрастов пяти человек

    // Ввод возрастов пяти человек
    cout << "Enter the ages of 5 people: ";
    for (int i = 0; i < 5; i++) {
        cin >> ages[i];
    }

    // Находим возраст самого младшего человека в группе
    int min_age = *min_element(ages, ages + 5);  // Функция min_element находит минимальный элемент в массиве

    // Стоимость билета для 5 человек без скидки
    double total_cost = 5 * 10.0;  // 5 билетов по 10 долларов

    // Скидка в процентах
    double discount = min_age;

    // Рассчитываем итоговую стоимость с учетом скидки
    double final_cost = total_cost - (total_cost * discount / 100.0);

    // Выводим итоговую стоимость
    cout << "Total cost after discount: " << final_cost << endl;

    return 0;
}
