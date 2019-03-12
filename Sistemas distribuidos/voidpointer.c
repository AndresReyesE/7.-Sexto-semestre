#include<stdio.h> 
int main() 
{ 
    int f;
    scanf("%d", &f);
    void *p = &f;
    printf("f = %d ", *(int *) p);
    //printf("%d", *(int *)ptr); 
    return 0; 
} 
