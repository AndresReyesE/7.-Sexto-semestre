#include "server.h"
#include "../interface.h"
#include <stdarg.h>

/**
 * Receives a code from the client to indicate the service the client want to perform
 * 
 * @params 
 *  client_sock - file descriptor indicating the client's socket
 * @return
 *  -1 if there's an error of communication while receiving the request
 *  0 if the client was already disconnected
 *  Otherwise, returns the ID of the service to perform
 */
int recv_message(int client_sock);

/**
 * Receives data from a client to perform a remote sum (by value)
 * @params
 *  client_sock - file descriptor indicating the client's socket 
 * @return
 *  the sum already performed (temporary)
 */
void recv_sum (int client_sock, int * a, int * b);

/**
 * Receives data from a client to perform a remote sum (by copy-restore)
 * @params
 *  client_sock - file descriptor indicating the client's socket 
 * @return void
 */
void recv_sumr (int client_sock, int * a, int * b, int * c);

void recv_struct (int client_sock, struct person * p);

void recv_str (int client_sock, char * s);

void send_result (int client_sock, int value);

void send_struct (int client_sock, struct person * p);

extern void perform_call (int id_request, void * result, int n, ...);

/* ################################################# */
int main(int argc, char *argv[]) {
    int socket_desc = initialization();
    
    while(1) {
        int client_sock = connection(socket_desc);
        // process(client_sock);
        int request = recv_message(client_sock);

        int result;
        
        
        int a, b, c;
        char * s = (char *) malloc (30);
        strcpy(s, "");

        struct person per;
        switch (request) {
            case 2:
                recv_sum(client_sock, &a, &b);
                perform_call(2, &c, 2, a, b);
                send_result(client_sock, c);
                break;
            case 3:
                recv_sumr(client_sock, &a, &b, &c);
                perform_call(3, 0, 3, &a, &b, &c);
                // sumr(&a, &b, &c);
                send_result(client_sock, a);
                send_result(client_sock, b);
                send_result(client_sock, c);
                break;
            case 4:
                recv_struct(client_sock, &per);
                perform_call(4, &per, 0);
                send_struct(client_sock, &per);
                break;
            case 5:
                recv_struct(client_sock, &per);
                recv_str (client_sock, s);
                perform_call(5, &per, 1, s);
                send_struct(client_sock, &per);
                break;
        }
        free(s);
    }
    
    close(socket_desc);
    return 0;
}
/* ################################################# */

int recv_message (int client_sock) {
    
    int status, service_to_perform;

    status = read (client_sock, &service_to_perform, sizeof(int));

    
    if (status == -1 || status == 0) {
        perror(status == -1 ? "Read of request failed from server side" : "Client disconnected");
        service_to_perform = status;
    }

    return service_to_perform;
}

void recv_sum (int client_sock, int * a, int * b) {
    int statusA, statusB;

    statusA = read (client_sock, a, sizeof(int));
    statusB = read (client_sock, b, sizeof(int));

    if (statusA == 0 || statusB == 0)
        puts("Client disconnected");

    if (statusA == -1 || statusB == -1)
        perror("Read of A or B failed from server side");
}

void recv_sumr (int client_sock, int * a, int * b, int * c) {
    int statusA, statusB, statusC;

    statusA = read (client_sock, a, sizeof(int));
    statusB = read (client_sock, b, sizeof(int));
    statusC = read (client_sock, c, sizeof(int));
    
    if (statusA == 0 || statusB == 0 || statusC == 0)
        puts("Client disconnected");

    if (statusA == -1 || statusB == -1 || statusC == -1)
        perror("Read of A, B or C failed from server side");
}

void recv_struct (int client_sock, struct person * p) {
    int status = read (client_sock, p, sizeof (struct person));

    if (status == 0)
        puts("Client disconnected");

    else if (status == -1)
        perror("Read of struct failed from server side");
}

void recv_str (int client_sock, char * s) {
    int n;
    // s = (char *) malloc (n);

    int statusN = read (client_sock, &n, sizeof(int));
    int statusS = read (client_sock, s, n);

    printf(" >>s: %s\n", s);

    if (statusN == 0 || statusS == 0)
        puts("Client disconnected");

    else if (statusN == -1 || statusS == -1)
        perror("Read of string failed from server side");
}

void send_result (int client_sock, int value) {
    printf("Server result: %d\n", value);

    if (send(client_sock, &value, sizeof(value), 0) < 0) {
        puts ("Send failed!");
        exit(1);
    }
}

void send_struct (int client_sock, struct person * p) {
    printf("Server result: %s - %d [%s %d, %s]\n", p->name, p->age, p->addr.street, p->addr.number, p->addr.city);

    if (write (client_sock, p, sizeof(struct person)) < 0) {
        puts ("Send failed!");
        exit(1);
    }
}