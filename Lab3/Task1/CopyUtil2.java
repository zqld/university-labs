import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//Это реализация однопоточного копировальщика массивами из InputStream в OutputStream. 
//Копирование происходит в потоке вызвавшем метод copy(...)


public class CopyUtil2 {
    public static void copy(InputStream src, OutputStream dst)throws IOException{
        byte[] buff = new byte[128]; // буфер 128 байт для чтения блоками
        try (InputStream src0 = src; OutputStream dst0 = dst) {
            int count;  // read вовзращает инт и -1 при конце потока
            while ((count = src.read(buff)) != -1) { // заполняет буфер полностью и возвращает кол-во прочитаных байт (-1 если конец)
                dst.write(buff, 0, count); // запись только count байт из начала массива buff(0 - начало массива)
            }
        }
    }
}