#include <stdio.h>

int func (void * ptr, int * a) {
	printf("ptr: %x - %x = %x\n", &ptr, ptr, (int *)ptr);
	printf("a: %x - %x\n", &a, 	a);
	*(int *) ptr = a;
	printf("ptr: %x - %x = %x\n", &ptr, ptr, (int *)ptr);
	printf("a: %x - %x\n", &a, a);
}

int main () {
	int * r;
	int R = 8;




	// void * ptr = &R;

	// r = ptr;
	printf("r: %x - %x = %x\n", &r, r, *r);
	printf("R: %x - %x\n", &R, R);

	func (&r, &R);

	printf("r: %x - %x = %x\n ", &r, r, *r);
	printf("R: %x - %x\n", &R, R);


	//printf("%d <  \n", *r);
	return 0;
}
