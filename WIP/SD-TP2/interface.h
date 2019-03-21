/**
 * This file works as an interface for both process at the same time, 
 * client and server, both have to implement the services here declared
 * exactly the way here are declared.
 * This includes type of return, name of the function and type of parameters.
 * 
 * These functions will be called in order as follows:
 * 1. Client will locally invoke any of these functions (defined locally in adapter_client)
 * 2. Client's stub (adapter_client) will control and send these parameters in an specific order
 * 3. Parameters and information of the procedure to be performed will be delivered to the server's skeleton (adapter_svc); its return values and references are returned to the client
 * 4. Skeleton will receive these parameters in the same specific order and store them locally
 * 5. Once received the parameters, skeleton will call the server indicating what it has to do; its return values and references are returned to the client's stub
 * 6. Server will receive skeleton's instructions and will perform the corresponding call; its return values and references are returned to the skeleton 
 * 7. The service (defined in service file) will receive the call and perform it, its return values and references are returned to the server
 */


int sum (int a, int b);

void sumr (int * a, int * b, int * c);

struct address {
    char street[50];
    int number;
    char city[30];
};

struct person {
    char name[30];
    int age;
    struct address addr;
};

int search_data (struct person * p, char * name);