/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pgrprou7evaluablehundirflota;

import java.util.Scanner;

/**
 * Versión simplificada del popular juego hundir la flota. En este caso el
 * jugador dispara a una flota distribuida aleatoriamente por un tablero oculto
 * y el ordenador muestra tras cada disparo el tablero actualizado. El juego
 * consta de varios niveles de dificultad.
 *
 * @author <a href="mailto:pgrdaw@gmail.com">Pablo Gimeno Ramallo</a>
 */
public class PGRPROU7EvaluableHundirFlota {

    /**
     * Gerena una nueva matriz tablero de tamaño alto x ancho, le añade una fila
     * y una columna para encabezados y carga los valores iniciales: cabeceras
     * de filas y columnas y '-' en el resto.
     *
     * @param alto alto útil de la matriz
     * @param ancho ancho útil de la matriz
     * @return tablero reseteado
     */
    public static char[][] crearTablero(int alto, int ancho) {
        // crea matriz tablero y añade 1 fila y 1 columna para encabezados
        char[][] tablero = new char[alto + 1][ancho + 1];
        char col = '0';
        char fil = 'A';
        // carga los valores iniciales
        // 0 0
        tablero[0][0] = ' ';
        // col 0
        for (int i = 1; i < tablero.length; i++) {
            tablero[i][0] = fil;
            fil++;
        }
        // fila 0
        for (int j = 1; j < tablero[0].length; j++) {
            tablero[0][j] = col;
            col++;
        }
        // resto
        for (int i = 1; i < tablero.length; i++) {
            for (int j = 1; j < tablero[0].length; j++) {
                tablero[i][j] = '-';
            }
        }
        return tablero;
    }

    /**
     * Rellena aleatoriamente un tablero con una flota teniendo en cuenta que
     * dos barcos pueden tocarse pero no superponerse y que los barcos tienen
     * que estar integramente dentro del tablero
     *
     * @param tablero tablero en el que se quieren distribuir la flota
     * @param flota conjunto de barcos que hay que distribuir
     * @return tablero relleno de barcos
     */
    public static char[][] llenarTablero(char[][] tablero, char[] flota) {
        int barco = 0;   // número de barco en la flota
        int intento = 0; // número de intentos fallidos para evitar bucle infinito
        while (barco < flota.length && intento < flota.length * 3) {
            if (flota[barco] == 'P') { // Portaavions (P): ocupa 5 caselles verticals consecutives del tauler.
                int fil = (int) (1 + Math.random() * (tablero.length - 5));
                int col = (int) (1 + Math.random() * (tablero[fil].length - 1));
                if (tablero[fil][col] == '-'
                        && tablero[fil + 1][col] == '-'
                        && tablero[fil + 2][col] == '-'
                        && tablero[fil + 3][col] == '-'
                        && tablero[fil + 4][col] == '-') { // comprobamos que hay hueco vacio para el barco
                    tablero[fil][col] = flota[barco]; // la rellenamos con el barco
                    tablero[fil + 1][col] = flota[barco];
                    tablero[fil + 2][col] = flota[barco];
                    tablero[fil + 3][col] = flota[barco];
                    tablero[fil + 4][col] = flota[barco];
                    barco++;
                } else {
                    intento++;
                }
            }
            if (flota[barco] == 'Z') { // Cuirassat (Z): ocupa 4 caselles horitzontals consecutives del tauler.
                int fil = (int) (1 + Math.random() * (tablero.length - 1));
                int col = (int) (1 + Math.random() * (tablero[fil].length - 4));
                if (tablero[fil][col] == '-'
                        && tablero[fil][col + 1] == '-'
                        && tablero[fil][col + 2] == '-'
                        && tablero[fil][col + 3] == '-') {
                    tablero[fil][col] = flota[barco];// la rellenamos con el barco
                    tablero[fil][col + 1] = flota[barco];
                    tablero[fil][col + 2] = flota[barco];
                    tablero[fil][col + 3] = flota[barco];
                    barco++;
                } else {
                    intento++;
                }
            }
            if (flota[barco] == 'B') { // Vaixell (B): ocupa 3 caselles horitzontals consecutives del tauler.
                int fil = (int) (1 + Math.random() * (tablero.length - 1));
                int col = (int) (1 + Math.random() * (tablero[fil].length - 3));
                if (tablero[fil][col] == '-'
                        && tablero[fil][col + 1] == '-'
                        && tablero[fil][col + 2] == '-') {
                    tablero[fil][col] = flota[barco];
                    tablero[fil][col + 1] = flota[barco];
                    tablero[fil][col + 2] = flota[barco];
                    barco++;
                } else {
                    intento++;
                }
            }
            if (flota[barco] == 'L') { // Llanxa (L): ocupa una casella del tauler.
                int fil = (int) (1 + Math.random() * (tablero.length - 1));
                int col = (int) (1 + Math.random() * (tablero[fil].length - 1));
                if (tablero[fil][col] == '-') {
                    tablero[fil][col] = flota[barco];
                    barco++;
                } else {
                    intento++;
                }
            }
        }
        return tablero;
    }

