package com.mycompany.texteditornew;

import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.util.List;

public class SpellCheckThread implements Runnable {
    private final JTextArea textArea;

    public SpellCheckThread(JTextArea textArea) {
        this.textArea = textArea;
    }
//package com.mycompany.texteditornew; - Это объявление пакета, в котором находится ваш класс.
// Он используется для организации классов в иерархии пакетов.
//
//import org.languagetool.JLanguageTool; - Импорт класса JLanguageTool из библиотеки LanguageTool.
// JLanguageTool используется для проверки орфографии и грамматики текста.
//
//import org.languagetool.language.Russian; - Импорт класса Russian из той же библиотеки.
// Russian указывает на язык, на котором будет производиться проверка орфографии и грамматики.
//
//import org.languagetool.rules.RuleMatch; - Импорт класса RuleMatch из той же библиотеки.
// RuleMatch представляет собой результат проверки орфографии и грамматики, включая
// информацию о найденных ошибках и предложенных исправлениях.
//
//import javax.swing.JTextArea; - Импорт класса JTextArea из библиотеки Swing.
// JTextArea - это компонент GUI, используемый для редактирования текста.
//
//import javax.swing.SwingUtilities; - Импорт класса SwingUtilities из той же библиотеки.
// SwingUtilities предоставляет удобные методы для взаимодействия с графическим интерфейсом.
//
//import java.io.IOException; - Импорт класса IOException из библиотеки java.io.
// IOException используется для обработки исключений, связанных с вводом-выводом.
//
//import java.util.List; - Импорт класса List из стандартной библиотеки Java.
// List используется для хранения списка предложенных исправлений.
//
//public class SpellCheckThread implements Runnable { - Объявление класса SpellCheckThread,
// который реализует интерфейс Runnable. Этот класс предназначен для выполнения проверки орфографии и
// автоматической замены слов в тексте.
//
//private final JTextArea textArea; - Объявление приватного поля textArea,
// которое будет содержать ссылку на JTextArea, с которым будет работать поток.
//
//public SpellCheckThread(JTextArea textArea) { ... } - Конструктор класса, который принимает
// JTextArea в качестве параметра и сохраняет его в поле textArea.

    @Override
    public void run() {
        //public void run() { ... } - Реализация метода run(), который будет выполнен при запуске потока.
        JLanguageTool languageTool = new JLanguageTool(new Russian());
        //Создание объекта languageTool типа JLanguageTool с указанием русского языка (Russian)
        // для проверки орфографии и грамматики текста.

        while (true) {
            //Начало бесконечного цикла. Проверка будет выполняться постоянно.
            try {
                Thread.sleep(9000); // Проверка каждую секунду
                //Попытка приостановки выполнения потока на 9 секунд,
                // чтобы уменьшить нагрузку на процессор при постоянной проверке.
                String text = textArea.getText();
                //Получение текста из JTextArea для последующей проверки.

                List<RuleMatch> matches = languageTool.check(text);
                //Запуск проверки орфографии и грамматики текста с использованием languageTool.
                // Результат проверки сохраняется в списке matches,
                // который содержит информацию о найденных ошибках и предложенных исправлениях.

                for (RuleMatch match : matches) {
                    //Начало цикла, который перебирает все найденные ошибки и предложенные исправления.
                    List<String> suggestedReplacements = match.getSuggestedReplacements();
                    //Получение списка предложенных исправлений для текущей ошибки.
                    if (!suggestedReplacements.isEmpty()) {
                        //Проверка, есть ли предложенные исправления для данной ошибки.
                        String suggested = suggestedReplacements.get(0);
                        //Если есть предложенные исправления, то выбирается первое из них.
                        text = text.substring(0, match.getFromPos()) + suggested + text.substring(match.getToPos());
                        //Выполняется автоматическая замена неправильного слова на предложенное исправление в тексте.
                    }
                }
                //Завершение цикла обработки ошибок и исправлений.

                final String updatedText = text;
                //Создается финальная копия обновленного текста для использования в графическом потоке.

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.setText(updatedText);
                    }
                });
                //Запускается графический поток, который обновляет JTextArea с новым текстом после автозамены.

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Завершение цикла потока и обработка исключений, которые могут возникнуть при приостановке
        // выполнения потока (InterruptedException) и при работе с languageTool (IOException).
        //Этот класс выполняет проверку орфографии и автоматическую замену слов в тексте, а затем обновляет
        // JTextArea с исправленным текстом в графическом потоке.
    }
}
