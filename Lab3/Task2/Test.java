import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
//задание 2

public class Test {
    public static void main(String[] args) throws IOException {
        Random rnd = new Random(0); // фиксированная случайная последовательность
        byte[] testData = new byte[64 * 1024]; // объявление места для 64 кбайт
        rnd.nextBytes(testData); // записываем генерацию 64 случайных байт
        ByteArrayOutputStream dst = new ByteArrayOutputStream(); // поток для получения записанных байтов
        long startTime = System.currentTimeMillis();
        CopyUtil3.copy(new ByteArrayInputStream(testData), dst); // копирование из рандом данных в поток dst
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        if (!Arrays.equals(testData, dst.toByteArray())) { // проверка совпадления поток (toByteArray возвращает содержимое потока в виде массива байтов)
            throw new AssertionError("Lab decision wrong!"); 
        } else {
            System.out.println("OK!");
        }
    }
}