#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>



#define PORT 8888

//utils
int initialization(); //inicialización del servicio
int connection(int socket_desc); //esperar a los clientes
int close(int sock);

FILE * db;
