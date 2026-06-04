import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666); // подключаемся к серверу по сокету
            System.out.println("Server: Подключено к серверу.");
            
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
