#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <sys/socket.h>

#define PORT_NUM 8888
// #define LOCAL_HOST "127.0.0.1"

int connection();
int close(int sock);

int store (char * m);