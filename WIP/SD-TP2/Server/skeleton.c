#include "server.h"
#include "../interface.h"

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
int recv_sum (int client_sock);

/**
 * Receives data from a client to perform a remote sum (by copy-restore)
 * @params
 *  client_sock - file descriptor indicating the client's socket 
 * @return void
 */
void recv_sumr (int client_sock, int * a, int * b, int * c);

void send_result (int client_sock, int value);

int recv_message (int client_sock) {
    
    int status, service_to_perform;

    status = read (client_sock, &service_to_perform, sizeof(int));

    
    if (status == -1 || status == 0) {
        perror(status == -1 ? "Read of request failed from server side" : "Client disconnected");
        service_to_perform = status;
    }

    return service_to_perform;
}

int recv_sum (int client_sock) {
    int a, b;
    int statusA, statusB;

    statusA = read (client_sock, &a, sizeof(a));
    statusB = read (client_sock, &b, sizeof(b));

    if (statusA == 0 || statusB == 0)
        puts("Client disconnected");

    if (statusA == -1 || statusB == -1)
        perror("Read of A or B failed from server side");

    return sum (a, b);
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

void send_result (int client_sock, int value) {
    printf("Server result: %d\n", value);

    if (send(client_sock, &value, sizeof(value), 0) < 0) {
        puts ("Send failed!");
        exit(1);
    }
}