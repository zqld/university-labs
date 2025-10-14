import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

//Задание2
/*В последнем двупоточном решении читатель постоянно создает 
новые byte[]-буфера, передает их писателю, а тот отправляет на съедение GC. 
Создайте отдельную обратную очередь пустых буферов от писателя к читателю. */


public class CopyUtil3 {
    private static final int poolSize = 16; // кол-во буферов (фикса)

    public static void copy(final InputStream src, final OutputStream dst) throws IOException {
        // reader-to-writer byte[]-channel 
        final BlockingQueue<byte[]> buffer = new ArrayBlockingQueue<>(16); // очередь заполненных буферов (в пути к писателю) (эл-т byte[] длиной 128)
        final BlockingQueue<byte[]> emptyBuffer = new ArrayBlockingQueue<>(poolSize); // фиксированные буферы, которые будут использоваться
        
        for (int i = 0; i < poolSize; i++) { // ограничение по кол-ву буферов
            emptyBuffer.add(new byte[128]); // добавляет элемент в очередь, если она не заполнена
        }

        // exception-channel from reader/writer threads?
        // Канал для передачи необработанного исключения из ThreadGroup.uncaughtException в поток, который вызовет copy (главный поток), чтобы в конце бросить IOException с причиной.
        final AtomicReference<Throwable> ex = new AtomicReference<>(); // атомарные операции для ссылок на объекты (вместо synchronized)

        final ThreadGroup group = new ThreadGroup("read-write") { // управление потоками как единым целым
            public void uncaughtException(Thread t, Throwable e) {ex.set(e);} // ловим исплючение, которое может "выпрыгнуть" (кроме тех, которые пойманы нормальным путем)
        };

        // reader from 'src' - поток для чтения
        Thread reader = new Thread(group, () -> {
            try (InputStream src0 = src) {              // 'src0' for auto-closing (try with resouerces)
                while (true) {
                    byte[] data = emptyBuffer.take(); //дождёмся свободного буфера из пула (блокирует, если все буферы в использовании)

                    int count = src0.read(data, 1, 127); // читаем в data с 1 по 127 байт
                    data[0] = (byte) count; // длина в нулевой байт

                    buffer.put(data); // отправляем data даже если -1, чтобы writer прекратил существование
                    if (count == -1) { // проверка на конец потока
                        //emptyBuffer.put(data);// возврат использованного data в пул
                        break;
                    }
                }
            } catch (Exception e) {group.interrupt();}  // interrupt writer (прерываем всю группу, исключение не заносится в ex)
        });
        reader.start();

        // writer to 'dst' - поток для записи
        Thread writer = new Thread(group, () -> {
            try (OutputStream dst0 = dst) {      // 'dst0' for auto-closing (try with resouerces)
                while (true) {
                    byte[] data = buffer.take();// берем заполненный буфер из reader (блокирует, если пусто)
                    // проверка конца потока
                    if (data[0] == (byte) -1) {break;} //  конец потока — вышли
                    dst.write(data, 1, data[0]); // записываем из data в поток данные с 1 по len (длина хранится в data0) 
                    emptyBuffer.put(data); // возвращаем буфер в пул
                }
            } catch (Exception e) {ex.set(e); group.interrupt();}  // interrupt writer (прерываем всю группу, исключение не заносится в ex)
        });
        writer.start();
        // wait to complete read/write operations (главный поток ждёт завершения)
        try {
            reader.join(); // wait for reader
            writer.join(); // wait for writer
        } catch (InterruptedException e) {throw new IOException(e);}
        if (ex.get() != null) {throw new IOException(ex.get());} // обработка ошибки из ex
    }
}