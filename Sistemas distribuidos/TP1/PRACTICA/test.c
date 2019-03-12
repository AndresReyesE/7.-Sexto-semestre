#include <stdio.h>

char a = 'A';
char b = 'B';
int x  = 5;
char c = 'C';
char d = 'D';

int main (int argc, char **argv) {
    printf("AAA");

    int r = 0;
    for (int i = 0; i < x; i++)
        ++r;

    r = getc(stdin);
    printf("BBB");
    return 0;
}