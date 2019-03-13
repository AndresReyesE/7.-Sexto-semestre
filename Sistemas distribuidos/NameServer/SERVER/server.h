#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

#define PORTMAPPER 1111
#define SERVICE_PORT 4444

//utils
int initialization(); //inicialización del servicio
int connection(int socket_desc); //esperar a los clientes
int close(int sock);
int portmapper(char * host); //conexión con servidor de nombres

//services
int store (char * m); 
