package Lab4_5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {


    private ServerSocket serverSocket;
    private Thread thread;

    //private static int clientCount = 0; // счетчик клиентов
    private static final ArrayList<EchoHandler> handlers = new ArrayList<>(); // список обработчиков
    private static final ConcurrentHashMap<String, BlockingQueue<byte[]>> namedQueues = new ConcurrentHashMap<>();



    // метод запуска сервера (а именно сокета сервера, куда должен подключиться клиент через просто сокет через echohandler)
    public Server(int port) throws IOException { 
        serverSocket = new ServerSocket(port); // создание серверсокета
        thread = new Thread(this); // создание потока для серверсокета
        System.out.println("Сервер запущен, порт: " + port);
    }
    
    // метод запуска потока
    public void start() { 
        thread.start();
    }

    // // метод отправки сообщений всем клиентам через обработчик (обходим все обработчики)
    // public static void broadcast(String message) { 
    //     for (EchoHandler handler : handlers) {
    //         handler.sendMessage(message);
    //     }
    // }

    // метод закрытия обработчика (убираем клиента)
    public void closeHandler(EchoHandler handler) { 
        handlers.remove(handler);
        //broadcast(handler.getName() + " покинул чат");
    } 

    @Override
    public void run() { // запускается при запуске нового потока
        while (true) {            
            try {
                Socket socket = serverSocket.accept(); // ждем подключение клиента

                //clientCount++;
                //String clientName = "User" + clientCount; // уникальное имя клиента
                
                EchoHandler echoHandler = new EchoHandler(); // создаем новый обработчик для клиента
                echoHandler.init(this, socket, namedQueues); // инициализация обработчика (новый клиент)
                handlers.add(echoHandler); // добавляем новый обработчик для нового клиента
                System.out.println("Новый пользователь");

                //broadcast(clientName + " подключился к чату"); // оповещение для всех о новом пользователе

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) { // запуск сервера + потока
        try {
            Server server = new Server(6666); // создаем сервер для подключения
            server.start(); // запускаем поток -> запускается run()
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }  
}
