package Lab4_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;


public class Client {


    private OutputStream outputStream; // Поток для записи данных в сокет
    private Scanner scanner; // Объект Scanner для считывания пользовательского ввода
    private String bufferName; // Переменная для хранения имени текущей очереди сообщений

    public void main(String[] args) throws IOException {

        // if (args.length < 2){
        //     System.out.println("Должно быть 2 аргумента");
        //     return; 
        // }
        Scanner scanner = new Scanner(System.in, "UTF-8"); //  объект чтения строк с консоли
        String message = scanner.nextLine();

        String mode = message.split(" ")[0];
        String queue = message.split(" ")[1];

        Socket socket = new Socket("localhost", 6666); // подключаемся к серверу по сокету
        BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream(), "UTF-8"));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        
        //Scanner scanner = new Scanner(System.in, "UTF-8"); //  объект чтения строк с консоли
        //String msg = scanner.nextLine(); // считываем текст с клавы
        out.write(mode + " " + queue); // отправка текста в поток
        out.newLine();
        out.flush(); //принудительная отправка данных по сети

        if ("send".equalsIgnoreCase(mode)) {
            //System.out.println("helo");
            //new Thread(() -> {
            try {
                send_message(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //});
        } else if("receive".equalsIgnoreCase(mode)){
            //new Thread(() -> {
            try {
                receive_message(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //});
        }

        //socket.close();



        // scanner = new Scanner(System.in); // Инициализируем Scanner для считывания ввода с клавиатуры
        // try {
        //     Socket socket = new Socket("localhost", 6666); // подключаемся к серверу по сокету
        //     System.out.println("Server: Подключено к серверу.");
            
            
            
        //     outputStream = socket.getOutputStream(); // Получаем поток вывода для отправки данных на сервер

        //     // поток для считывания сообщений
        //     new Thread(() -> {
        //         try {
                    
        //             while (true) { // Бесконечный цикл для ввода команд
        //                 String command = scanner.nextLine().trim(); // Считываем введенную команду

        //                 if (command.startsWith("send")) { // Если команда начинается с "send"
        //                     String[] lenSend = command.split(" ", 2); // Разделяем команду на части, чтобы получить имя очереди
        //                     bufferName = lenSend[1]; // Сохраняем имя очереди
        //                 } else {
        //                     if (command.startsWith("receive")) { // Если команда начинается с "receive"
        //                         bufferName = null; // Сбрасываем имя очереди
        //                     }
        //                 }
        //                 outputStream.write((command + "\n").getBytes(StandardCharsets.UTF_8)); // Отправляем команду на сервер
        //                 outputStream.flush(); // Очищаем буфер вывода
        //             }


        //         } catch (Exception e) {
        //             // TODO: handle exception
        //         }
        //     });










            
        //     // поток для чтения сообщений от сервера
        //     new Thread(() -> { 
        //         //BufferedReader in = null;
        //         try {
        //             //System.out.println("Server: Ждём сообщения");
        //             BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream(), "UTF-8"));  // получаем данные с потока
        //             String message;
        //             while ((message = in.readLine()) != null) { // условие пока есть сообщениев в потоке
        //                 System.out.println(message); // выводим входящие сообщения клиенту
        //             }
        //         } catch (IOException ex) {
        //             ex.printStackTrace();
        //             System.out.println("Соединение с сервером потеряно.");
        //         }
        //     }).start();



            

        //     // Поток для отправки сообщений на сервер
        //     Scanner scanner = new Scanner(System.in, "UTF-8"); //  объект чтения строк с консоли
        //     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

        //     while (true) {                
        //         String msg = scanner.nextLine(); // считываем текст с клавы
        //         out.write(msg); // отправка текста в поток
        //         out.newLine();
        //         out.flush(); //принудительная отправка данных по сети
        //     }
           
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        // }
        
    }


    private static void send_message(BufferedWriter out) throws IOException{
        //Random random = new Random();
        for(int i = 0; i < 100; i++){
            String msg = "Hello " + i;
            out.write("message " + msg.length()); // отправка текста в поток
            out.newLine();
            //System.out.println("Клиент отправил сообщение: " + msg);
            out.write(msg);
            out.newLine();
            out.flush(); //принудительная отправка данных по сети
        }
        System.out.println("success");
    }


    private static void receive_message(BufferedReader in) throws IOException{
        System.out.println("начало receive");
        String line;
        while((line = in.readLine()) != null){
            int len = Integer.parseInt(line.split(" ")[1]);
            char[] buffer = new char[len];
            in.read(buffer, 0, len);
            System.out.println(new String(buffer));
        }
        System.out.println("конец receive");
        //while(true);
    }



}
