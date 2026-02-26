#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <algorithm>
#include <string>

using namespace std;

// Структура для хранения записи
struct Record {
    string phoneNumber;
    string name;
    string address;
    
    // Конструктор для инициализации записи
    Record(string phone, string name, string addr) : phoneNumber(phone), name(name), address(addr) {}
};

// Функция для извлечения числа из строки (номер телефона)
bool compareByPhoneNumber(const Record& a, const Record& b) {
    return a.phoneNumber < b.phoneNumber;
}

int main() {
    string filename;
    
    // Запрашиваем имя файла
    cout << "Enter the name of the file: ";
    cin >> filename;
    
    ifstream file(filename);  // Открываем файл для чтения
    
    if (!file) {
        cout << "Error opening file!" << endl;
        return 1;
    }

    vector<Record> records;  // Вектор для хранения записей
    string line;

    // Чтение данных из файла
    while (getline(file, line)) {
        stringstream ss(line);
        string phoneNumber, name, address;
        
        // Чтение номера телефона, ФИО и адреса
        getline(ss, phoneNumber, ',');
        getline(ss, name, ',');
        getline(ss, address);
        
        // Добавляем запись в вектор
        records.push_back(Record(phoneNumber, name, address));
    }

    file.close();  // Закрываем файл после чтения

    // Сортируем записи по номеру телефона
    sort(records.begin(), records.end(), compareByPhoneNumber);

    // Открываем файл для записи отсортированных данных
    ofstream outFile("sorted_phonebook.txt");
    
    if (outFile) {
        // Записываем отсортированные данные в новый файл
        for (const auto& record : records) {
            outFile << record.phoneNumber << ", " << record.name << ", " << record.address << endl;
        }
        outFile.close();  // Закрываем файл после записи
        cout << "The records have been sorted and saved to 'sorted_phonebook.txt'" << endl;
    } else {
        cout << "Error writing to output file!" << endl;
        return 1;
    }

    return 0;
}