#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "interface.h"

int main (int argc, char **argv) {
    if(argc != 2) {
	    fprintf(stderr, "Usage: %s <\"name_searched\">\n", argv[0]);
	    exit(1);
	}
    
        struct person p;    //structure where the results (by reference) will be stored

    int found = search_data(&p, argv[1]); //search for the name given as parameter

    if (found) { 
        printf("Register found!\n");
        printf("Name:\t%s\n",   p.name);
        printf("Age:\t%d\n",    p.age);
        printf("Street:\t%s\n", p.addr.street);
        printf("Number:\t%d\n", p.addr.number);
        printf("City:\t%s\n",   p.addr.city);
    }   
    else
        printf("Name not found\n\n");

    return 0;
}

/**
 * Search for a name in a simple text-file-implemented database, if the name is in the database, it fills the structure received with the
 * data it has, otherwise it doesn't touch the structure
 * @params
 *  p - person structure pointer, reference to the structure where the correct values, if name found, should be stored
 *  name - trimmed string indicating the name to be searched  
 */
int search_data (struct person * p, char * name) {
    int found = 0;
    FILE * db = fopen("Server/db", "r");

    if (db == NULL) {
        puts ("Problem while reading the file, check out and try again");
        return -1;
    }
    
    char c;     //caracter para leer el archivo un caracter a la vez
    struct person temp;
    
    c = fgetc(db);
    
    while (c != EOF && found == 0) {
        ungetc(c, db);
        
        if (fgets(temp.name, 30, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else
            temp.name[strcspn(temp.name, "\r\n")] = 0;

        if (fscanf(db, "%d", &temp.age) != 1) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        
        fgetc(db);

        if (fgets(temp.addr.street, 50, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else
            temp.addr.street[strcspn(temp.addr.street, "\r\n")] = 0;

        if (fscanf(db, "%d", &temp.addr.number) != 1) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }

        fgetc(db);
        
        if (fgets(temp.addr.city, 30, db) == NULL) {
            printf("error in the contents of the file, data must be in the following order: \nName\nAge\nStreet\nNumber\nCity\n");
            break;
        }
        else
            temp.addr.city[strcspn(temp.addr.city, "\r\n")] = 0;

        found = strcmp(temp.name, name) == 0 ? 1 : 0;

        c = fgetc(db);
    }


    if (found) {
        strcpy(p->name, temp.name);
        p->age = temp.age;
        strcpy(p->addr.street, temp.addr.street);
        p->addr.number = temp.addr.number;
        strcpy(p->addr.city, temp.addr.city);
    }

    fclose(db);

    return found;
}