#include <stdarg.h>

#include "../interface.h"

int perform_call (int id_request, void * result, int n, ...);

int perform_call (int id_request, void * result, int n,  ...) {
    va_list v;

    va_start (v, n);

    int * aa;
    int * bb;
    int * cc;
    int found;
    switch (id_request) {
        case 1:
            found = search_data ((struct person *) result, va_arg(v, char *)); 
            return found;

        case 2:
            *(int *) result = sum(va_arg(v, int), va_arg(v, int));
            break;

        case 3:
            aa = va_arg(v, int *);
            bb = va_arg(v, int *);
            cc = va_arg(v, int *);
            sumr (aa, bb, cc);
            break;

        default:
            break;
    }

    va_end(v);
    return 0;
}