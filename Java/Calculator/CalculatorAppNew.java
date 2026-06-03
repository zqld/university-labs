package com.mycompany.calculatorappnew;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorAppNew {
    public static void main(String[] args) {
        // Создание основного окна приложения
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание текстового поля для вывода ввода и результата
        JTextField textField = new JTextField();
        textField.setForeground(Color.WHITE); // Установка цвета текста
        Font font = new Font("Arial", Font.BOLD, 49); // Установка шрифта текстового поля
        textField.setFont(font);
        textField.setHorizontalAlignment(JTextField.RIGHT); // Выравнивание текста по правому краю
        textField.setPreferredSize(new Dimension(300, 50)); // Установка размера текстового поля
        textField.setBackground(Color.LIGHT_GRAY); // Установка цвета фона

        // Массив меток для кнопок калькулятора
        String[] buttonLabels = {
            "1", "2", "3", "/",
            "4", "5", "6", "*",
            "7", "8", "9", "-",
            "0", ".", "√", "+",
            "sin(x)", "cos(x)",
            "^", "C",
        };

        // Создание панели для размещения кнопок
        JPanel panel = new JPanel(new GridLayout(0, 4, 6, 6)); // Создание сетки 4x4 для кнопок с отступами
        panel.setBackground(Color.lightGray); // Установка цвета фона панели

        for (String label : buttonLabels) {
            // Создание кнопки с меткой
            JButton button = new JButton(label);
            button.setPreferredSize(new Dimension(40, 40)); // Установка размера кнопки
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Установка черной рамки
            button.setBackground(Color.white); // Установка белого фона
            button.setOpaque(true); // Разрешение рисования фона кнопки
            button.setFocusPainted(false); // Отключение подсветки при фокусировке
            button.setFont(new Font("Arial", Font.PLAIN, 30)); // Установка шрифта кнопки
            button.setBorderPainted(false); // Отключение отрисовки рамки
            button.addActionListener(new CalculatorActionListener(textField)); // Добавление слушателя событий
            panel.add(button); // Добавление кнопки на панель
        }

        // Создание кнопки "="
        JPanel panelEqually = new JPanel(new GridLayout(1, 1));
        JButton equalsButton = new JButton("=");
        equalsButton.setPreferredSize(new Dimension(1000, 50)); // Установка размера кнопки "="
        equalsButton.setBackground(Color.RED); // Установка красного фона
        Font fontEqually = new Font("Arial", Font.BOLD, 49); // Установка шрифта
        equalsButton.setFont(fontEqually);
        equalsButton.setForeground(Color.WHITE); // Установка цвета текста
        panelEqually.add(equalsButton);
        equalsButton.addActionListener(new CalculatorActionListener(textField)); // Добавление слушателя для кнопки "="

        // Добавление панели с кнопкой "=" в нижнюю часть окна
        frame.add(panelEqually, BorderLayout.SOUTH);
        frame.add(panelEqually, BorderLayout.SOUTH);
        //frame.add(panelEqually, BorderLayout.SOUTH);

        // Добавление панели с кнопками в верхнюю часть окна
        frame.add(panel);

        frame.pack(); // Автоматическое изменение размера окна, чтобы вместить содержимое

        frame.add(textField, BorderLayout.NORTH); // Добавление текстового поля в верхнюю часть окна
        frame.setMinimumSize(new Dimension(460, 570)); // Установка минимального размера окна
        frame.setSize(461, 571); // Установка начального размера окна
        frame.setVisible(true); // Отображение окна
    }
}

