#include <stdio.h>
#include <stdlib.h>

int store (char * m);

int main (int argc, char **argv) {
    char * m;

    if (argc != 2) {
        fprintf(stderr, "Uso de %s<mensaje>\n", argv[0]);
        exit(1);
    }

    m = argv[1];

    store(m);
    printf("El mensaje fue almacenado");

    return 0;
}

int store (char * m) {
    FILE *fp = fopen("mensajes.txt", "a");

    fprintf(fp, "%s\n", m);
    fclose(fp);
    return 1;
}

