package com.uade.logistica;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GrafoTest {

    private Grafo crearGrafo() {
        Grafo grafo = new Grafo();
        for (int id = 1; id <= 4; id++) {
            grafo.agregarDeposito(new Deposito(id, "Deposito " + id, false, null));
        }
        grafo.agregarConexion(1, 2, 100);
        grafo.agregarConexion(1, 3, 20);
        grafo.agregarConexion(3, 2, 30);
        return grafo;
    }

    @Test
    void eligeCaminoIndirectoMasCorto() {
        assertEquals(50.0, crearGrafo().distanciaMinima(1, 2));
    }

    @Test
    void mismoDepositoTieneDistanciaCero() {
        assertEquals(0.0, crearGrafo().distanciaMinima(1, 1));
    }

    @Test
    void detectaDestinoInalcanzableYRespetaSentido() {
        Grafo grafo = crearGrafo();
        assertEquals(Double.POSITIVE_INFINITY, grafo.distanciaMinima(1, 4));
        assertEquals(Double.POSITIVE_INFINITY, grafo.distanciaMinima(2, 1));
    }

    @Test
    void rechazaDepositosInexistentesYDistanciasInvalidas() {
        Grafo grafo = crearGrafo();
        assertThrows(IllegalArgumentException.class, () -> grafo.distanciaMinima(9, 1));
        assertThrows(IllegalArgumentException.class, () -> grafo.distanciaMinima(1, 9));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarConexion(1, 9, 10));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarConexion(1, 2, -1));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarConexion(1, 2, Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarConexion(1, 2, Double.POSITIVE_INFINITY));
    }

    @Test
    void permiteDistanciaCeroYCiclos() {
        Grafo grafo = crearGrafo();
        grafo.agregarConexion(3, 1, 0);
        grafo.agregarConexion(1, 3, 0);
        assertEquals(30.0, grafo.distanciaMinima(1, 2));
    }
}
