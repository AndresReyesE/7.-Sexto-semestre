#include <stdio.h>
#include <stdlib.h>

int store (char * m) {
    FILE *fp = fopen("mensajes.txt", "a");

    fprintf(fp, "%s\n", m);
    fclose(fp);
    return 1;
}
