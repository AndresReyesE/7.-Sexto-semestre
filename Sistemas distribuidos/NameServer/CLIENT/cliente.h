#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <sys/socket.h>

#define PORTMAPPER 1111
// #define LOCAL_HOST "127.0.0.1"

int connection(int port);
int close(int sock);

int store (char * m);
int find_service (int id);
