#include <stdio.h>


int main () {
    int m;
    int n;

    printf("Ingrese la cantidad de filas: ");
    scanf("%d", &m);
    printf("Ingrese la cantidad de columnas: ");
    scanf("%d", &n);

    int A[m][n];

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            printf("Ingrese elemento %d, %d: ", i, j);
            scanf("%d", &A[i][j]);
        }
    }
    

    int max, min, maxI, maxJ, minI, minJ;
    max = min = A[0][0];
    maxI = maxJ = minI = minJ = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (A[i][j] < min) {
                min = A[i][j];
                minI = i;
                minJ = j;
            }
            if (A[i][j] > max) {
                max = A[i][j];
                maxI = i;
                maxJ = j;
            }
        }
    }

    printf("\nMatriz leida: \n\n");
    for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            printf("%d\t", A[i][j]);
         }
        printf("\n");
    }

    printf("\nValor maximo: %d\nEncontrado en: %d, %d\n", max, maxI, maxJ);
    printf("\nValor minimo: %d\nEncontrado en: %d, %d\n", min, minI, minJ);
    
    return 0;
}