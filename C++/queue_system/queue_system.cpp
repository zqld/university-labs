#include <iostream>
#include <vector>
using namespace std;

class Queue {
private:
    vector<int> queue;  // Вектор для хранения данных очереди

public:
    // Метод для добавления элемента в конец очереди
    void add(int id) {
        queue.push_back(id);  // Добавляем элемент в конец очереди
    }

    // Метод для удаления первого элемента из очереди
    void remove() {
        if (queue.empty()) {
            cout << "Queue is empty!" << endl;
        } else {
            queue.erase(queue.begin());  // Удаляем первый элемент из очереди
        }
    }

    // Метод для вывода элементов очереди
    void print() const {
        if (queue.empty()) {
            cout << "Queue is empty!" << endl;
        } else {
            cout << "Queue: ";
            for (int id : queue) {
                cout << id << " ";
            }
            cout << endl;
        }
    }

    // Метод для получения размера очереди
    size_t size() const {
        return queue.size();
    }
};

int main() {
    Queue q;

    // Добавляем элементы в очередь
    q.add(101);
    q.add(102);
    q.add(103);

    // Выводим состояние очереди
    q.print();

    // Удаляем первый элемент
    q.remove();

    // Выводим состояние очереди после удаления
    q.print();

    // Добавляем еще один элемент
    q.add(104);
    q.print();

    // Удаляем все элементы
    q.remove();
    q.remove();
    q.remove();

    // Выводим состояние очереди после удаления всех элементов
    q.print();

    return 0;
}