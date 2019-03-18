#include "client.h"
#include "../interface.h"

extern int sock;

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

void struct_function (struct person * p) {
    int code = 4;

    if (write (sock, (int *) &code, sizeof(int)) < 0)
        puts ("Something went wrong while sending the code from client side");

    if (write (sock, (struct person *) p, sizeof(struct person)) < 0)
        puts ("Something went wrong while sending the structure from client side");
    
    int status = read (sock, (struct person *) p, sizeof(struct person));

    if (status == -1)
        puts ("Error at the reception in client side");

    else if (status == 0)
        puts ("Client disconnected");

}

int search_data (struct person * p, char * name) {
    int code = 5;
    int len = strlen(name);

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

    if (status == -1)
        puts ("Error at the reception in client side");

    else if (status == 0)
        puts ("Client disconnected");

    return 0;
}