#include <stdio.h>

int obtener_numero() {
    int x;
    printf("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

float calcular_serie (int n) {
    float total = 1;
    float elemento;

    for (int i = 1; i < n; i++) {
        elemento = (float) 1 / (2 * i);
        
        if (i % 2 == 1) {
            total = total - elemento;
        }
        
        else {
            total = total + elemento;
        }
    }

    return total;
}

int main (void) {
    int n = obtener_numero();

    float resultado = calcular_serie (n);

    printf("El resultado de la serie para %d elementos es: %f\n", n, resultado);

    return 0;
}