#include <iostream>

int main() {
    int n;

    // Считываем входное число
    if (!(std::cin >> n)) {
        return 0;
    }

    // Цикл от N до 1 включительно
    for (int i = n; i >= 1; --i) {
        // Выводим текущее число
        std::cout << i << std::endl;

        // Если число кратно 5, выводим "Beep"
        if (i % 5 == 0) {
            std::cout << "Beep" << std::endl;
        }
    }

    return 0;
}