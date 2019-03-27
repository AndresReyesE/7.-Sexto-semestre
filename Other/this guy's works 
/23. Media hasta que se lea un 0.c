#include <stdio.h>

/*
23.  Que calcule la media de X números, se dejarán de solicitar números hasta que se introduzca el cero.
*/


//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

float media (int acumulado, int n) {
    float media = (float) acumulado / n;
    
    return media;
}

void imprimir (int acumulado, int n, float media) {
    printf("Su total acumulado fue de %d obtenido de %d numeros:\n\n", acumulado, n);

    printf("Su media es: %f\n", media);
}

int main (void) {
    int acumulado = -1;
    int total_de_numeros = -1;
    int nuevo_numero = 1;
    float promedio = 0;

    /*Inicia el acumulado en -1 para que entre en el ciclo y ahí se sumará el valor de nuevo_numero, en
    la primera iteración el acumulado y el total de números se igualarán a 0 (-1 + 1)
    y a partir de ahí se pedirán nuevos números hasta que se dé un 0*/
    while (nuevo_numero != 0) {
        acumulado += nuevo_numero;
        total_de_numeros++;
        nuevo_numero = obtener_dato();
    }

    promedio = media(acumulado, total_de_numeros);

    imprimir(acumulado, total_de_numeros, promedio);

    return 0;
}