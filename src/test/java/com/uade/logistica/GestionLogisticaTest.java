package com.uade.logistica;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GestionLogisticaTest {
    @Test
    void prioridadPorUrgenciaOPesoConOrdenDeLlegadaEnEmpates() {
        CentroDistribucion centro = new CentroDistribucion();
        Paquete<?> estandar = new Paquete<>("A", 50, "Rosario", false, "Alimentos");
        Paquete<?> pesado = new Paquete<>("B", 51, "Rosario", false, 123);
        Paquete<?> urgente = new Paquete<>("C", 1, "Rosario", true, "Fragiles");
        centro.agregarPaquete(estandar);
        centro.agregarPaquete(pesado);
        centro.agregarPaquete(urgente);
        assertSame(pesado, centro.procesarSiguiente());
        assertSame(urgente, centro.procesarSiguiente());
        assertSame(estandar, centro.procesarSiguiente());
        assertNull(centro.procesarSiguiente());
    }

    @Test
    void deshacerYDescargarRespetanLifo() {
        Camion camion = new Camion();
        Paquete<?> primero = new Paquete<>("A", 20, "Rosario", false, "Notebook");
        Paquete<?> segundo = new Paquete<>("B", 30, "Cordoba", true, "Monitor");
        camion.cargarPaquete(primero);
        camion.cargarPaquete(segundo);
        assertSame(segundo, camion.deshacerCarga());
        assertEquals(1, camion.cantidadPaquetes());
        camion.cargarPaquete(segundo);
        assertSame(segundo, camion.descargarPaquete());
        assertSame(primero, camion.descargarPaquete());
        assertNull(camion.descargarPaquete());
        assertNull(camion.deshacerCarga());
    }

    @Test
    void inventarioCargaObjetosConConfiguracion() throws IOException {
        List<Paquete<?>> paquetes = JsonLoader.cargarInventario();
        assertEquals(3, paquetes.size());
        assertEquals("P002", paquetes.get(1).getId());
        assertEquals(65, paquetes.get(1).getPeso());
        assertTrue(paquetes.get(1).isUrgente());
    }

    @Test
    void rechazaPesosInvalidos() {
        for (double peso : new double[]{0, -1, Double.NaN, Double.POSITIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Paquete<>("A", peso, "Rosario", false, "Contenido"));
        }
    }
}
