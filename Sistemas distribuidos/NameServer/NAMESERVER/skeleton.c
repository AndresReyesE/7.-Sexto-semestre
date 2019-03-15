
#include "server.h"


int recv_int (int client_sock);
void send_integer (int client_sock, int value);


int main(int argc, char *argv[])
{
    int socket_desc = initialization();
    int id, port;

    while(1) {


        int client_sock = connection(socket_desc);
        // process(client_sock);
        int proc = recv_int(client_sock);

        switch (proc) {
            case 1:     //registrar un servidor
                id = recv_int (client_sock);
                port = recv_int(client_sock);
                printf("Suscription: %d, %d", id, port);
                
                register_service(id, port);
                break;

            case 2:     //buscar un 
                id = recv_int (client_sock);
                port = find_service (id);
                printf("Look up: %d, %d", id, port);

                send_integer(client_sock, port);
                break;
            
            default:
                puts("El servicio no existe");
        }
    }
    
    close(socket_desc);
    return 0;
}

int recv_int (int client_sock) {
    int read_size, value;

    read_size = recv(client_sock, &value, sizeof(value), 0);

    
    if(read_size == 0) {
        puts("Client disconnected");
        fflush(stdout);
    }
    else if(read_size == -1) {
        perror("recv failed");
    }

    return value;
}

void send_integer (int client_sock, int value) {
    printf("Server result: %d\n", value);

    if (send(client_sock, &value, sizeof(value), 0) < 0) {
        puts ("Send failed!");
        exit(1);
    }
}