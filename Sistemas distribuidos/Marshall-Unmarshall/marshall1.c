#include <stdio.h>

char a = 'a';
char b = 'b';
int num = 5;
char c = 'c';
char d = 'd';

void writeData(char * p) {
	FILE * f = fopen("message.bytes", "wb");
	
	fprintf(f, "%c", a);
	fprintf(f, "%c", b);
	
	for(int i = 0; i < sizeof(int); i++) {
		fprintf(f, "%c", p[3 - i]);
	}

	//for (int i = sizeof(i)-1; i >= 0; i--)
	//	fprintf(f, "c", p[j]);

	fprintf(f, "%c", c);
	fprintf(f, "%c", d);
	fclose(f);
}

void printData() {	
	printf("a = %c\n", a);
	printf("b = %c\n", b);
	printf("num = %d\n", num);
	printf("c = %c\n", c);
	printf("d = %c\n", d);
}

void display () {
	char * p2 = &a;

	for (int i = 0; i < 12; i++)
		printf("%c - %i\n", p2[i], p2[i]);
}

int main () {
	char * p = (char *) &num;
	printf("In memory...\n");
	display();
	
	printf("Creando archivo message.bytes\n");
	printData();
	writeData(p);
	printf("Writing process done...\n");
	return 0;
}
