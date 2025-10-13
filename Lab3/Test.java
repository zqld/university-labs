import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
//Задание #4
public class Test {
    public static void main(String[] args) throws IOException {
        Random rnd = new Random(0); // фиксированная случайная последовательность
        byte[] testData = new byte[64 * 1024]; // объявление места для 64 байт
        rnd.nextBytes(testData); // записываем генерацию 64 случайных байт
        ByteArrayOutputStream dst = new ByteArrayOutputStream(); // поток для получения записанных байтов
        ByteArrayOutputStream dst1 = new ByteArrayOutputStream(); // поток для получения записанных байтов
        ByteArrayOutputStream dst2 = new ByteArrayOutputStream(); // поток для получения записанных байтов
        ByteArrayOutputStream dst3 = new ByteArrayOutputStream(); // поток для получения записанных байтов
        CopyUtil3.copy(new ByteArrayInputStream(testData), dst, dst1, dst2, dst3); // копирование из рандом данных в поток dst

        // для проверки
        byte[] a = dst1.toByteArray();
        byte[] b = dst2.toByteArray();
        byte[] c = dst3.toByteArray();

        if (!Arrays.equals(testData, dst.toByteArray())) { // проверка совпадления поток (toByteArray возвращает содержимое потока в виде массива байтов)
            throw new AssertionError("Lab decision wrong!"); 
        } else if (!Arrays.equals(a, b) || !Arrays.equals(a, c)) { // проверка совпадления писателй
            throw new AssertionError("Lab decision wrong!"); 
        } else {
            System.out.println("OK!");
        }
    }
}