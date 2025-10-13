import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//Это реализация однопоточного копировальщика массивами из InputStream в OutputStream. 
//Копирование происходит в потоке вызвавшем метод copy(...)

public class CopyUtil2 {
    public static void copy(InputStream src, OutputStream... dst) throws IOException {
        byte[] buff = new byte[128]; // буфер 128 байт для чтения блоками
        try (InputStream src0 = src) { 
            int count;  // read вовзращает инт и -1 при конце потока
            while ((count = src0.read(buff)) != -1) {// заполняет буфер полностью и возвращает кол-во прочитаных байт (-1 если конец)
                for (OutputStream d : dst) {
                    d.write(buff, 0, count); // запись только count байт из начала массива buff(0 - начало массива) (для всех потоков)
                }
            }
            for (OutputStream d : dst) {
                d.close(); // закрываем всех потоки
            }
        }
    }
}