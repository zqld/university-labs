package com.mycompany.sortinganarray;
//Название класса.

import java.util.Arrays;
//Библиотека для работы с массивами.
public class Sorting {
//Создаём класс.
   public void bubbleSorting(int Array1[]){
       //Создание метода класса для сортировки.
       System.out.print("Искомый массив: ");
       //Вывод искомого массива через цикл.
               for(int i=0;i<Array1.length;i++){
            System.out.print(Array1[i]);
            System.out.print("\t");
        }
        System.out.print("\n");       
        boolean transitions = false;
        //Переменная, которая используется для выхода из цикла для сортировки.
         int intermediateValue = 0;
         //Переменная для сохранения значения элемента.
         while(!transitions){
             //Начинаем сортировку, пока значение переменной transitions не будет false.
             transitions = true;
             //Изначально переменной transitions присваивается true.
             // Если текущий элемент больше следующего, меняем их местами.
             for(int i = 0; i<Array1.length-1;i++){
                 if(Array1[i]>Array1[i+1]){
                     transitions = false;
                     intermediateValue = Array1[i];
                     Array1[i] = Array1[i+1];
                     Array1[i+1] = intermediateValue;
                 }
             }
         }
         System.out.println("Отсортированный массив: "+Arrays.toString(Array1));
         /*Вывод отсортированного массива на экран.*/
              
   }
   
}
