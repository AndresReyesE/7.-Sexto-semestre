
#include "server.h"

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>

void process(int client_sock);

int main(int argc, char *argv[])
{
    int socket_desc = initialization();
    
    while(1) {
        int client_sock = connection(socket_desc);
        process(client_sock);
    }
    
    close(socket_desc);
    return 0;
}

void process(int client_sock)
{
    int read_size;
    char client_message[2000];
    
    // receive a message from client
    while((read_size = recv(client_sock,
                            client_message, 2000, 0)) > 0)
    {
        // send the message back to client
        write( client_sock,
               client_message, strlen(client_message) );
    }
    
    if(read_size == 0) {
        puts("Client disconnected");
        fflush(stdout);
    }
    else if(read_size == -1) {
        perror("recv failed");
    }
}
