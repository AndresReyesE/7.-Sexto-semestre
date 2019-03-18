#include "client.h"
#include "../interface.h"

int sock;

int main (int argc, char **argv) {
    sock = connection();

    //int result = store(m);
    // int a = atoi(argv[1]);
    // int b = atoi(argv[2]);


    // int result = sum (a, b);
    // int result;
    // sumr (&a, &b, &result);
    // printf("Resultado recibido %d\n", result);

    struct person p;

    // struct_function (&p);
    search_data(&p, argv[1]);

    printf("Result received: %s - %d [%s %d, %s]\n", p.name, p.age, p.addr.street, p.addr.number, p.addr.city);

    close(sock);

    return 1;
}
