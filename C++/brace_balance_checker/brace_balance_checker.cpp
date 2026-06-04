#include <iostream>
#include <fstream>
#include <stack>
#include <string>

using namespace std;

// Функция для проверки баланса фигурных скобок в строке
bool checkBalance(const string& code) {
    stack<char> s;  // Стек для хранения открывающих скобок
    
    for (char ch : code) {
        if (ch == '{') {
            s.push(ch);  // Если открывающая скобка, кладем в стек
        } else if (ch == '}') {
            if (s.empty()) {
                return false;  // Если закрывающая скобка без пары, ошибка
            }
            s.pop();  // Если есть пара, убираем верхний элемент из стека
        }
    }

    return s.empty();  // Если стек пуст, значит все скобки сбалансированы
}

int main() {
    string filename;
    cout << "Enter the name of the source file: ";
    cin >> filename;

    ifstream file(filename);  // Открываем файл для чтения

    if (!file) {
        cout << "Error opening file!" << endl;
        return 1;
    }

    string line;
    string fileContent;
    
    // Читаем файл построчно
    while (getline(file, line)) {
        fileContent += line + "\n";
    }

    file.close();  // Закрываем файл после чтения

    // Проверка баланса фигурных скобок
    bool isBalanced = checkBalance(fileContent);
    
    // Результат проверки
    string result = isBalanced ? "The braces are balanced." : "The braces are not balanced.";
    
    // Выводим результат на экран
    cout << result << endl;
    
    // Записываем результат в файл
    ofstream outFile("out.txt");  // Открываем файл для записи
    if (outFile) {
        outFile << result << endl;
        outFile.close();  // Закрываем файл после записи
    } else {
        cout << "Error writing to output file!" << endl;
        return 1;
    }

    return 0;
}
