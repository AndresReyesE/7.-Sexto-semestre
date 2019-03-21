#include "client.h"
#include "../interface.h"

int sock;


/**
 * Client functionality is defined here
 * 
 * Note: commented code is the way other services this server offer are invoked
 */
int main (int argc, char **argv) {
    if(argc != 2) {
	    fprintf(stderr, "Usage: %s <\"name_searched\">\n", argv[0]);
	    exit(1);
	}

    sock = connection();

    // int a = atoi(argv[1]);
    // int b = atoi(argv[2]);
    // int result = sum (a, b);

    // int result;
    // sumr (&a, &b, &result);
    
    // printf("Resultado recibido %d\n", result);

    struct person p;    //structure where the results (by reference) will be stored

    int found = search_data(&p, argv[1]);

    if (found) { //the call to search_data is performed as if it was a local function
        printf("Register found!\n");
        printf("Name:\t%s\n",   p.name);
        printf("Age:\t%d\n",    p.age);
        printf("Street:\t%s\n", p.addr.street);
        printf("Number:\t%d\n", p.addr.number);
        printf("City:\t%s\n",   p.addr.city);
    }   
    else
        printf("Name not found\n\n");

    close(sock);

    return 1;
}