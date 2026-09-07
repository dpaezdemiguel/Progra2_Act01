# Gestion de carga y despacho

Requiere Java 21 y Maven. Ejecutar `com.uade.logistica.Main` desde el IDE con las dependencias de Maven, o desde la terminal:

```sh
mvn compile org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=com.uade.logistica.Main
```

Pruebas: `mvn test`.

El sistema incorpora `src/resources/inventario.json` al iniciar. El archivo es una lista de objetos con `id`, `peso`, `destino`, `urgente` y `contenido`. Los IDs deben ser unicos; si el archivo contiene datos invalidos o duplicados, no se incorpora ningun paquete de esa carga.

El menu permite registrar paquetes manualmente, procesar el siguiente del centro y cargarlo al camion, deshacer la ultima carga devolviendola al centro, descargar y consultar cantidades. Los registros manuales permanecen en memoria durante la ejecucion; no se guardan en el JSON.

El centro procesa primero los paquetes urgentes o de mas de 50 kg. A igual prioridad respeta el orden de ingreso al centro; un paquete devuelto al deshacer ingresa nuevamente. La estructura de prioridad existente busca el siguiente en O(n). El camion usa la pila enlazada existente: cargar, descargar y retirar la ultima carga son O(1). Reinsertar en el centro usa el `ArrayList` existente y cuesta O(1) amortizado.
