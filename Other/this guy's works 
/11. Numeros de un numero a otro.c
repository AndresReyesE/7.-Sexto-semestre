#include <stdio.h>

/*
11.  Que pida dos números y muestre todos los números que van desde el primero al segundo. 
Se debe controlar que los valores son correctos.
*/

//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

// Esta función regresa 0 si los valores son válidos y regresa 1 si no lo son
int verificar_numeros(int x, int y) {
    int valido;

    if (x == y) {
        printf("Los valores no pueden ser iguales\n\n");
        valido = 1;
    }
    else if (x > y) {
        printf("El segundo valor debe ser mas grande que el primero\n\n");
        valido = 1;
    }
    else {
        printf("Valores validos!\n\n");
        valido = 0;
    }

    return valido;
}

void imprimir_valores (int x, int y) {
    printf("Valores del %d al %d\n\n", x, y);
    for (int i = x; i <= y; i++)
        printf("%d\n", i);
}

int main (void) {
    int x, y;
    int validos = 1;

    while (validos != 0) { //mientras los datos no sean válidos, los seguirá pidiendo
        x = obtener_dato();
        
        y = obtener_dato();
        
        validos = verificar_numeros(x, y);
    }


    imprimir_valores(x, y);

    return 0;
}