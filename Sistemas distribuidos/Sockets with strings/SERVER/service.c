#include <stdio.h>
#include <string.h>

int store (char * m) {
    FILE *fp = fopen("mensajes.txt", "a");

    fprintf(fp, "%s\n", m);
    fclose(fp);
    return strlen(m);
}

