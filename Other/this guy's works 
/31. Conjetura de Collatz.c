#include <stdio.h>
#include <math.h>

/*
31.- Considere siguiente proceso repetitivo para un número entero dado: 
si el número es 1, el proceso termina. 
De lo contrario, si es par se divide entre 2, 
y si es impar se multiplica por 3 y se le suma 1. 
Si empezamos con 6, por ejemplo, obtendremos la sucesión de números 6, 3, 10, 5, 16, 8, 4, 2, 1. 
La conjetura de Collatz dice que, a partir de cualquier número inicial,
la sucesión obtenida siempre termina en 1.
Escriba un programa que permita verificar la conjetura de Collatz para cualquier entero dado, 
y que imprima la secuencia correspondiente. 
*/

//Pide un número al usuario y lo regresa
int obtener_dato() {
    int x;
    
    printf ("Ingrese un numero: ");
    scanf ("%d", &x);

    return x;
}

/*
La función entra con un número x, lo imprime y después condiciona:
Si el número es uno, la función terminará imprimiendo éxito,
si no lo es decidirá si el número es par o impar
y llamará a la función misma con el valor indicado por Collatz (la mitad si es par o 3 veces más 1 si es impar) 
*/
void collatz (int x) {
    printf("%d\t", x);
    
    if (x == 1)
        printf("\n\n Fin de la ejecucion! Conjetura probada con exito!\n");

    else {
        if (x % 2 == 0)
            collatz (x / 2);
        else 
            collatz (x * 3 + 1);
    }
}

int main (void) {
    
    int x;
    x = obtener_dato();

    collatz(x);

    return 0;
}