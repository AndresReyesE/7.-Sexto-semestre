#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

#define NIL 0
#define PORT_NUM 1111
#define TABLE_SIZE 10

typedef struct {
    int id;
    int port;
} LOCATION;

//utils
int initialization();
int connection(int socket_desc);
int close(int sock);

//services
void register_service(int id, int port);
int find_service(int id);

