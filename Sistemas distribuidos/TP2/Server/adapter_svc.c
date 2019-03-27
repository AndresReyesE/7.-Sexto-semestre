/**
 * This file works as Server skeleton (server's stub), it is the one responsible for  
 * stablish and manage the connections incoming from Client's stubs.
 * 
 */

#include "server.h"
#include "../interface.h"
#include <stdarg.h>

/*
    AUXILIAR FUNCTIONS (IMPLEMENTATION) TO CONTROL SENDING AND RECEIVING OF INFORMATION
                                                                                           */

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
 */
void recv_sum (int client_sock, int * a, int * b);

/**
 * Receives data from a client to perform a remote sum (by copy-restore)
 * @params
 *  client_sock - file descriptor indicating the client's socket 
 * @return void
 */
void recv_sumr (int client_sock, int * a, int * b, int * c);

/**
 * Receive data from a client of a person type structure to be stored in the reference received as parameter
 * 
 * @params
 *  client_sock - file descriptor indicating the client's socket
 *  p - person structure pointer where the incoming structure will be stored 
 */
void recv_struct (int client_sock, struct person * p);

/**
 * Receive a string from a client to be stored in the reference received as parameter
 * 
 * @params
 *  client_sock - file descriptor indicating the client's socket
 *  s - string pointer where the incoming structure will be stored 
 */
void recv_str (int client_sock, char * s);


/**
 * Send an integer to the specified socket
 * 
 * @params
 *  client_sock - file descriptor indicating the receiver's socket
 *  value - integer containing the value to be delivered
 */
void send_result (int client_sock, int value);

/**
 * Send the values of the structure parameter to the specified socket
 * 
 * @params
 *  client_sock - file descriptor indicating the receiver's socket
 *  p - person structure pointer containing the values of the structure to be delivered
 */
void send_struct (int client_sock, struct person * p);

/**
 * Server's side function in charge of perform the services with the parameters specified by the invokation to this function
 * 
 * @params
 *  id_request - integer indicating the service to be performed
 *  result - pointer to the any-kind object where the result of the function will be stored
 *  n - number of the 'auxiliar' params that will be received by the function of the service to be performed (i.e., 1 for search_data, 2 for sum, 3 for sumr)
 *  After n it can be included n number of params in the order the objective function have to receive them 
 */
extern int perform_call (int id_request, void * result, int n, ...);

/* ################################################# */
int main(int argc, char *argv[]) {
    int socket_desc = initialization();
    
    while(1) {
        int client_sock = connection(socket_desc);
        
        //skeleton receive the code of the function to be performed
        int request = recv_message(client_sock);

        //auxiliar variables reseted for every client where the reference values can be stored
        int result;
        int a, b, c;
        char * s = (char *) malloc (30);
        struct person per;

        switch (request) {

            case 1: //code correspondent to search_data()
                recv_struct(client_sock, &per);
                recv_str (client_sock, s);
                printf(" >>Searched name: %s\n", s);
                result = perform_call(request, &per, 1, s);
                if (result)
                    printf("Server result: %s - %d [%s %d, %s]\n", per.name, per.age, per.addr.street, per.addr.number, per.addr.city);
                else
                    printf("Server result: Name not found\n");
                send_struct(client_sock, &per);
                send_result(client_sock, result);
                break;
            case 2: //code correspondent to sum by value
                recv_sum(client_sock, &a, &b);
                perform_call(request, &c, 2, a, b);
                send_result(client_sock, c);
                break;
            case 3: //code correspondent to sum by reference
                recv_sumr(client_sock, &a, &b, &c);
                perform_call(request, 0, 3, &a, &b, &c);
                send_result(client_sock, a);
                send_result(client_sock, b);
                send_result(client_sock, c);
                break;
                
        }
        free(s);
    }
    
    close(socket_desc);
    return 0;
}
/* ################################################# */

/*
    AUXILIAR FUNCTIONS (IMPLEMENTATION) TO CONTROL SENDING AND RECEIVING OF INFORMATION
                                                                            */

int recv_message (int client_sock) {    
    int service_to_perform;

    int status = read (client_sock, &service_to_perform, sizeof(int));
    
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

    if (statusN == 0 || statusS == 0)
        puts("Client disconnected");

    else if (statusN == -1 || statusS == -1)
        perror("Read of string failed from server side");
}

void send_result (int client_sock, int value) {
    printf("Server result: %d\n", value);

    if (send(client_sock, &value, sizeof(value), 0) < 0) {
        puts ("Send of Int failed!");
        exit(1);
    }
}

void send_struct (int client_sock, struct person * p) {
    if (write (client_sock, p, sizeof(struct person)) < 0) {
        puts ("Send of Struct failed!");
        exit(1);
    }
}