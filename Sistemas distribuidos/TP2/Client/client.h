#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>


#define LOCAL_HOST "127.0.0.1"
#define PORT 8888

int connection();
int close(int sock);