    /**
     * Muestra en pantalla el argumento tablero formateado de tipo %2c
     *
     * @param tablero matriz de chars
     */
    public static void mostrarTablero(char[][] tablero) {
        System.out.println("");
        for (int fil = 0; fil < tablero.length; fil++) {
            for (int col = 0; col < tablero[fil].length; col++) {
                System.out.printf("%2c", tablero[fil][col]);
            }
            System.out.println("");
        }
    }

    /**
     * Crea array flota con el número y tipo de barcos definidos por nivel
     *
     * @param nivel nivel de juego seleccionado por el jugador
     * @return array flota conteniendo el número y tipos de barcos
     * correspondientes
     */
    public static char[] crearFlota(int nivel) {
        char[] flota;
        Scanner entrada = new Scanner(System.in);
        switch (nivel) {
            case 0:     // nivel para pruebas
                flota = new char[]{'L', 'L', 'L', 'L', 'L'};
                break;
            case 1:     // nivel fácil
                flota = new char[]{'P', 'Z', 'B', 'B', 'B', 'L', 'L', 'L', 'L', 'L'};
                break;
            case 2:     // nivel medio
                flota = new char[]{'P', 'Z', 'B', 'L', 'L'};
                break;
            case 3:     // nivel difícil
                flota = new char[]{'B', 'L'};
                break;
            case 9:     // nivel personalizado   
                System.out.print("Introduce número de portaaviones: ");
                int P = entrada.nextInt();
                System.out.print("Introduce número de acorazados: ");
                int Z = entrada.nextInt();
                System.out.print("Introduce número de fragatas: ");
                int B = entrada.nextInt();
                System.out.print("Introduce número de lanchas: ");
                int L = entrada.nextInt();
                flota = new char[P + Z + B + L];
                for (int i = 0; i < P; i++) {
                    flota[i] = 'P';
                }
                for (int i = P; i < P + Z; i++) {
                    flota[i] = 'Z';
                }
                for (int i = P + Z; i < P + Z + B; i++) {
                    flota[i] = 'B';
                }
                for (int i = P + Z + B; i < P + Z + B + L; i++) {
                    flota[i] = 'L';
                }
                break;
            default:    // nivel default
                flota = new char[]{'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L', 'L'};
        }
        return flota;
    }

    /**
     * Muestra en pantalla la entradilla
     */
    public static void entradilla() {
        System.out.println("");
        System.out.println("*********************");
        System.out.println("*                   *");
        System.out.println("*  HUNDIR LA FLOTA  *");
        System.out.println("*  by Pablo Gimeno  *");
        System.out.println("*                   *");
        System.out.println("*********************");
        pausa(500);

    }

    /**
     * pausa el programa durante ms milisegundos
     *
     * @param ms milisegundos de pausa
     */
    public static void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /**
     * pregunta al usuario el nivel de dificultad y define el escenario
     *
     * @return int[] {nivel, alto, ancho, intentos}
     */
    public static int[] escenario() {
        int escenario[] = new int[4];
        Scanner entrada = new Scanner(System.in);
        System.out.println("");
        System.out.println("****** NIVELES ******");
        System.out.println("* 1 - fácil         *");
        System.out.println("* 2 - medio         *");
        System.out.println("* 3 - difícil       *");
        System.out.println("* 9 - personalizado *");
        System.out.println("*********************");
        System.out.println("");
        pausa(500);
        System.out.print("Seleccionar nivel de dificultad: ");
        try {
            escenario[0] = entrada.nextInt();
            switch (escenario[0]) {
                case 0:
                    escenario[1] = 5;  // alto del tablero
                    escenario[2] = 5;  // ancho del tablero
                    escenario[3] = 10;  // munición
                    break;

                case 1:
                    escenario[1] = 10;  // alto del tablero
                    escenario[2] = 10;  // ancho del tablero
                    escenario[3] = 50;  // munición
                    break;
                case 2:
                    escenario[1] = 10;
                    escenario[2] = 10;
                    escenario[3] = 30;
                    break;
                case 3:
                    escenario[1] = 10;
                    escenario[2] = 10;
                    escenario[3] = 10;
                    break;
                case 9:
                    System.out.print("Introduce alto del tablero: ");
                    escenario[1] = entrada.nextInt();
                    System.out.print("Introduce ancho del tablero: ");
                    escenario[2] = entrada.nextInt();
                    System.out.print("Introduce cantidad de munición: ");
                    escenario[3] = entrada.nextInt();
                    break;
                default:
                    escenario[1] = 10;
                    escenario[2] = 10;
                    escenario[3] = 50;
            }
        } catch (Exception e) {
            System.out.println("");
            System.out.println("¡¡ Nivel no válido. Seleccionado el nivel por defecto !!");
            escenario = new int[]{0, 10, 10, 50};
        }
        return escenario;
    }

