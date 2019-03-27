#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/*
38.- 10. Escribe un programa que reciba un número entero N del usuario e imprima el contorno de un
cuadrado de N x N de asteriscos. Ej.
>> 3
***
* *
***
*/

//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

void dibujar_cuadrado(int x) {
    for (int i = 0; i < x; i++) {
        if (i == 0 || i == x - 1)
            for (int j = 0; j < x; j++) 
                printf("*");
        else
            for (int j = 0; j < x; j++) 
                if (j == 0 || j == x - 1)
                    printf("*");
                else
                    printf(" ");
        printf("\n");
    }
}

int main (void) {
    int x = obtener_dato();

    dibujar_cuadrado(x);

    return 0;
}