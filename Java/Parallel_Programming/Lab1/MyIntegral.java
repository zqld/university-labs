import java.util.function.Function;
public class MyIntegral {
    private double a, b, h;
    private int threadCount;
    private Function<Double, Double> function;
    
    public MyIntegral(double a, double b, double h, int threadCount, Function<Double, Double> function) {
        this.a = a;
        this.b = b;
        this.h = h;
        this.threadCount = threadCount;
        this.function = function;
    }
    
    public double calc(){
        double[] results = new double[threadCount]; //массив дял результатов
        Thread[] threads = new Thread[threadCount]; //массив потоков
        double step = (b - a) / threadCount; //длина шага для потока
        for(int i = 0; i < threadCount; i++){
            int threadIndex = i;
            threads[i] = new Thread(() ->{
                
               // границы для каждого h
               double tempSum = 0.0;
               double tempA = a + threadIndex * step;
               double tempB = a + (threadIndex + 1) * step;
               
               // подсчет интеграла трапецией
               for (double x = tempA + h; x < tempB; x+=h){
                   tempSum += function.apply(x); //сама функция
               }
               
               tempSum += (function.apply(tempA) + function.apply(tempB)) / 2.0;
               results[threadIndex] = tempSum * h;
            });
            
            threads[i].start();//старт потоков
            
        }
        double finalSum = 0.0;
        //ожидание завершения потоков
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finalSum += results[i];
        }
        
        return finalSum;
               
    }
}
