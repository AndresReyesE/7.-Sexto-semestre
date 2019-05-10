#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/*
32. El juego de dados conocido como “craps” (tiro perdedor) es muy popular, 
realice un programa que simule dicho juego, a continuación se muestran las reglas para los jugadores. 
• Un jugador tira dos dados. Cada dato tiene seis caras. Las caras contienen 1, 2, 3, 4, 5 y 6 puntos. 
• Una vez que los dados se hayan detenido, se calcula la suma de los puntos en las dos caras superiores. 
• Si a la primera tirada, la suma es 7, o bien 11, el jugador gana. 
• Si a la primera tirada la suma es 2, 3 o 12 (conocido como “craps”), el jugador pierde (es decir la casa “gana”). 
• Si a la primera tirada la suma es 4, 5, 6, 8, 9 ó 10, entonces dicha suma se convierte en el “punto” o en la “tirada”. 
• Para ganar, el jugador deberá continuar tirando los dados hasta que haga su “tirada”. 
• El jugador perderá si antes de hacer su tirada sale una tirada de 7.  
*/

int dado () {
    int random = (int) (rand () % 6) + 1; 
    return random;
}

/*
Desglose de Estado:
Victoria = 1
Derrota = -1
Juego continúa = 0
Puedes ganar si:
 - En el turno 1 consigues sacar 7 u 11
 - En cualquier otro turno sacas tu tirada
Puedes perder si:
 - En el turno 1 sacas 2, 3 o 12
 - En cualquier otro turno sacas 7
El juego continúa si:
 - En el turno 1 la suma no es 7, 11, 2, 3 ò 12
 - En cualquier otro turno no sacas ni tu tirada ni 7
*/
int verificar_estado (int turno, int dado1, int dado2, int tirada) {
    int suma = dado1 + dado2;
    int estado = 0;
    
    if (turno == 1) { // Si es el primer turno
        if (suma == 7 || suma == 11) //Victoria inmediata
            estado = 1;
        
        else if (suma == 2 || suma == 3 || suma == 12) //CRAPS = Derrota inmediata
            estado = -1;

        else //Juego continúa
            estado = 0;
    }

    else { //Turnos siguientes
        if (suma == 7)
            estado = -1;
        else if (suma == tirada)
            estado = 1;
        else
            estado = 0;
    }

    return estado;
}

/* 
Esta función se encarga únicamente de imprimir en pantalla el estado del juego:
El turno
El valor de tus dados
La tirada que necesitas
La tirada que conseguiste
Un letrero que te avisa si ganaste, perdiste o el juego sigue
*/
void imprimir_estado (int estado, int turno, int dado1, int dado2, int tirada) {
    printf("----------------------------------\n");
    printf("\tTurno %d\n\n", turno);
    printf("  Dado 1: %d\n", dado1);
    printf("  Dado 2: %d\n", dado2);
    printf("  Suma actual: %d\n\n", dado1 + dado2);
    printf(" > Tu tirada: %d\n\n", tirada);
    switch (estado) {
        case -1:
            printf(" Has perdido. Mas suerte a la proxima!");
            break;
        case 0:
            printf(" Sigue jugando!", tirada);
            break;
        case 1:
            printf(" Felicidades, has ganado!", tirada);
            break;
    }
    printf("\n----------------------------------\n\n");
}

int main (void) {
    srand(time (NULL)); //instrucción para generar números aleatorios con base en el tiempo actual

    int turno;
    int dado1, dado2;
    int tirada;
    int fin_de_juego;

    turno = 1;
    dado1 = dado();
    dado2 = dado();
    tirada = dado1 + dado2;

    fin_de_juego = verificar_estado(turno, dado1, dado2, tirada);

    while (fin_de_juego == 0) {
        imprimir_estado(fin_de_juego, turno, dado1, dado2, tirada);

        turno++;
        dado1 = dado();
        dado2 = dado();

        fin_de_juego = verificar_estado(turno, dado1, dado2, tirada);
    }
    
    imprimir_estado(fin_de_juego, turno, dado1, dado2, tirada);

    return 0;
}