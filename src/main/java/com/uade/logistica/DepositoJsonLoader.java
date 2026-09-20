package com.uade.logistica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepositoJsonLoader {

    public static List<Deposito> cargarDepositos() throws IOException {

        try (InputStream input = DepositoJsonLoader.class
                .getClassLoader()
                .getResourceAsStream("depositos.json")) {

            if (input == null) {
                throw new IOException("No se encontro depositos.json");
            }

            JsonNode raiz = new ObjectMapper().readTree(input);

            JsonNode depositos = raiz.get("depositos");

            if (depositos == null || !depositos.isArray()) {
                throw new IOException("El archivo debe contener un arreglo llamado depositos");
            }

            List<Deposito> resultado = new ArrayList<>();

            for (JsonNode nodo : depositos) {

                int id = nodo.get("id").asInt();
                String nombre = nodo.get("nombre").asText();
                boolean auditado = nodo.get("auditado").asBoolean();

                LocalDateTime fechaAuditoria =
                        auditado
                                ? LocalDateTime.now().minusDays(10)
                                : LocalDateTime.now().minusDays(40);

                resultado.add(
                        new Deposito(
                                id,
                                nombre,
                                auditado,
                                fechaAuditoria
                        )
                );
            }

            return resultado;
        }
    }
}
