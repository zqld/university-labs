#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

// Структура для представления координат точки
struct Point {
    double x, y;
};

// Функция для вычисления площади многоугольника
double polygonArea(const vector<Point>& points) {
    int n = points.size();
    double area = 0.0;

    // Формула для площади через координаты
    for (int i = 0; i < n; i++) {
        int j = (i + 1) % n;  // Следующая вершина, с учётом цикличности
        area += points[i].x * points[j].y - points[i].y * points[j].x;
    }

    return fabs(area) / 2.0;
}

int main() {
    int n;

    // Ввод количества вершин многоугольника
    cout << "Enter the number of vertices of the polygon: ";
    cin >> n;

    vector<Point> points(n);

    // Ввод координат вершин
    cout << "Enter the coordinates of the vertices (x y):\n";
    for (int i = 0; i < n; i++) {
        cout << "Vertex " << i + 1 << ": ";
        cin >> points[i].x >> points[i].y;
    }

    // Вычисление площади
    double area = polygonArea(points);

    // Вывод результата
    cout << "The area of the polygon is: " << area << endl;

    return 0;
}