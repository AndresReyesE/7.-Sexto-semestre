#include "client.h"
#include "../interface.h"

int sock;

int main (int argc, char **argv) {
    sock = connection();

    //int result = store(m);
    int a = atoi(argv[1]);
    int b = atoi(argv[2]);


    // int result = sum (a, b);
    int result;
    sumr (&a, &b, &result);

    printf("Resultado recibido %d\n", result);

    printf("El mensaje fue almacenado\n");
    close(sock);

    return 1;
}
