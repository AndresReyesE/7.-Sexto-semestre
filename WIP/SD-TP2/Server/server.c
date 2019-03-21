/**
 * As written by (Tanembaum et al. 2007), the server only should be in charge of "doing the work and return the result to the server's stub",
 * so this server can offer multiple services, two of them are just examples of "other services the server can offer".
 * In few words, the server receive the unpackaged instructions from the skeleton and perform the indicated job with the values it received. 
 */

#include <stdarg.h>

#include "../interface.h"

/**
 * Manage of the calls required by calling the specified function with the specified parameters by the skeleton
 * 
 * @params
 *  id_request - integer indicating the service to be performed
 *  result - pointer to the any-kind object where the result of the function will be stored
 *  n - number of the 'auxiliar' params that will be received by the function of the service to be performed (i.e., 1 for search_data, 2 for sum, 3 for sumr)
 *  After n it can be included n number of params in the order the objective function have to receive them 
 * @return
 *  Any non-zero number to indicate the function was succesfully performed
 */
int perform_call (int id_request, void * result, int n, ...);

int perform_call (int id_request, void * result, int n,  ...) {
    //Variables used for initialize the variable params
    va_list v;

    va_start (v, n);

//auxiliar variables where the reference values can be stored
    int * aa;
    int * bb;
    int * cc;
    int res = 0;

    switch (id_request) {
        case 1: //call to the actual search data (defined in service.c) with the structure received as parameter to be used as return result and the string send as first and only auxiliar variable
            res = search_data ((struct person *) result, va_arg(v, char *)); 
            break;

        case 2: //call to the sum (by value)
            *(int *) result = sum(va_arg(v, int), va_arg(v, int));
            res = 1;
            break;

        case 3: //call to the sum (by reference)
            aa = va_arg(v, int *);
            bb = va_arg(v, int *);
            cc = va_arg(v, int *);
            sumr (aa, bb, cc);
            res = 1;
            break;

        default:
            res = -1;
            break;
    }

    va_end(v);
    return res;
}