package com.uade.logistica;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DepositoJsonLoaderTest {
    private List<Deposito> cargar(String json) throws IOException {
        return DepositoJsonLoader.cargarDepositos(
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void cargaSoloPrincipalesIgnorandoConexiones() throws IOException {
        List<Deposito> depositos = cargar("""
                {"depositos":[
                  {"id":50,"nombre":"Central","auditado":true,
                   "conexiones":[{"id":80,"distancia":300}]},
                  {"id":20,"nombre":"Cordoba","auditado":false,"conexiones":[{}]}
                ]}
                """);
        assertEquals(List.of(50, 20), depositos.stream().map(Deposito::getId).toList());
        assertNotNull(depositos.get(0).getFechaUltimaAuditoria());
        assertNull(depositos.get(1).getFechaUltimaAuditoria());
        ArbolBinario<Deposito> arbol = new ArbolBinario<>();
        depositos.forEach(arbol::insert);
        arbol.runAudit();
        assertFalse(depositos.get(0).isVisitado());
        assertTrue(depositos.get(1).isVisitado());
    }

    @Test
    void respetaFechaExplicita() throws IOException {
        Deposito deposito = cargar("""
                {"depositos":[{"id":1,"nombre":"Central","auditado":true,
                "fechaUltimaAuditoria":"2020-01-01T12:00:00"}]}
                """).get(0);
        assertEquals(LocalDateTime.of(2020, 1, 1, 12, 0), deposito.getFechaUltimaAuditoria());
        ArbolBinario<Deposito> arbol = new ArbolBinario<>();
        arbol.insert(deposito);
        arbol.runAudit();
        assertTrue(deposito.isVisitado());
    }

    @Test
    void rechazaDocumentosInvalidosYDuplicados() {
        for (String json : List.of("", "null", "{}", "{\"depositos\":[{}]}",
                """
                {"depositos":[{"id":1,"nombre":"A","auditado":false},
                {"id":1,"nombre":"B","auditado":true}]}
                """,
                """
                {"depositos":[{"id":1.5,"nombre":"A","auditado":false}]}
                """)) {
            assertThrows(IOException.class, () -> cargar(json));
        }
    }
}
