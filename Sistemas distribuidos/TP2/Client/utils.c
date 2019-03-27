
#include "client.h"

extern char * host;

int connection() {
    int sock;
    struct sockaddr_in server;
    
    // create socket
    sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock == -1) {
        printf("Could not create socket");
    }
    puts("Socket created");
    
    server.sin_addr.s_addr = inet_addr(LOCAL_HOST);
    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);
    
    // connect to remote server
    if (connect(sock, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("connect failed. Error");
        exit(1);
    }
    
    puts("Connected\n");
    return sock;
}

int close (int sock) {
    return shutdown(sock, 2);
}