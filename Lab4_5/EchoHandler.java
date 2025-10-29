package Lab4_5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.DocFlavor.STRING;

public class EchoHandler implements Runnable {

    private Thread thread;
    private Server server;
    private Socket socket;
    private BufferedReader reader;

    private ConcurrentHashMap<String, BlockingQueue<byte[]>> namedQueues;

    // конструктор для инициализации
    public void init(Server server, Socket socket, ConcurrentHashMap<String, BlockingQueue<byte[]>> namedQueues) { 
        this.server = server;
        this.socket = socket;
        this.namedQueues = namedQueues;
        //this.name = clientName;

        thread = new Thread(this);
        thread.start(); // запускает новый поток -> запускает код run() (новый поток для хендлера)

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); // инициализация - поток ввода
            //out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")); // инициализация - поток вывод
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sendMessage("Добро пожаловать, " + name + "!"); // отправляется приветствие клиенту!
    }


    // запускается при запуске нового потока (код для серверной части обраьотки сообщений)
    @Override
    public void run() { 
        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); // инициализация - поток ввода
            OutputStream out = socket.getOutputStream();

            String userMessage = reader.readLine();
            if (userMessage == null) {System.out.println("Нет сообщения"); return;}
            
            String messageParts[] = userMessage.split(" ");
            //проверка правильности команды
            if(messageParts.length != 2) {
                System.out.println("Неверная команда (partslength!=2)");
                return;
            }
            
            String mode = messageParts[0];
            String queueName = messageParts[1];

            namedQueues.putIfAbsent(queueName, new ArrayBlockingQueue<>(1000)); // проверка на наличие очереди
            BlockingQueue<byte[]> queue = namedQueues.get(queueName);
            if (queue != null) {
                System.out.println("Очередь создана и не пуста.");}
                else{
                    System.out.println("Очередь не создана");
                }


            System.out.println("Server: Запустился обработчик для клиента очереди " + queueName);
            System.out.println(mode+ ""+ queueName);
            if( mode.contains("send")) {
                System.out.println(queue.isEmpty());// test
                handleSender(reader, queue);
                System.out.println(queue.isEmpty());// test
            } else if ( mode.contains("receive")) {
                handleReceiver(queue, out);
            } else {
                System.out.println("Неизвестная команда, попробуйте еще раз");
            }


        } catch (IOException e){
            e.printStackTrace();
        } 
        

        // String message;
        // try {
        //     System.out.println("Server: Запустился обработчик для клиента " + name);

        //     while ((message = in.readLine()) != null) {
        //         System.out.println("Server: " + name + ": " + message); // вывод на сервере
        //         Server.broadcast(name + ": " + message); // рассылка всем
        //     }
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        //     System.out.println("Server: " + name + " отключился");
        // } finally {
        //     try {
        //         socket.close(); // закрываем сокет для экономии ресурсов!
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        //     server.closeHandler(this); // удаляем клиента из списка при выходе клиента
    }

    // клиент - sender отправитель ГОТОВО
    private void handleSender(BufferedReader reader, BlockingQueue<byte[]> queue) throws IOException{
        String message;
        //System.out.println(reader.readLine());
        while((message = reader.readLine()) != null){
            System.out.println("отправка");
            if(!(message.startsWith("message "))) continue; // проверка правильности команды отправки сообщения
            //System.out.println("test1"); //SASDASD

            String[] messageParts = message.split(" ");// разделение сообщения на команду и вторую часть с длиной сообщения и самим сообщением
            int messageLength = Integer.parseInt(messageParts[1]);
            //System.out.println(messageLength); //ASDASDASD

            char[] buffer = new char[messageLength]; // само сообщение, которое мы хотим считать
            int read = reader.read(buffer, 0, messageLength); // длина отправленных сообщений buffer - куда мы записываем сообщение
            if(!(read == messageLength)) continue; // проверка, все ли сообщение мы считали
            
            byte[] messageByte = new String(buffer).getBytes();
            try {
                queue.put(messageByte);
            } catch (InterruptedException e) { // при отключении отключаем и поток
                //e.printStackTrace();
                server.closeHandler(this); // удаляем клиента из списка при ошибке (то есть при отключении)
                System.out.println("Клиент очереди " + namedQueues.get(queue) + " отключился");
                break;
            }

        }
   }


   // клиент - receive получатель ГОТОВО
   private void handleReceiver(BlockingQueue<byte[]> queue, OutputStream out) throws IOException{
    int t = 1;
    try {
        System.out.println("asd");
        //int t = 1;
        while (true) {
            byte[] message = queue.take();
            String header = "message " + message.length + "\n";
            out.write(header.getBytes()); // отправка сообщения в поток клиенту
            out.write(message); // отправка сообщения в поток клиенту
            out.flush(); // принудительная отправка данных по сети (очишение потока)
            t++;
        }


    } catch (IOException | InterruptedException e) { // при отключении отключаем и поток
        //e.printStackTrace();
        server.closeHandler(this); // удаляем клиента из списка при ошибке (то есть при отключении)
        System.out.println(e);
        System.out.println("Клиент очереди " + namedQueues.get(queue) + " отключился");
    }
    System.out.println(t);
   }








    
    //ConcurrentHashMap<String, BlockingQueue<String>> namedQueues = new ConcurrentHashMap<>();


    // public void createBuffer(String nameBuffer){
    //     final BlockingQueue<String> buffer = new ArrayBlockingQueue<>(1000); // очередь буферов

    //     namedQueues.put(nameBuffer, buffer);

    // }


























    //     // метод получения имени обрабатываемого клиента
    // public String getName(){ 
    //     return name;
    // }

    // private BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")); // инициализация - поток вывод


    // // метод орбычного отправки сообщения
    // public synchronized void sendMessage(String message) { 
    //     try {
    //         out.write(message); // отправка сообщения
    //         out.newLine(); // переходим на новую строку
    //         out.flush(); // принудительная отправка данных по сети
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    







}
