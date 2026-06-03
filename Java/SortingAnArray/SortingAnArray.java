//Реализовать сортировку массива методом пузырька.
//В данной программе мы можем задавать размер массива и присваивать значения его элемента.
package com.mycompany.sortinganarray;
import java.util.Scanner;
public class SortingAnArray {
public static Scanner in = new Scanner(System.in);
//Основной метод, в котором задается массив.
    public static void main(String[] args) {
        System.out.print("Введите число элементов массива: ");
        int element = in.nextInt();
        int []Array = new int[element];
        for(int i=0;i<element;i++){
            System.out.printf("Введите значение элемента массива №%d: ",i+1);
            Array[i] =in.nextInt();
        }
        //Создаем объект класса Sorting для выполнения сортировки
        Sorting sorting = new Sorting();
        // Вызываем метод сортировки пузырьком и передаем массив
        sorting.bubbleSorting(Array);
    }
}

