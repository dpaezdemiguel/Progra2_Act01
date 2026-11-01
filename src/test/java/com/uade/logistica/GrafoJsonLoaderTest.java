package com.uade.logistica;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GrafoJsonLoaderTest {

    private Grafo cargar(String json) throws IOException {
        byte[] datos = json.getBytes(StandardCharsets.UTF_8);
        List<Deposito> depositos = DepositoJsonLoader.cargarDepositos(new ByteArrayInputStream(datos));
        return GrafoJsonLoader.cargarGrafo(new ByteArrayInputStream(datos), depositos);
    }

    @Test
    void cargaDepositosYConexiones() throws IOException {
        Grafo grafo = cargar("""
                {"depositos": [
                  {"id": 50, "nombre": "Central", "auditado": true,
                   "conexiones": [{"id": 20, "distancia": 700}]},
                  {"id": 20, "nombre": "Cordoba", "auditado": false}
                ]}
                """);
        assertEquals(700.0, grafo.distanciaMinima(50, 20));
        assertEquals(Double.POSITIVE_INFINITY, grafo.distanciaMinima(20, 50));
    }

    @Test
    void rechazaConexionesInvalidas() {
        for (String conexion : List.of(
                "{\"id\": 99, \"distancia\": 10}",
                "{\"id\": 50, \"distancia\": -1}",
                "{\"id\": 50, \"distancia\": \"10\"}")) {
            String json = "{\"depositos\": [{\"id\": 50, \"nombre\": \"Central\","
                    + "\"auditado\": true, \"conexiones\": [" + conexion + "]}]}";
            assertThrows(IOException.class, () -> cargar(json));
        }
    }

    @Test
    void integraRutasConSistema() throws IOException {
        Sistema sistema = new Sistema();
        sistema.cargarDepositos();
        assertEquals(700.0, sistema.distanciaMinima(50, 20));
        assertEquals(300.0, sistema.distanciaMinima(50, 80));
        assertEquals(1000.0, sistema.distanciaMinima(20, 80));
        assertEquals(Double.POSITIVE_INFINITY, sistema.distanciaMinima(50, 40));
    }
}
