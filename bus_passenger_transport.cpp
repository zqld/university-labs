#include <iostream>
using namespace std;

int main() {
    int passengers;  // Переменная для хранения количества пассажиров
    int bus_capacity = 50;  // Вместимость одного автобуса

    // Ввод количества пассажиров, ожидающих на автовокзале
    cout << "Enter the number of passengers waiting: ";
    cin >> passengers;

    // Рассчитываем количество пассажиров, которые поедут в последнем автобусе
    int remaining_passengers = passengers % bus_capacity;  // Оставшиеся пассажиры, которые не поместятся в предыдущие автобусы
    int free_seats_in_last_bus = bus_capacity - remaining_passengers;  // Свободные места в последнем автобусе

    // Выводим результат
    cout << "The number of free seats in the last bus: " << free_seats_in_last_bus << endl;

    return 0;
}