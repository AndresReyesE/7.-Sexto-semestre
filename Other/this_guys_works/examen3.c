#include <stdio.h>
#include <time.h>
#include <stdlib.h>


int main (void) {
    int A[101];

    for (int i = 0; i < 101; i++) //Arreglo de 100 números que tendrá números aleatorios del 1 al 10
        A[i] = (rand() % 10) + 1;
    
     //en este arreglo contadores se van a guardar la cantidad de veces que cada número se repite en su correspondiente 
     //índice, en contadores[1] se guardará la cantidad de 1 que hay, en contadores[2] cuántos 2 hay y así
    int contadores[11];

    for (int i = 1; i <= 10; i++) //primero se inicializan campos 1 a 10 en 0
        contadores[i] = 0;

    for (int i = 0; i < 101; i++) {
        int posicion = A[i];
        contadores[posicion]++; //aquí se aumenta 1 a cada espacio del arreglo dependiendo del número que encuentre en A
    }


    for (int i = 1; i <= 10; i++)
        printf("Del numero %d hay %d\n", i, contadores[i]);
}