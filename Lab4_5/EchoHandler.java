package Lab4_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class EchoHandler implements Runnable {

    private Thread thread;
    private Server server;
    private Socket socket;
    private String name;
    private BufferedReader in;
    private BufferedWriter out;






    
    ConcurrentHashMap<String, BlockingQueue<String>> namedQueues = new ConcurrentHashMap<>();


    public void createBuffer(String nameBuffer){
        final BlockingQueue<String> buffer = new ArrayBlockingQueue<>(1000); // очередь буферов

        namedQueues.put(nameBuffer, buffer);

    }























    // запускается при запуске нового потока
    @Override
    public void run() { 
        String message;
        try {
            System.out.println("Server: Запустился обработчик для клиента " + name);

            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + name + ": " + message); // вывод на сервере
                Server.broadcast(name + ": " + message); // рассылка всем
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Server: " + name + " отключился");
        } finally {
            try {
                socket.close(); // закрываем сокет для экономии ресурсов!
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.closeHandler(this); // удаляем клиента из списка при выходе клиента
        }
    }


        // метод получения имени обрабатываемого клиента
    public String getName(){ 
        return name;
    }

    // метод орбычного отправки сообщения
    public synchronized void sendMessage(String message) { 
        try {
            out.write(message); // отправка сообщения
            out.newLine(); // переходим на новую строку
            out.flush(); // принудительная отправка данных по сети
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    // конструктор для инициализации
    public void init(Server server, Socket socket, String clientName) { 
        this.server = server;
        this.socket = socket;
        this.name = clientName;

        thread = new Thread(this);
        thread.start(); // запускает новый поток -> запускает код run()

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); // инициализация - поток ввода
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")); // инициализация - поток вывод
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendMessage("Добро пожаловать, " + name + "!"); // отправляется приветствие клиенту!
    }





}
