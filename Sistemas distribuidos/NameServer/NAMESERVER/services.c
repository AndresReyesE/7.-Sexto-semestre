#include "server.h"

int entry = NIL;
LOCATION directory[TABLE_SIZE];

void register_service(int id, int port) {
    for (int i = 0; i < entry; i++) {
        if (directory[i].id == id) {
            directory[i].port = port;
            return;
        }

    }

    directory[entry].id = id;
    directory[entry].port = port;

    entry++;
}


int find_service(int id) {
    for (int i = 0; i < entry; i++)
        if (directory[i].id == id)
            return directory[i].port;
    
    return NIL;
}