    /**
     * Pregunta el disparo y modifica el tableroVisible en función de lo que
     * contenga el tableroOculto en esa casilla
     *
     * @param tableroOculto matriz con la posición de todos los barcos
     * @param tableroVisible matriz con la información conocida
     * @return tableroVisible actualizado una vez efectuado el disparo
     */
    public static char[][] disparo(char[][] tableroOculto, char[][] tableroVisible) {
        int fil, col;
        String disparo;
        Scanner entrada = new Scanner(System.in);
        // System.out.print("Disparo: ");
        disparo = entrada.next();
        fil = disparo.charAt(0) - 'A' + 1;
        col = disparo.charAt(1) - '0' + 1;
        pausa(500);
        //System.out.println("fila: " + fil);
        //System.out.println("columna " + col);
        try {
            switch (tableroOculto[fil][col]) {
                case '-':
                case 'A':
                    System.out.println("");
                    System.out.println("      ************");
                    System.out.println("      *   AGUA   *");
                    System.out.println("      ************");
                    tableroVisible[fil][col] = 'A';
                    pausa(500);
                    break;
                default:
                    System.out.println("");
                    System.out.println("      ************");
                    System.out.println("      *  TOCADO  *");
                    System.out.println("      ************");
                    tableroVisible[fil][col] = 'X';
                    pausa(500);

            }
        } catch (Exception e) {
            System.out.println("");
            System.out.println("Disparo fuera de los límites del tablero.");
            System.out.println("Prueba algo como: "
                    + (char) ('A' + (Math.random() * (tableroVisible.length - 1)))
                    + (char) ('0' + (Math.random() * (tableroVisible[0].length - 1))));
            pausa(1000);
        }

        return tableroVisible;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // declaración de variables
        int municion = 0, objetivos = 0, tocados = 0;

        // comienzo del programa
        entradilla();       // mostrar la entradilla
        int escenario[] = escenario();  // elegir escenario
        char[] flota = crearFlota(escenario[0]);   // crear la flota acorde al nivel
        char[][] tableroVisible = crearTablero(escenario[1], escenario[2]);    // crear tablero visible
        char[][] tableroOculto = crearTablero(escenario[1], escenario[2]);     // crear tablero oculto
        llenarTablero(tableroOculto, flota);          // llenar tablero oculto con flota
        // contar cuantas posiciones están ocupadas por barcos
        for (int fil = 1; fil < tableroOculto.length; fil++) {
            for (int col = 1; col < tableroOculto[0].length; col++) {
                if (tableroOculto[fil][col] != '-') {
                    objetivos++;
                }
            }
        }
        municion = escenario[3];
        // mostrat tablero, tocados y munición y preguntar disparo mientras haya posibilidades de ganar
        while ((municion >= objetivos - tocados) && (objetivos > tocados)) {
            mostrarTablero(tableroVisible);
            System.out.println("");
            System.out.println("Tocados  " + tocados + " / " + objetivos);
            System.out.println("Municion " + municion + " / " + escenario[3]);
            System.out.print("Disparo: ");
            tableroVisible = disparo(tableroOculto, tableroVisible);
            municion--;
            tocados = 0;
            //recuento de tocados
            for (int fil = 0; fil < tableroVisible.length; fil++) {
                for (int col = 0; col < tableroVisible[0].length; col++) {
                    if (tableroVisible[fil][col] == 'X') {
                        tocados++;
                    }
                }
            }
        }
        if (municion < objetivos - tocados) {
            System.out.println("");
            System.out.println("*************** GAME OVER ****************");
            System.out.println("*                                        *");
            System.out.println("*     No tienes suficiente munición      *");
            System.out.println("*     para hundir la flota restante      *");
            System.out.println("*                                        *");
            System.out.println("******************************************");
            pausa(1000);
        } else {
            System.out.println("");
            System.out.println("********* YOU WIN *********");
            System.out.println("*                         *");
            System.out.println("*       Enhorabuena       *");
            System.out.println("*       Has ganado!       *");
            System.out.println("*                         *");
            System.out.println("***************************");
            pausa(1000);
        }
        mostrarTablero(tableroOculto);
        System.out.println("");
    }
}
