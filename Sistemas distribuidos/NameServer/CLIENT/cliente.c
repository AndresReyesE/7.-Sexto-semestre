
#include "cliente.h"

int sock;
char * host;
// void message(int sock);

int main(int argc, char *argv[])
{
    char * m;

    if (argc != 3) {
        fprintf(stderr, "Usage: %s <host> <message>\n", argv[0]);
        exit(1);
    }

    host = argv[1];
    m = argv[2];

    sock = connection();
    int result = store (m);
    printf("Resultado recibido %d\n", result);

    printf("El mensaje fue almacenado\n");
    close(sock);

    return 1;
}

// void message(int sock)
// {
//     char message[1000];
//     char server_reply[2000];
    
//     while(1) {
//         printf("Enter message: ");
//         scanf("%s", message);
        
//         // send some data
//         if( send(sock, message, strlen(message), 0) < 0) {
//             puts("Send failed");
//             exit(1);
//         }
        
//         // receive a reply from the server
//         if( recv(sock, server_reply, 2000, 0) < 0) {
//             puts("recv failed");
//             break;
//         }
        
//         puts("Server reply:");
//         puts(server_reply);
//     }
// }
