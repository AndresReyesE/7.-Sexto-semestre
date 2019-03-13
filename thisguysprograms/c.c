#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/*
48. Escribe un programa que reciba un número entero N del usuario e imprima el contorno de
pirámide de N pisos de asteriscos. Ej.
>> 3
      *
     * *
    *****
*/

//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

//Verifica que el número sea positivo, regresa 0 si lo es, 1 si es no-positivo
int verificar_valor (int x) {
    int valido = 1;

    if (x <= 0)
        printf ("El numero debe de ser mayor que 0\n");
    else
        valido = 0;

    return valido; 
}

void imprimir_rombo (int x) {
    for (int i = 0; i < x; i++) {
        for (int j = x - i - 1; j >= 0; j--)
            printf(" ");

        for (int j = 0; j < i * 2 + 1; j++)
            printf("*");
        
        printf("\n");
    }
}

int main (void) {
    int x, valido;
    
    do {
        x = obtener_dato();
        valido = verificar_valor(x);
    } while (valido != 0);

    imprimir_rombo(x);

    return 0;
}