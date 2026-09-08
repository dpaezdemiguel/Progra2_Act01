package com.uade.logistica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    public static List<Paquete<?>> cargarInventario() throws IOException {
        try (InputStream input = JsonLoader.class.getClassLoader()
                .getResourceAsStream("inventario.json")) {
            if (input == null) {
                throw new IOException("No se encontro inventario.json en los recursos.");
            }
            JsonNode raiz = new ObjectMapper().readTree(input);
            if (raiz == null || !raiz.isArray()) {
                throw new IOException("El inventario debe ser una lista JSON de paquetes.");
            }
            List<Paquete<?>> paquetes = new ArrayList<>();
            for (JsonNode nodo : raiz) {
                if (!nodo.path("id").isTextual() || !nodo.path("peso").isNumber()
                        || !nodo.path("destino").isTextual() || !nodo.path("urgente").isBoolean()
                        || !nodo.hasNonNull("contenido")) {
                    throw new IOException("Cada paquete requiere id, peso, destino, urgente y contenido validos.");
                }
                try {
                    paquetes.add(new Paquete<>(nodo.get("id").asText(), nodo.get("peso").asDouble(),
                            nodo.get("destino").asText(), nodo.get("urgente").asBoolean(),
                            nodo.get("contenido")));
                } catch (IllegalArgumentException e) {
                    throw new IOException("Paquete invalido: " + e.getMessage(), e);
                }
            }
            return paquetes;
        }
    }
}
