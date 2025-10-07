import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.Comparator;

class Result{
    int threadCount;
    double result;
    long time;
    
    public Result(int threadCount, double result, long time) {
        this.threadCount = threadCount;
        this.result = result;
        this.time = time;
    }
    
    public String toString() {
        return("Кол-во потоков: " + threadCount + " Результат: " + result + " Время: " + time + " нс");
    }
}


public class Main{

    public static void main(String[] args) {
        double a = 0;
        double b = 5;
        double h = Math.pow(10, -6); //точность
        int threadMax = 20;
        Function<Double, Double> function = Math::exp; // функция для интеграла
        List<Result> results = new ArrayList<>(); //список для результатов
        
        for (int thread = 1; thread <= threadMax; thread++){
            MyIntegral integral = new MyIntegral(a, b, h, thread, function);
            long startTime = System.nanoTime(); // старт время
            double result = integral.calc(); // интеграл
            long endTime = System.nanoTime(); // конец время
            long elapsedTime = endTime - startTime; // затраченное время
            
            
            results.add(new Result(thread, result, elapsedTime)); // результат
        }
        
        results.sort(Comparator.comparingLong(t -> t.time)); //cортировка результатов
        for (Result t:results){
            System.out.println(t);
        }
        
    }
    
}
