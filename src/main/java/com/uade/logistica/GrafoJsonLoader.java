package com.uade.logistica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GrafoJsonLoader {

    public static Grafo cargarGrafo(InputStream input, List<Deposito> depositos) throws IOException {
        JsonNode raiz = new ObjectMapper().readTree(input);
        JsonNode nodos = raiz == null ? null : raiz.get("depositos");
        if (nodos == null || !nodos.isArray()) {
            throw new IOException("El archivo debe contener un arreglo llamado depositos");
        }
        Grafo grafo = new Grafo();
        try {
            for (Deposito deposito : depositos) grafo.agregarDeposito(deposito);
            for (JsonNode nodo : nodos) {
                JsonNode conexiones = nodo.get("conexiones");
                if (conexiones == null) continue;
                if (!conexiones.isArray()) {
                    throw new IOException("Las conexiones deben ser una lista.");
                }
                for (JsonNode conexion : conexiones) {
                    JsonNode id = conexion.path("id");
                    JsonNode distancia = conexion.path("distancia");
                    if (!id.isIntegralNumber() || !id.canConvertToInt() || !distancia.isNumber()) {
                        throw new IOException("Cada conexion requiere id entero y distancia numerica.");
                    }
                    grafo.agregarConexion(nodo.path("id").asInt(), id.asInt(), distancia.asDouble());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IOException("Red de rutas invalida: " + e.getMessage(), e);
        }
        return grafo;
    }
}
