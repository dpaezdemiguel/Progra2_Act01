package com.uade.logistica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepositoJsonLoader {

    public static List<Deposito> cargarDepositos() throws IOException {
        try (InputStream input = DepositoJsonLoader.class.getClassLoader()
                .getResourceAsStream("depositos.json")) {
            if (input == null) throw new IOException("No se encontro depositos.json");
            return cargarDepositos(input);
        }
    }

    public static List<Deposito> cargarDepositos(InputStream input) throws IOException {
        JsonNode raiz = new ObjectMapper().readTree(input);
        JsonNode depositos = raiz == null ? null : raiz.get("depositos");
        if (depositos == null || !depositos.isArray()) {
            throw new IOException("El archivo debe contener un arreglo llamado depositos");
        }
        List<Deposito> resultado = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        LocalDateTime fechaCarga = LocalDateTime.now();
        for (JsonNode nodo : depositos) {
            JsonNode id = nodo.path("id");
            JsonNode nombre = nodo.path("nombre");
            JsonNode auditado = nodo.path("auditado");
            if (!id.isIntegralNumber() || !id.canConvertToInt()
                    || !nombre.isTextual() || nombre.asText().isBlank()
                    || !auditado.isBoolean()) {
                throw new IOException("Cada deposito requiere id entero, nombre y auditado booleano");
            }
            if (!ids.add(id.asInt())) throw new IOException("ID de deposito duplicado: " + id);

            // El formato actual no incluye fechas: true se considera reciente al cargar.
            LocalDateTime fecha = auditado.asBoolean() ? fechaCarga : null;
            JsonNode fechaJson = nodo.get("fechaUltimaAuditoria");
            if (fechaJson != null) {
                if (fechaJson.isNull()) {
                    fecha = null;
                } else {
                    if (!fechaJson.isTextual()) throw new IOException("Fecha de auditoria invalida");
                    try {
                        fecha = LocalDateTime.parse(fechaJson.asText());
                    } catch (DateTimeParseException e) {
                        throw new IOException("Fecha de auditoria invalida: " + fechaJson.asText(), e);
                    }
                }
            }
            // Solo los registros principales se insertan en el ABB; conexiones se ignora.
            resultado.add(new Deposito(id.asInt(), nombre.asText(), false, fecha));
        }
        return resultado;
    }
}
