#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

/*
 * Carlos Andr�s Reyes Evangelista
 * 157068
 * Ingenier�a en Sistemas Computacionales. UDLAP.
 */
 
 int size = 24;
 const int len = 100;
 char path[100];
 
 struct s {
 	char** stack;
 	int size;
 	int top;
 };
 
void display (struct s *st) {
	printf("SIZE: %d\n\n", st->size);
	for (int i = 0; i < st->top + 1; i++)
		printf("STRING %i: %s\n", i, st->stack[i]);
}

void init_stack (struct s *st) {
	st->size = size;
	st->top = -1;
	
	st->stack = (char**) malloc(st->size * sizeof(char*));
	
	for (int i = 0; i < st->size; i++)
		st->stack[i] = (char*) malloc(len * sizeof(char));
	
	
	for (int i = 0; i < size; i++)
		strncpy(st->stack[i], "asd", 0);
}

int empty(struct s st) {
	return (st.top == -1);
}

int full(struct s st) {
	return (st.top == st.size-1);
}

void push(struct s *st, char data[]) {
	st->stack[++st->top] = data;
}

void pop(struct s *st) {
	st->top--;
}

void swap (char hero[][len], int i, int j) {
	char aux[len];
	
	strncpy (aux, hero[i], len);
	strncpy (hero[i], hero[i + j], len);
	strncpy (hero[i + j], aux, len);	
}

void randomize (char hero [][len]) {
	srand(time(NULL));
	char aux[len];
	
	int k, j;
	k = size;
	
	for (int i = 0; i < size; i++) {
		j = rand() % k;
		
		swap (hero, i, j);
		
		k--;
	}
}

void to_stack (struct s *st, char hero [][len]) {
	for (int i = 0; i < size; i++)
		push (st, hero[i]);
}

int read (struct s *st, char hero [][len]) {
	FILE *in;
	int TOP;
	
 	in = fopen (path, "r");
	
	
	for (int i = 0; i < size; i++)
		fscanf(in, "%s", &hero[i]);	
		
	fscanf(in, "%i", &TOP);
	
	fclose (in);
	
	return TOP;
}

void write (struct s *st, char hero [][len]) {
	FILE *out;
 	out = fopen (path, "w");
	
 	for (int i = 0; i < size; i++)
	 	fprintf(out, "%s\n", hero[i]);
	 	
	fprintf(out, "%i", st->top);
	fclose(out);
}



int main (int argc, char **argv) {
//	char hero[][20] = {"Androxus", "Ash", "Barik", "Bomb_King", "Buck", "Cassie", "Drogoz", "Evie", "Fernando", "Furia", "Grohk", "Grover", "Inara", "Jenos", "Khan", "Kinessa", "Koga", "Lex", "Lian", "Maeve", "Makoa", "Mal'Damba", "Moji", "Pip", "Ruckus", "Seris", "Sha-lin", "Skye", "Strix", "Talus", "Terminus", "Torvald", "Tyra", "Viktor", "Vivian", "Willo", "Ying", "Zhin"};	 	
	
	// printf("Arg received: %s", argv[1]);
	strcpy(path, argv[1]);

	struct s stack;
	int TOP, opc;
	char current[size][len];
	
	init_stack (&stack);
	
	TOP = read (&stack, current);
	
	to_stack(&stack, current);
	
	stack.top = TOP;
		
	printf("\nCURRENT QUESTION: %s (%i)\n", current[stack.top], stack.top);
		
	while (opc != 27) {

		printf("\nPress Enter to pop...\n");
		opc = getc(stdin);

		printf("Key pressed: %i \n", opc);

		if (opc == 10) {
			if (empty(stack)) {
				randomize (current);
				to_stack(&stack, current);
			}
			else 
				pop (&stack);	
				
			write (&stack, current);
			
			system("clear");
			printf("\nCURRENT QUESTION: %s (%i)\n", current[stack.top], stack.top);
		}
	}
	
	free(stack.stack);
	return 0;
}

