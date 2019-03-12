#include<stdio.h>




int main(int argc, char **argv) {
    char a = 'A';
    char b = 'B';

    int i = 5;
    char c = 'C';
    char d = 'D';
    char *p  = (char *)&i;
    
    p[1] = 1;

    *(p - 4) = 'T';
    printf("%c\n", i);
    for (int index = -4; index <= 5; index++) {
        printf("%i: %c\n", index, p[index]);
    }
    return 0;
}
