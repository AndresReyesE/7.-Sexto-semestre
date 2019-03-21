#include "cliente.h"

extern int sock;

int find_service (int id) {
    int read_size, port, proc = 2;

    int sock = connection(PORTMAPPER);

    send (sock, &proc, sizeof(proc), 0);

    if (send (sock, &id, sizeof(id), 0) < 0) {
        puts ("Send failed");
        exit(1);
    }

    read_size = recv (sock, &port, sizeof(port), 0);

    if (read_size == 0) {
        puts ("Client disconnected");
        fflush (stdout);
    }
    else if (read_size == -1)
        perror("recv failed");

    close (sock);
    return port;
}

int store (char * m) {
    int read_size, result;
    int length = strlen(m);

    send(sock, &length, sizeof(length), 0);

    if (send (sock, m, length, 0) < 0) {
        puts ("Send failed");
        exit(1);
    }

    read_size = recv (sock, &result, sizeof(result), 0);

    if (read_size == 0) {
        puts ("Client disconnected");
        fflush (stdout);
    }
    else if (read_size == -1)
        perror("recv failed");

    return result;
}
