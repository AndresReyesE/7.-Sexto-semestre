#include "client.h"
#include "../interface.h"

extern int sock;

int store (char * m) {
    int read_size, result;
    int length = strlen(m);

    send(sock, &length, sizeof(length), 0);

    if (send (sock, m, length, 0) < 0) {
        puts ("Send failed");
        exit(1);
    }

    read_size = recv (sock, &result, sizeof(result), 0);

    if (read_size == 0) {
        puts ("Client disconnected");
        fflush (stdout);
    }
    else if (read_size == -1)
        perror("recv failed");

    return result;
}

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
    // if (cr == -1)
        puts ("Error at the reception in client side");
    else if (ar == 0 || br == 0 || cr == 0)
    // else if (cr == 0)
        puts ("Client disconnected");
    

}