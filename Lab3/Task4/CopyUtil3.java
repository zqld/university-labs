import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/*Задание #4
Сделайте предыдущее задание #3 но образуйте не топологию 'звезда', 
где в центре читатель и от него исходят лучи к писателям, а топологию 'кольцо'. 
В которой читатель и писатели выстраиваются в круг и передают буфер по кругу. 
Читатель — первому писателю, первый писатель — второму,… последний писатель — читателю. 
И после чего читатель может использовать буфер повторно. */


public class CopyUtil3 {

    public static void copy(final InputStream src, final OutputStream... dst) throws IOException {

        final int capacity = 16; // длина очереди
        final int poolSize = 16; // заранее готовые буферы
        final int writers_count = dst.length; // длина писателей

        // reader-to-writer byte[]-channel 
        final BlockingQueue<byte[]>[] buffers = new ArrayBlockingQueue[writers_count+1]; // массив очередей (+1 для читателя)
        //final BlockingQueue<byte[]> emptyBuffer = new ArrayBlockingQueue<>(poolSize); // фиксированные буферы, которые будут использоваться

        // выделение места для очередей
        for (int i = 0; i < writers_count+1; i++) {
            buffers[i] = new ArrayBlockingQueue<>(capacity); // буфер в виде блокир. очереди длиной 64 эл-та
        }
        // выделение места для данных (заранее)
        for (int i = 0; i < poolSize; i++) { // ограничение по кол-ву буферов
            buffers[writers_count].add(new byte[128]); // выделение места в последней очереди (кол-во poolsize)
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
                    byte[] data = buffers[writers_count].take(); //берем выделенное место из последней очереди

                    int count = src0.read(data, 1, 127); // читаем в data с 1 по 127 байт
                    data[0] = (byte) count; // длина в нулевой байт

                    buffers[0].put(data); // отправляем data даже если -1, чтобы writer прекратил существование
                    if (count == -1) { // проверка на конец потока
                        break;
                    }
                }
            } catch (Exception e) {group.interrupt();}  // interrupt writer (прерываем всю группу, исключение не заносится в ex)
        });
        reader.start();

        // writer to 'dst' - поток для записи
        Thread[] writers = new Thread[writers_count]; // потоки для писателей (каждый писатель - новый поток)
        for (int i = 0; i < writers_count; i++) { // перебираем всех писателей
            final int idx = i; // Java не позволяет напрямую использовать переменные в лямбда-выражении, которые изменяются в цикле
            writers[i] = new Thread(group, () -> {
                try (OutputStream dst0 = dst[idx]) {      // 'dst0' for auto-closing (try with resouerces)
                    while (true) {
                        byte[] data = buffers[idx].take();// берем заполненный буфер из reader (блокирует, если пусто)
                        // проверка конца потока
                        if (data[0] == (byte) -1) {
                            buffers[idx + 1].put(data); // передаем следующему буферу сигнал конца
                            break;
                        } //  конец потока — вышли
                        dst[idx].write(data, 1, data[0]); // записываем из data в поток данные с 1 по len (длина хранится в data0) 
                        buffers[idx+1].put(data); // кидаем буфер следующему writer
                    }
                } catch (Exception e) {ex.set(e); group.interrupt();}  // interrupt writer (прерываем всю группу, исключение не заносится в ex)
            });
            writers[i].start();
        }
        // wait to complete read/write operations (главный поток ждёт завершения)
        try {
            reader.join(); // wait for reader
            for (Thread w : writers) w.join(); // wait for writers
        } catch (InterruptedException e) {throw new IOException(e);}
        if (ex.get() != null) {throw new IOException(ex.get());} // обработка ошибки из ex
    }
}