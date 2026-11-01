package com.uade.logistica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Grafo {

    private final Map<Integer, Deposito> depositos = new LinkedHashMap<>();
    private final Map<Integer, Map<Integer, Double>> conexiones = new HashMap<>();

    public void agregarDeposito(Deposito deposito) {
        if (deposito == null || depositos.containsKey(deposito.getId())) {
            throw new IllegalArgumentException("Deposito nulo o duplicado.");
        }
        depositos.put(deposito.getId(), deposito);
        conexiones.put(deposito.getId(), new HashMap<>());
    }

    public void agregarConexion(int origen, int destino, double distancia) {
        validarDeposito(origen);
        validarDeposito(destino);
        if (!Double.isFinite(distancia) || distancia < 0) {
            throw new IllegalArgumentException("La distancia debe ser finita y no negativa.");
        }
        conexiones.get(origen).merge(destino, distancia, Math::min);
    }

    public double distanciaMinima(int origen, int destino) {
        validarDeposito(origen);
        validarDeposito(destino);
        Map<Integer, Double> distancias = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();
        for (int id : depositos.keySet()) {
            distancias.put(id, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);

        while (visitados.size() < depositos.size()) {
            Integer actual = null;
            double menor = Double.POSITIVE_INFINITY;
            for (int id : depositos.keySet()) {
                if (!visitados.contains(id) && distancias.get(id) < menor) {
                    actual = id;
                    menor = distancias.get(id);
                }
            }
            if (actual == null) break;
            if (actual == destino) return menor;
            visitados.add(actual);
            for (Map.Entry<Integer, Double> conexion : conexiones.get(actual).entrySet()) {
                double nueva = menor + conexion.getValue();
                if (nueva < distancias.get(conexion.getKey())) {
                    distancias.put(conexion.getKey(), nueva);
                }
            }
        }
        return distancias.get(destino);
    }

    private void validarDeposito(int id) {
        if (!depositos.containsKey(id)) {
            throw new IllegalArgumentException("No existe el deposito " + id + ".");
        }
    }
}
