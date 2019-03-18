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

void struct_function (struct person * p);

int search_data (struct person * p, char * name);