#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

#define PORT_NUM 8888

//utils
int initialization();
int connection(int socket_desc);
int close(int sock);

//services
int store (char * m); 
