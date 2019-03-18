#include <stdarg.h>

#include "../interface.h"

void perform_call (int id_request, void * result, int n, ...);

void perform_call (int id_request, void * result, int n,  ...) {
    va_list v;

    va_start (v, n);

            int * aa;
            int * bb;
            int * cc;
    switch (id_request)
    {
        case 2:
            *(int *) result = sum(va_arg(v, int), va_arg(v, int));
            break;

        case 3:
            aa = va_arg(v, int *);
            bb = va_arg(v, int *);
            cc = va_arg(v, int *);
            sumr (aa, bb, cc);
            break;

        case 4:
            struct_function((struct person *) result);

        case 5:
            search_data ((struct person *) result, va_arg(v, char *));
        default:
            break;
    }

    va_end(v);
}