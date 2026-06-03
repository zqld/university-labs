// Импорт пакетов для доступа к классам Zookeeper, Leo, Elephant и Cougar.
package zoo;
import zookeeper.Zookeeper;

public class Zoo {

    public static void main(String[] args) {
        // Вывод приветствия в консоль.
        System.out.println("Хей, привет, добро пожаловать в игру зоопарк!!!");

        // Создание экземпляра класса Zookeeper для ухода за животными в зоопарке.
        Zookeeper zookeeper = new Zookeeper();

        // Создание и использование объекта Leo (льва).
        Leo leo = new Leo("Симба", 3); // Создание льва с именем "Симба" и возрастом 3 лет.
        leo.makeSounds(); // Вызов метода makeSounds() для издания звуков льва.
        zookeeper.feeding(leo); // Кормление льва с помощью объекта Zookeeper.
        if (leo instanceof EatMeat) {
            // Проверка, является ли объект leo экземпляром интерфейса EatMeat.
            int kgMeat = EatMeat.Kilograms_Meat;
            // Получение значения константы Kilograms_Meat из интерфейса EatMeat.
            System.out.println(leo.getName() + " кушает " + kgMeat + " килограммов мяса на ужин.");
            // Вывод сообщения о том, что лев кушает определенное количество мяса.
        } else {
            // Если объект leo не является экземпляром интерфейса EatMeat, выполняется этот блок кода.
            System.out.println(leo.getName() + " кушает траву.");
            // Вывод сообщения о том, что лев кушает траву.
        }

        // Создание и использование объекта Elephant (слона).
        Elephant elephant = new Elephant("Джамбо", 4); // Создание слона с именем "Джамбо" и возрастом 5 лет.
        elephant.makeSounds(); // Вызов метода makeSounds() для издания звуков слона.
        zookeeper.feeding(elephant); // Кормление слона с помощью объекта Zookeeper.
        zookeeper.wash(elephant); // Мытье слона с помощью объекта Zookeeper.
        if (elephant instanceof EatMeat) {
            // Проверка, является ли объект elephant экземпляром интерфейса EatMeat.
            int kgMeat = EatMeat.Kilograms_Meat;
            // Получение значения константы Kilograms_Meat из интерфейса EatMeat.
            System.out.println(elephant.getName() + " кушает " + kgMeat + " килограммов мяса на ужин.");
            // Вывод сообщения о том, что слон кушает определенное количество мяса.
        } else {
            // Если объект elephant не является экземпляром интерфейса EatMeat, выполняется этот блок кода.
            System.out.println(elephant.getName() + " кушает траву.");
            // Вывод сообщения о том, что слон кушает траву.
        }
        
        // Создание объекта Cougar (пумы) с именем "Люси" и возрастом 1 год.
        Cougar cougar = new Cougar("Люси", 1);
        // Вызов метода makeSounds() для издания звуков пумы.
        cougar.makeSounds();
        // Кормление пумы с помощью объекта Zookeeper.
        zookeeper.feeding(cougar);
        // Мытье пумы с помощью объекта Zookeeper.
        zookeeper.wash(cougar);
        if (cougar instanceof EatMeat) {
            // Проверка, является ли объект cougar экземпляром интерфейса EatMeat.
            int kgMeat = EatMeat.Kilograms_Meat;
            // Получение значения константы Kilograms_Meat из интерфейса EatMeat.
            System.out.println(cougar.getName() + " кушает " + kgMeat + " килограммов мяса на ужин.");
            // Вывод сообщения о том, что пума кушает определенное количество мяса.
        } else {
            // Если объект cougar не является экземпляром интерфейса EatMeat, выполняется этот блок кода.
            System.out.println(cougar.getName() + " кушает траву.");
            // Вывод сообщения о том, что пума кушает траву.
        }
    }
}
