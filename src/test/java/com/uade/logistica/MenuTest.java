package com.uade.logistica;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MenuTest {
    @Test
    void ejecutaOpcionesRestauradasYDepositos() {
        PrintStream anterior = System.out;
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try (PrintStream captura = new PrintStream(salida);
             Scanner entrada = new Scanner("3\n4\n3\n5\n6\n8\n7\n8\n9\n0\n9\n1\n9\n-1\n9\nabc\n0\n")) {
            System.setOut(captura);
            new Menu(entrada).ejecutar();
        } finally {
            System.setOut(anterior);
        }
        String texto = salida.toString();
        assertTrue(texto.contains("Cargado:"));
        assertTrue(texto.contains("Carga deshecha; devuelto al centro:"));
        assertTrue(texto.contains("Descargado:"));
        assertTrue(texto.contains("Pendientes:"));
        assertTrue(texto.contains("No hay depositos cargados."));
        assertTrue(texto.contains("Depositos cargados: 2"));
        assertTrue(texto.contains("Hub Central Buenos Aires (50) - Auditoria reciente"));
        assertTrue(texto.contains("Deposito Cordoba (20) - Visitado: requiere auditoria"));
        assertTrue(texto.contains("Nivel 0 (raiz = 0):"));
        assertTrue(texto.contains("Nivel 1 (raiz = 0):"));
        assertTrue(texto.contains("Ingrese un nivel entero no negativo."));
    }
}
