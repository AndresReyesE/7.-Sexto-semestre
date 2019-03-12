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
	
	for(int i = 0; i < 4; i++) {
		fprintf(f, "%c", p[3 - i]);
	}

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


int main () {
	char * p = (char *) &num;
	printf("Creando archivo message.bytes\n");
	printData();
	writeData(p);
	printf("Writing process done...\n");
	return 0;
}
