#include "server.h"
#include "../interface.h"


extern int recv_message(int client_sock);
extern void send_result (int client_sock, int value);
extern int recv_sum (int client_sock);
extern void recv_sumr (int client_sock, int * a, int * b, int * c);

int main(int argc, char *argv[]) {
    int socket_desc = initialization();
    
    while(1) {
        int client_sock = connection(socket_desc);
        // process(client_sock);
        int request = recv_message(client_sock);

        int result;
        int a, b, c;
        switch (request) {
            case 2:
                result = recv_sum(client_sock);
                send_result(client_sock, result);
                break;
            case 3:
                recv_sumr(client_sock, &a, &b, &c);
                sumr(&a, &b, &c);
                send_result(client_sock, a);
                send_result(client_sock, b);
                send_result(client_sock, c);
                break;

        }
    }
    
    close(socket_desc);
    return 0;
}
