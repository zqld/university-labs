
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//Это реализация однопоточного побайтового копировальщика из InputStream в OutputStream. 
//Копирование происходит в потоке вызвавшем метод copy(...)

public class CopyUtil1 {
    public static void copy(InputStream src, OutputStream... dst) throws IOException {
        try (InputStream src0 = src) { // try with resources (закрывает в обратном порядке)
            // поток закроется сам, а вот выходные потоки закрываем после копирования (потому что непонятно сколько их)
            int b; // read вовзращает инт и -1 при конце потока
            while ((b = src0.read()) != -1) { //-1 = конец потока
                for (OutputStream d : dst) { // для всех выходных потоков
                    d.write(b); // запись одиночного байта (записывает младший байт)
                }
            }
            for (OutputStream d : dst) {
                d.close(); // закрываем все выходные потоки
            }
        }
    }
}