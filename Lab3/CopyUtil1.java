
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//Это реализация однопоточного побайтового копировальщика из InputStream в OutputStream. 
//Копирование происходит в потоке вызвавшем метод copy(...)

public class CopyUtil1 {
    public static void copy(InputStream src, OutputStream dst)throws IOException{
        try (InputStream src0 = src; OutputStream dst0 = dst) { // try with resources (закрывает в обратном порядке)
            int b; // read вовзращает инт и -1 при конце потока
            while ((b = src.read()) != -1) { //-1 = конец потока
                dst.write(b); // запись одиночного байта (записывает младший байт)
            }
        }
    }
}
