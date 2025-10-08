
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class EchoHandler implements Runnable {

    private Thread thread;
    private Server server;
    private Socket socket;
    private String name;
    private BufferedReader in;
    private BufferedWriter out;

    // метод получения имени обрабатываемого клиента
    public String getName(){ 
        return name;
    }

    // метод отправки сообщения клиенту
    public synchronized void sendMessage(String message) { 
        try {
            out.write(message); // отправка сообщения
            out.newLine(); // переходим на новую строку
            out.flush(); // принудительная отправка данных по сети
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод отправки списка активных пользователей подключившемуся клиенту
    private void sendActiveUsers() {
        List<String> users = Server.getActiveUserNames();
        sendMessage("Пользователи в чате:");
        for (String user : users) {
            sendMessage(" - " + user);
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

        sendActiveUsers(); // отправляем список активных пользователей

        sendMessage("Добро пожаловать, " + name + "!"); // отправляется приветствие клиенту!
    }


    @Override
    public void run() { // запускается при запуске нового потока
        String message;
        try {
            System.out.println("Server: Запустился обработчик для клиента " + name);

            //in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); // инициализация - поток ввода
            //out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")); // инициализация - поток вывода

            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + name + ": " + message); // вывод на сервере
                Server.broadcast(name + ": " + message); // рассылка всем
            }
            
            /*while (true) {
                String str = in.readLine();
                System.out.println("read: " + str); //выводим то, что прочли
                out.write(str);
                out.newLine();
                out.flush();
            }*/
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


}
