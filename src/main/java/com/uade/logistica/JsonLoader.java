package com.uade.logistica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonLoader {

    public static void cargarInventario() {

        try {

            ObjectMapper mapper = new ObjectMapper();

            InputStream input =
                    JsonLoader.class.getClassLoader()
                            .getResourceAsStream("inventario.json");

            JsonNode paquetes = mapper.readTree(input);

            System.out.println("Paquetes cargados:");

            for (JsonNode paquete : paquetes) {

                System.out.println(
                        paquete.get("id").asText()
                                + " - "
                                + paquete.get("destino").asText()
                );
            }

        } catch (Exception e) {

            System.out.println("Error al leer inventario.json");

            e.printStackTrace();
        }
    }
}