#include "client.h"
#include "../interface.h"

extern int sock;

/**
 * Search a name in database (parameters by reference)
 * Sending order:
 * 1. Code 1 (inherent to this method)
 * 2. Value of struct person where final results will be stored (by copy-restore)
 * 3. Length of the name to be searched
 * 4. N bytes correspondent to the searched name
 * 
 * Receiving order:
 * 1. The values of the reference passed structure to be stored in the reference of the structure first passed
 * 2. An bool-like integer in C standard format (0 for false, 1 for true)
 */
int search_data (struct person * p, char * name) {
    int code = 1;
    int len = strlen(name), found;

    //name = (char *) malloc(len);

    if (write (sock, (int *) &code, sizeof(int)) < 0)
        puts ("Something went wrong while sending the code from client side");

    if (write (sock, (struct person *) p, sizeof(struct person)) < 0)
        puts ("Something went wrong while sending the structure from client side");

    if (write (sock, &len, sizeof(int)) < 0)
        puts ("Something went wrong while sending the size of the string from client side");
    
    if (write (sock, name, len) < 0)
        puts ("Something went wrong while sending the string from client side");
    
    int status = read (sock, (struct person *) p, sizeof(struct person));
    int statusF = read (sock, &found, sizeof(int));

    if (status == -1 || statusF == -1)
        puts ("Error at the reception in client side");

    else if (status == 0 || statusF == 0)
        puts ("Client disconnected");

    return found;
}

/**
 * Sum (parameters by value)
 * Sending order:
 * 1. Code 2 (inherent to this method)
 * 2. Addend 1
 * 3. Addend 2
 * 
 * Receive:
 * 1. Result of addend 1 + addend 2
 */
int sum (int a, int b) {
    int code = 2;

    if (write (sock, (int *) &code, sizeof(int)) < 0)
        puts ("Something went wrong while sending the code from client side");

    if (write (sock, (int *) &a, sizeof(a)) < 0)
        puts ("Something went wrong while sending a from client side");
    
    if (write (sock, (int *) &b, sizeof(b)) < 0)
        puts ("Something went wrong while sending b from client side");
    
    int result;
    int r = read (sock, (int *) &result, sizeof(int));


    if (r == -1)
        puts ("Error at the reception in client side");
    else if (r == 0)
        puts ("Client disconnected");
    
    return result;
}

/**
 * Sum (parameters by reference)
 * Sending order:
 * 1. Code 3 (inherent to this method)
 * 2. Reference for addend 1
 * 3. Reference for addend 2
 * 4. Reference to store the result
 * 
 * Receiving order:
 * 1. Reference of the addend 1
 * 2. Reference of the addend 2
 * 3. Reference storing the result of addend 1 + addend 2
 */
void sumr (int * a, int * b, int * c) {
    int code = 3;

    if (write (sock, (int *) &code, sizeof(int)) < 0)
        puts ("Something went wrong while sending the code from client side");

    if (write (sock, (int *) a, sizeof(int)) < 0)
        puts ("Something went wrong while sending a from client side");
    
    if (write (sock, (int *) b, sizeof(int)) < 0)
        puts ("Something went wrong while sending b from client side");

    if (write (sock, (int *) c, sizeof(int)) < 0)
        puts ("Something went wrong while sending b from client side");
    
    
    int result;
    int ar = read (sock, (int *) a, sizeof(int));
    int br = read (sock, (int *) b, sizeof(int));
    int cr = read (sock, (int *) c, sizeof(int));

    if (ar == -1 || br == - 1 || cr == -1)
        puts ("Error at the reception in client side");
    else if (ar == 0 || br == 0 || cr == 0)
        puts ("Client disconnected");
}
