
#include "server.h"


char * recv_message(int client_sock);
void send_result (int client_sock, int value);


int main(int argc, char *argv[])
{
    int socket_desc = initialization();
    
    while(1) {
        int client_sock = connection(socket_desc);
        // process(client_sock);
        char * m = recv_message(client_sock);

        int value = store(m);
        free(m);
        send_result(client_sock, value);
    }
    
    close(socket_desc);
    return 0;
}

char * recv_message (int client_sock)
{
    char * message;
    int read_size, length;

    read_size = recv(client_sock, &length, sizeof(length), 0);


    message = (char *) malloc(length);
    
    // receive a message from client
    read_size = recv(client_sock, message, length, 0);
        // send the message back to client
    
    if(read_size == 0) {
        puts("Client disconnected");
        fflush(stdout);
    }
    else if(read_size == -1) {
        perror("recv failed");
    }

    return message;
}

void send_result (int client_sock, int value) {
    printf("Server result: %d\n", value);

    if (send(client_sock, &value, sizeof(value), 0) < 0) {
        puts ("Send failed!");
        exit(1);
    }
}