#include <stdio.h>
#include <stdlib.h>

void swap (int * p, int * q);
void randomize (int * A, int size);

int main (void) {
    // int size = 30;
    // // int * A = (int *) malloc(30 * sizeof(int));
    // int A[size];

    // for (int i = 0; i < size; i++)
    //     A[i] = i + 1;

    // randomize(A, size);

    // free (A);

    int i = 18, *ip, **ipp;

    ip = &i;
    ipp = &ip;

    printf("ip = %lo, &ip = %lo, ipp = %lo\n", ip, &ip, ipp);
    printf("*ipp = %lo, *ip = %d\n", *ipp, *ip);
    printf("&i = %lo, i = %d\n", &i, i);

    return 0;
}

void swap (int *p, int *q) {
    int temp = *p;

    *p = *q;
    *q = temp;
}

void randomize (int * A, int size) {
    int k = size;
    for (int i = 0; i < size; i++) {
        int j = rand() % k;

        swap (A + i, A + j);

        k--;
    }
}