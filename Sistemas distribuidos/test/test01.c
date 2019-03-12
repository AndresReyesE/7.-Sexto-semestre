#include<stdio.h>



int i = 5;

int main (int argc, char **argv) {
    char *p =(int*) &i;

    printf("%i - %p\n%b", i, *p, 7);

    for (int j = 0; j < 4; j++)
        printf("%i\n", *(p+j));

}