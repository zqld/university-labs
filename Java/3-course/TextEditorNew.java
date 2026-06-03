package com.mycompany.texteditornew;  // Объявление пакета

import javax.swing.*;  // Импорт библиотеки Swing для создания GUI
import java.awt.*;  // Импорт библиотеки AWT для работы с компонентами GUI
import java.awt.event.ActionEvent;  // Импорт класса ActionEvent для обработки событий
import java.awt.event.ActionListener;  // Импорт интерфейса ActionListener для прослушивания событий
import javax.swing.JTextArea;  // Импорт класса JTextArea для текстового редактирования
import java.io.*;  // Импорт библиотеки для ввода-вывода файлов

public class TextEditorNew {  // Определение класса TextEditorNew
    private static JTextArea textArea;  // Объявление переменной JTextArea для текстового редактирования
    private static JFrame frame;  // Объявление переменной JFrame для основного окна приложения

    public static void main(String[] args) {  // Главный метод, точка входа в программу
        frame = new JFrame("Текстовый редактор");  // Создание объекта JFrame с заголовком "Текстовый редактор"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Установка операции закрытия приложения по умолчанию
        textArea = new JTextArea();  // Создание объекта JTextArea для редактирования текста
        frame.add(textArea, BorderLayout.CENTER);  // Добавление JTextArea в центр окна
        JMenuBar bar = new JMenuBar();  // Создание меню-бара
        frame.setJMenuBar(bar);  // Установка меню-бара для окна
        JMenu menu = new JMenu("Файл");  // Создание меню с названием "Файл"
        bar.add(menu);  // Добавление меню в меню-бар

        // Создание пунктов меню для открытия, сохранения, закрытия и очистки текста
        JMenuItem openMenuItem = new JMenuItem("Открыть");
        JMenuItem saveMenuItem = new JMenuItem("Сохранить");
        JMenuItem closeMenuItem = new JMenuItem("Закрыть");
        JMenuItem cleanTextMenuItem = new JMenuItem("Очистить поле");

        // Создание слушателя событий для обработки действий меню
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                switch (command) {
                    case "Открыть":
                        openFile();
                        break;
                    case "Сохранить":
                        saveFile();
                        break;
                    case "Закрыть":
                        System.exit(0);
                        break;
                    case "Очистить поле":
                        textArea.setText("");
                        break;
                }
            }
        };

        // Прикрепление слушателя к пунктам меню
        openMenuItem.addActionListener(listener);
        saveMenuItem.addActionListener(listener);
        closeMenuItem.addActionListener(listener);
        cleanTextMenuItem.addActionListener(listener);

        // Добавление пунктов меню в меню "Файл"
        menu.add(saveMenuItem);
        menu.add(openMenuItem);
        menu.add(closeMenuItem);
        menu.add(cleanTextMenuItem);

        frame.setSize(500, 500);  // Установка размеров окна
        frame.setMinimumSize(new Dimension(300, 300));  // Установка минимального размера окна
        frame.setVisible(true);  // Отображение окна
    }

    // Метод для открытия файла и отображения его содержимого в JTextArea
    public static void openFile() {
        JFileChooser fileChooser = new JFileChooser();  // Создание диалога выбора файла
        int returnVal = fileChooser.showOpenDialog(frame);  // Отображение диалога выбора файла
        if (returnVal == JFileChooser.APPROVE_OPTION) {  // Если выбран файл
            File file = fileChooser.getSelectedFile();  // Получение выбранного файла
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));  // Создание объекта для чтения файла
                String line;
                StringBuilder text = new StringBuilder();
                while ((line = reader.readLine()) != null) {  // Чтение файла построчно
                    text.append(line).append("\n");  // Добавление каждой строки в текст
                }
                reader.close();  // Закрытие объекта для чтения файла
                textArea.setText(text.toString());  // Установка содержимого JTextArea
            } catch (IOException ex) {  // Обработка ошибок ввода-вывода файла
                JOptionPane.showMessageDialog(null, "Ошибка: файл не найден!");  // Отображение сообщения об ошибке
            }
        }
    }

    // Метод для сохранения текста из JTextArea в файл
    public static void saveFile() {
        JFileChooser fileChooser = new JFileChooser();  // Создание диалога выбора файла для сохранения
        int returnVal = fileChooser.showSaveDialog(frame);  // Отображение диалога выбора файла для сохранения
        if (returnVal == JFileChooser.APPROVE_OPTION) {  // Если выбран файл для сохранения
            File file = fileChooser.getSelectedFile();  // Получение выбранного файла
            try {
                FileWriter writer = new FileWriter(file);  // Создание объекта для записи в файл
                writer.write(textArea.getText());  // Запись содержимого JTextArea в файл
                writer.close();  // Закрытие объекта для записи в файл
            } catch (IOException ex) {  // Обработка ошибок ввода-вывода файла
                JOptionPane.showMessageDialog(null, "Ошибка: файл не найден!");  // Отображение сообщения об ошибке
            }
        }
    }
}

