import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
/*Это реализация многопоточного копировальщика массивами из InputStream в OutputStream. 
Мы заводим на чтение и на запись по отдельному новому потоку и 
соединяем их блокирующей ограниченной очередью для передачи данных от читателя к писателю */


public class CopyUtil3 {
    public static void copy(final InputStream src, final OutputStream dst) throws IOException {
        // reader-to-writer byte[]-channel 
        final BlockingQueue<byte[]> buffer = new ArrayBlockingQueue<>(64); // буфер в виде блок очереди длиной 64 эл-та (эл-т byte[] длиной 128)
        // exception-channel from reader/writer threads?
        // Канал для передачи необработанного исключения из ThreadGroup.uncaughtException в поток, который вызовет copy (главный поток), чтобы в конце бросить IOException с причиной.
        final AtomicReference<Throwable> ex = new AtomicReference<>(); // атомарные операции для ссылок на объекты (вместо synchronized)
        // final ThreadGroup group = new ThreadGroup("read-write") { // управление потоками как единым целым
        //     public void uncaughtException(Thread t, Throwable e) {ex.set(e);} // ловим исплючение, которое может "выпрыгнуть" (кроме тех, которые пойманы нормальным путем)
        // };
        // reader from 'src' - поток для чтения
        Thread reader = new Thread(() -> { //group
            try (InputStream src0 = src) {              // 'src0' for auto-closing (try with resouerces)
                while (true) {
                    byte[] data = new byte[128];        // new data buffer
                    int count = src.read(data, 1, 127); // read up to 127 bytes (оставляет data[0] для метаданных)
                    data[0] = (byte) count;             // 0-byte is length-field (помещаем в 0 байт длину)
                    buffer.put(data);                   // send to writer (помещается в очередь - если полная очередь, ждет появления места - поток чтения ждет)
                    if (count == -1) {break;}           // src empty
                }
            } catch (Exception e) {                     //group.interrupt(); interrupt writer (прерываем всю группу, исключение не заносится в ex)
                try {
                    // если ошибка — кладём маркер конца (-1)
                    byte[] end = new byte[1];
                    end[0] = -1;
                    buffer.put(end);
                } catch (InterruptedException ignored) {}
            }
        });
        reader.start();
        // writer to 'dst' - поток для записи
        // Thread writer = new Thread(group, () -> {
        try (OutputStream dst0 = dst) {      // 'dst0' for auto-closing (try with resouerces)
            while (true) {
                byte[] data = buffer.take(); // get new data from reader (ждет, пока очередь пуста, пока не появятся новые данные)
                if (data[0] == -1) {break;}  // its last data (прерывание на последнюю дату)
                dst.write(data, 1, data[0]); // записывает data[0] байт начиная с позиции 1
            }
        } catch (Exception e) {ex.set(e);}  // group.interrupt(); interrupt writer (прерываем всю группу, исключение не заносится в ex)
        // });
        // writer.start();
        // // wait to complete read/write operations (главный поток ждёт завершения)
        // try {
        //     reader.join(); // wait for reader
        //     writer.join(); // wait for writer
        // } catch (InterruptedException e) {throw new IOException(e);}
        if (ex.get() != null) {throw new IOException(ex.get());} // обработка ошибки из ex
    }
}