package com.mycompany.layoutmanagerdemo;

import javax.swing.*;
import java.awt.*;

public class LayoutManagerDemo {
    public static void main(String[] args){
        // Создаем новое главное окно (JFrame) с заголовком "Демонстрация менеджеров компоновки"
        JFrame frame = new JFrame("Демонстрация менеджеров компоновки");
        // Устанавливаем операцию закрытия окна по умолчанию (при закрытии окна программа завершится)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем JPanel с FlowLayout для верхней части окна
        JPanel flowLayoutPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        // Создаем две кнопки
        JButton buttonNumber1 = new JButton("Кнопка 1");
        JButton buttonNumber2 = new JButton("Кнопка 2");
        // Устанавливаем для панели FlowLayout и добавляем в нее обе кнопки
        flowLayoutPanel.setLayout(flowLayout);
        flowLayoutPanel.add(buttonNumber1);
        flowLayoutPanel.add(buttonNumber2);

        // Создаем JPanel с BorderLayout для остальной части окна
        JPanel borderLayoutPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        // Создаем пять кнопок для различных областей окна
        JButton buttonNORTH = new JButton("Север");
        JButton buttonSOUTH = new JButton("Юг");
        JButton buttonWEST = new JButton("Запад");
        JButton buttonEAST = new JButton("Восток");
        JButton buttonCENTER = new JButton("Центр");
        // Устанавливаем для панели BorderLayout и добавляем в нее кнопки с указанием областей
        borderLayoutPanel.setLayout(borderLayout);
        borderLayoutPanel.add(buttonNORTH, BorderLayout.NORTH);
        borderLayoutPanel.add(buttonSOUTH, BorderLayout.SOUTH);
        borderLayoutPanel.add(buttonWEST, BorderLayout.WEST);
        borderLayoutPanel.add(buttonEAST, BorderLayout.EAST);
        borderLayoutPanel.add(buttonCENTER, BorderLayout.CENTER);

        // Добавляем панель с BorderLayout в центр главного окна
        frame.add(borderLayoutPanel, BorderLayout.CENTER);
        // Добавляем панель с FlowLayout в верхнюю часть окна
        frame.add(flowLayoutPanel, BorderLayout.NORTH);

        // Устанавливаем размер главного окна (500x500)
        frame.setSize(500, 500);
        // Устанавливаем минимальный размер главного окна (300x300)
        frame.setMinimumSize(new Dimension(300, 300));
        // Делаем окно видимым
        frame.setVisible(true);
    }
}
