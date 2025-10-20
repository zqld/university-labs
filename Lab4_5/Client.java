package Lab4_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Client {


    private OutputStream outputStream; // Поток для записи данных в сокет
    private Scanner scanner; // Объект Scanner для считывания пользовательского ввода
    private String bufferName; // Переменная для хранения имени текущей очереди сообщений

    public void main(String[] args) {
        scanner = new Scanner(System.in); // Инициализируем Scanner для считывания ввода с клавиатуры
        try {
            Socket socket = new Socket("localhost", 6666); // подключаемся к серверу по сокету
            System.out.println("Server: Подключено к серверу.");
            
            
            
            outputStream = socket.getOutputStream(); // Получаем поток вывода для отправки данных на сервер

            // поток для считывания сообщений
            new Thread(() -> {
                try {
                    
                    while (true) { // Бесконечный цикл для ввода команд
                        String command = scanner.nextLine().trim(); // Считываем введенную команду

                        if (command.startsWith("send")) { // Если команда начинается с "send"
                            String[] lenSend = command.split(" ", 2); // Разделяем команду на части, чтобы получить имя очереди
                            bufferName = lenSend[1]; // Сохраняем имя очереди
                        } else {
                            if (command.startsWith("receive")) { // Если команда начинается с "receive"
                                bufferName = null; // Сбрасываем имя очереди
                            }
                        }
                        outputStream.write((command + "\n").getBytes(StandardCharsets.UTF_8)); // Отправляем команду на сервер
                        outputStream.flush(); // Очищаем буфер вывода
                    }


                } catch (Exception e) {
                    // TODO: handle exception
                }
            });










            
            // поток для чтения сообщений от сервера
            new Thread(() -> { 
                //BufferedReader in = null;
                try {
                    //System.out.println("Server: Ждём сообщения");
                    BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream(), "UTF-8"));  // получаем данные с потока
                    String message;
                    while ((message = in.readLine()) != null) { // условие пока есть сообщениев в потоке
                        System.out.println(message); // выводим входящие сообщения клиенту
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Соединение с сервером потеряно.");
                }
            }).start();



            

            // Поток для отправки сообщений на сервер
            Scanner scanner = new Scanner(System.in, "UTF-8"); //  объект чтения строк с консоли
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            while (true) {                
                String msg = scanner.nextLine(); // считываем текст с клавы
                out.write(msg); // отправка текста в поток
                out.newLine();
                out.flush(); //принудительная отправка данных по сети
            }
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
