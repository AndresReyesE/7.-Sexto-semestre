#include <stdio.h>

char a = '-';
char b = '-';
long num = -1;
char c = '-';
char d = '-';

void readFile(int sizeOfNum) {
	long * p = &num;
	FILE * f = fopen("message.bytes", "r");
	a = fgetc(f);
	b = fgetc(f);
		
	for (int i = sizeof(num) - 1; i >= 0; i--) {
		
		if (i < sizeOfNum) {
			p[i] = getc(f);
		} else {
			p[i] = 0;
		}
	}

	c = fgetc(f);
	d = fgetc(f);
	fclose(f);
}

void printData() {
	printf("a = %c\n", a);
	printf("b = %c\n", b);
	printf("num = %i\n", num);
	printf("c = %c\n", c);
	printf("d = %c\n", d);
}

void display () {
	char *p2 = &a;
	char *p3 = &d;
	for (long i = (long) p2; i < p3 - p2; i++)
		printf("%l: %i - %c\n", i, p2[i], p2[i]);
	
}

int main () {
	printf("In memory...");
	display();

	printData();
	printf("Reading file...\n");
	readFile(sizeof(int));
	printData();
	return 0;
}

