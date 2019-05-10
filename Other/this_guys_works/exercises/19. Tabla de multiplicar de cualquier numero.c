#include <stdio.h>

/*
19.  Que muestre la tabla de multiplicar de un número cualquiera.
*/


//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

void imprimir_tabla (int x) {
    printf("Tabla de multiplicar del %d:\n\n", x);

    for (int i = 0; i <= 10; i++)
            printf("%d \t* %d \t= %d\n", x, i, x * i);
}

int main (void) {
    int x = obtener_dato();

    imprimir_tabla(x);

    return 0;
}