#include <stdio.h>
#include <string.h>

/**
 * Service 1
 */
int store (char * m) {
    FILE *fp = fopen("mensajes.txt", "a");

    fprintf(fp, "%s\n", m);
    fclose(fp);
    return strlen(m);
}

/**
 * Service 2
 */
int sum (int a, int b) {
    return a + b;
}

/**
 * Service 3
 */
void sumr (int * a, int * b, int * c) {
    *c = *a + *b;
}