#include <stdio.h>


void uno(int m[36][36], int n) {
    int a, b, c, d, e;

    c = n/2+1;
    a = 1;
    d = a;
    e = c;
    for (b = 1; b<=n*n; b++) {
        if (m[a][c] == 0) {
            m[a][c] = b;
        }
        else {
            a = d+1;
            c=e;
            m[a][c] = b;
        }
        d = a;
        e = c;
        a = a - 1;
        c = c - 1;
        if (a == 0)
            a = n;
        if (c == 0)
            c = n;
    }
}

void dos(int m[36][36], int n) {
    int a, b;
    for (a = 1; a <= n; a++) {
        for (b = 1; b <= n; b++) {
            printf("%3d", m[a][b]);
        }
        printf("\n");
    }
}


int main () {
    int m[36][36] = {0};
    int n;
    n = 5;
    uno(m,n);
    dos(m,n);
    return 0;
}