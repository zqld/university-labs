package com.mycompany.calculatorappnew;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class CalculatorActionListener implements ActionListener {

    private final JTextField textField;

    public CalculatorActionListener(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       // Получение команды, связанной с событием
       String command = e.getActionCommand();
       // Получение текущего текста в текстовом поле
       String currentText = textField.getText();
       
       // Обработка различных команд и кнопок
       if (command.length() == 1 && Character.isDigit(command.charAt(0)) || command.equals(".")) {
            // Если команда - цифра или точка, добавляем ее к текущему тексту
            textField.setText(currentText + command);
       } else {
           if (command.equals("C")) {
               // Если команда - "C", очищаем текстовое поле
               textField.setText(" ");
           } else {
               if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/") || command.equals("^")) {
                   // Если команда - оператор, добавляем его к текущему тексту
                   textField.setText(currentText + command);
               } else {
                   if (command.equals("sin(x)")) {
                      // Если команда - "sin(x)", добавляем "sin()" к текущему тексту
                      textField.setText("sin(" + currentText + ")");
                   }
                   if (command.equals("cos(x)")) {
                      // Если команда - "cos(x)", добавляем "cos()" к текущему тексту
                      textField.setText("cos(" + currentText + ")");
                   }
                   if (command.equals("√")) {
                      // Если команда - "√", добавляем "√" к текущему тексту
                      textField.setText("√" + currentText);
                   }
                    
                   if (command.equals("=")) {
                        // Если команда - "=", вычисляем результат выражения
                        try {
                            double result = evaluateExpression(currentText);
                            textField.setText(Double.toString(result));
                        } catch (ArithmeticException ex) {
                            textField.setText("Error: " + ex.getMessage());
                        }
                   }
               }
           }
       }
    }

    // Метод для вычисления математического выражения
    public static double evaluateExpression(String currentText) {
        double counter1; 
        double counter2; 
        double number1 = 0; 
        double number2 = 0; 
        double result = 0;
        StringBuilder floatBuffer = new StringBuilder();
        
        // Итерация по символам в текущем тексте
        for (char c : currentText.toCharArray()) {
            if (Character.isDigit(c) || (c == '.')) {
                floatBuffer.append(c); 
            } else if (floatBuffer.length() > 0) { 
                float floatValue = Float.parseFloat(floatBuffer.toString()); 
                counter1 = + floatValue; 
                number1 = counter1 + number1; 
                floatBuffer.setLength(0);
            }
        }
        
        if (floatBuffer.length() > 0) {
            float floatValue1 = Float.parseFloat(floatBuffer.toString());
            counter2 = + floatValue1;
            number2 = counter2 + number2;
        }
            
        String operation = operationDefinition(currentText);
        result = roundToTwoDecimalPlaces(performOperation(number1, number2, operation));
        return result;
    }
    
    // Метод для округления до двух десятичных знаков
    public static double roundToTwoDecimalPlaces(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
    
    // Метод для определения операции
    public static String operationDefinition(String currentText) {
        if (currentText.contains("+")) {
            return "+";
        } else if (currentText.contains("-")) {
            return "-";
        } else if (currentText.contains("*")) {
            return "*";
        } else if (currentText.contains("/")) {
            return "/";
        } else {
            if (currentText.contains("cos")) {
                return "cos";
            } else {
                if (currentText.contains("sin")) {
                    return "sin";
                } else {
                    if (currentText.contains("^")) {
                        return "^";
                    } else {
                        if (currentText.contains("√")) {
                            return "√";
                        } else {
                            return "";
                        }
                    }
                }
            }
        }
    }
    
    // Метод для выполнения операции
    private static double performOperation(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Деление на ноль!");
                    return num1;
                }
            case "sin":
                return Math.sin(Math.toRadians(num1));
            case "cos":
                return Math.cos(Math.toRadians(num1));
            case "^":
                return Math.pow(num1, num2);
            case "√":
                return Math.sqrt(num2);
            default:
                return num2;
        }
    }
}
