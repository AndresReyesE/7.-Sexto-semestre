#include <stdio.h>
#include <math.h>

/*
24.  Que calcule la suma de los cuadrados de los 100 primeros números.
*/

//Toma un número y regresa ese número elevado al cuadrado
double cuadrado (double x) {
    return pow(x, 2.0);
}

int main (void) {
    double acumulado = 0;

    for (double i = 0; i <= 100; i++)
        acumulado += cuadrado(i);

    printf("La suma de los cuadrados de los primeros 100 números es: %lf\n", acumulado);

    return 0;
}