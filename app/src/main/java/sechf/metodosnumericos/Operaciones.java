package sechf.metodosnumericos;

import java.util.ArrayList;

/**
 * Created by Erick Chávez on 13/05/2017.
 */

public class Operaciones {
    public static Matriz gauss(Matriz a) {
        Matriz matriz = a;
        Matriz mat = a;
        float valor;
        ArrayList<Float> valores;
        String texto;
        int fila;

        // vvv DEBUG
        System.out.println("INICIO: " + matriz.getDatos().toString());
        // ^^^ DEBUG
        texto = "Matriz inicial:";
        GaussResult.agregarTexto(texto);
        GaussResult.agregarMatriz(matriz.copy());

        // TEST
        ArrayList<Float> temp;
        int pos = 1;
        try {
            while (mat.getDatos().get(0).get(0) == 0) {
                temp = mat.getDatos().get(0);
                mat.getDatos().set(0, mat.getDatos().get(pos));
                mat.getDatos().set(pos, temp);
                pos++;
            }
        } catch (IndexOutOfBoundsException e) {
            GaussResult.resetSteps();
            GaussResult.agregarTexto("LA MATRIZ INGRESADA ES INVALIDA");
            System.out.println("Error :: " + e.getMessage());
            return null;
        }
        // TEST

        if (mat.getDatos().get(0).get(0) != 1f) {
            valores = mat.getDatos().get(0);
            for (int i = valores.size() - 1; i >= 0; i--) {
                valor = valores.get(i) / mat.getDatos().get(0).get(0);
                valores.set(i, valor);
            }
            // vvv DEBUG
            System.out.println("STEP 1: " + matriz.getDatos().toString());
            // ^^^ DEBUG
            fila = 1;
            texto = "Se divide la fila " + fila + " entre el valor en la\n" +
                    "posición [" + fila + "] de ésta misma.\n" +
                    "(La F" + fila + " se divide entre F" + fila + "[" + fila + "])";
            GaussResult.agregarTexto(texto);
            GaussResult.agregarMatriz(matriz.copy());

        }

        // vvv DEBUG
//        System.out.println("STEP: " + matriz.getDatos().toString());
        // ^^^ DEBUG

        float pivote = 0;
        for (int i = 1; i < mat.getDatos().size(); i++) {
            for (int j = i; j < mat.getDatos().size(); j++) {
                valores = mat.getDatos().get(j);
                pivote = valores.get(i - 1);

                // TEST
                try {
                    while (mat.getDatos().get(i).get(i) == 0) {
                        temp = mat.getDatos().get(i);
                        mat.getDatos().set(i, mat.getDatos().get(j + 1));
                        mat.getDatos().set(j + 1, temp);
                        pos++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    GaussResult.resetSteps();
                    GaussResult.agregarTexto("LA MATRIZ INGRESADA ES INVALIDA");
                    System.out.println("Error :: " + e.getMessage());
                    return null;
                }
                // TEST

//                System.out.println("Pivote: " + pivote);
                for (int k = i - 1; k < valores.size(); k++) {
                    valor = valores.get(k) - (pivote * mat.getDatos().get(i - 1).get(k));
                    valores.set(k, valor);
                    // vvv DEBUG
//                    System.out.println("STEP 2: " + matriz.getDatos().toString());
//                    System.out.println("Valor: " + mat.getDatos().get(i - 1).get(k));
                    // ^^^ DEBUG

                }
                // vvv DEBUG
                System.out.println("STEP 2: " + matriz.getDatos().toString());
                // ^^^ DEBUG
                fila = j + 1;
                texto = "Se le resta a cada valor de la fila " + fila + " el resultado" +
                        "\nde la resta del valor en esta misma posición menos" +
                        "\nla multiplicación del valor en la posición " + (fila - 1) + "por" +
                        "\nel valor en esta misma posición de la fila anterior." +
                        "\n(F" + fila + "[x] - (F" + fila + "[" + (fila - 1) + "] * F" + (fila - 1) + "[x]) )";
                GaussResult.agregarTexto(texto);
                GaussResult.agregarMatriz(matriz.copy());
            }

            valores = mat.getDatos().get(i);
            for (int j = valores.size() - 1; j >= 0; j--) {
                valor = valores.get(j) / mat.getDatos().get(i).get(i);
                valores.set(j, valor);
            }

            // vvv DEBUG
            System.out.println("STEP 3: " + matriz.getDatos().toString());
            // ^^^ DEBUG
            fila = i + 1;
            texto = "Se divide la fila " + fila + " entre el valor en la\n" +
                    "posición [" + fila + "] de ésta misma.\n" +
                    "(La F" + fila + " se divide entre F" + fila + "[" + fila + "])";

            GaussResult.agregarTexto(texto);
            GaussResult.agregarMatriz(matriz.copy());
        }

        // vvv DEBUG
//        System.out.println("Step: " + matriz.getDatos().toString());
        // ^^^ DEBUG


//        for (int i = 1; i < mat.getDatos().size(); i++) {
//            valores = mat.getDatos().get(i);
//            for (int j = valores.size() - 1; j >= 0; j--) {
//                valor = valores.get(j) / mat.getDatos().get(i).get(i);
//                valores.set(j, valor);
//            }
//        }

        // vvv DEBUG
        System.out.println("Final: " + matriz.getDatos().toString());
        // ^^^ DEBUG
        texto = "Matriz final:";
        GaussResult.agregarTexto(texto);
        GaussResult.agregarMatriz(matriz.copy());

        return matriz.copy();
    }

    public static Matriz gaussJordan(Matriz matriz) {
        Matriz salida = matriz;
        int col = 0;
        ArrayList<ArrayList<Float>> mat = matriz.getDatos();
        ArrayList<Float> temp;

        for (int y = 0; y < matriz.getDimensiones()[1]; y++) {
            for (int x = 0; x < matriz.getDimensiones()[1]; x++) {
                while (mat.get(y).get(col) == 0) {
                    temp = mat.get(y);
                    mat.set(y, mat.get(y + 1));
                    mat.set(y + 1, temp);
                }

                if (mat.get(y).get(col) != 1) {
                    ArrayList<Float> valores = mat.get(y);
                    float valor;
                    for (int i = valores.size() - 1; i >= 0; i--) {
                        valor = valores.get(i) / valores.get(col);
                        valores.set(i, valor);
                    }
                }

                if (x != col) {
                    float valor;
                    ArrayList<Float> valores = mat.get(y);
                    float pivote = mat.get(x).get(col);
                    for (int i = valores.size() - 1; i >= 0; i--) {
                        valor = (valores.get(i) * (-pivote) + mat.get(x).get(i));
                        mat.get(x).set(i, valor);
                    }
                }
            }
            col++;
        }
        System.out.println("Matriz FINAL: " + mat.toString());
        GaussResult.agregarTexto("El resultado de la matriz aplicando el método de" +
                "\nGauss-Jordan es: ");
        GaussResult.agregarMatriz(matriz);

        salida.setDatos(mat);
        return salida;
    }

    public static Matriz inversa(Matriz matriz, Matriz multiplicable) {
        Matriz inv = id(matriz);
        Matriz nueva = join(matriz, inv);
        System.out.println("Join: " + nueva.getDatos().toString());

        Matriz salida = Operaciones.gaussJordan(nueva);
        GaussResult.resetSteps();

        for (int x = 0; x < matriz.getDimensiones()[1]; x++) {
            for (int i = 0; i < matriz.getDimensiones()[0]; i++) {
                matriz.getDatos().get(x).set(i,
                        salida.getDatos().get(x).get(i + matriz.getDimensiones()[0]));
            }
        }

        GaussResult.agregarTexto("La matriz inversa de la matriz ingresada es:");
        GaussResult.agregarMatriz(matriz);

        Matriz resultadoMulti = multiplicar(matriz, multiplicable);

        GaussResult.agregarTexto("Al multiplicar el resultado de la inversa por la matriz:");
        GaussResult.agregarMatriz(multiplicable);
        GaussResult.agregarTexto("El resultado es:");
        GaussResult.agregarMatriz(resultadoMulti);
        return matriz;
    }

    public static Matriz join(Matriz matriz, Matriz inv) {
        Matriz nueva = new Matriz(matriz.getDimensiones()[0] * 2, matriz.getDimensiones()[1]);
        nueva.llenarVacia();

        for (int y = 0; y < nueva.getDimensiones()[1]; y++) {
            for (int x = 0; x < nueva.getDimensiones()[0] / 2; x++) {
                // Parte 1
                nueva.getDatos().get(y).set(x, matriz.getDatos().get(y).get(x));
                // Parte 2
                nueva.getDatos().get(y).set(x + nueva.getDimensiones()[0] / 2,
                        inv.getDatos().get(y).get(x));
            }
        }
        return nueva;
    }

    public static Matriz id(Matriz matriz) {
        Matriz copia = matriz.copy();

        for (int y = 0; y < matriz.getDimensiones()[1]; y++) {
            for (int x = 0; x < matriz.getDimensiones()[1]; x++) {
                if (x == y) {
                    copia.getDatos().get(y).set(x, 1f);
                } else {
                    copia.getDatos().get(y).set(x, 0f);
                }
            }
        }
        return copia;
    }

    public static Matriz multiplicar(Matriz m1, Matriz m2) {
        Matriz salida;
        float valor;
        float valor1;
        float valor2;
        if (m2.getDimensiones()[0] == 1) {
            salida = m2.copy();
            for (int y = 0; y < m1.getDimensiones()[1]; y++) {
                valor = 0;
                for (int x = 0; x < m1.getDimensiones()[0]; x++) {
                    valor1 = m1.getDatos().get(y).get(x);
                    valor2 = m2.getDatos().get(x).get(0);
                    valor += valor1 * valor2;
                }
                salida.getDatos().get(y).set(0, valor);
            }
            return salida;
        }

        return null;
    }
}
