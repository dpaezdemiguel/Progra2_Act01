# Gestion de carga y despacho

Requiere Java 21 y Maven. Ejecutar `com.uade.logistica.Main` desde el IDE con las dependencias de Maven, o desde la terminal:

mvn compile org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=com.uade.logistica.Main

Pruebas: `mvn test`.

El sistema incorpora `src/resources/inventario.json` al iniciar. El archivo es una lista de objetos con `id`, `peso`, `destino`, `urgente` y `contenido`. Los IDs deben ser unicos; si el archivo contiene datos invalidos o duplicados, no se incorpora ningun paquete de esa carga.

El menu permite registrar paquetes manualmente, procesar el siguiente del centro y cargarlo al camion, deshacer la ultima carga devolviendola al centro, descargar y consultar cantidades. Los registros manuales permanecen en memoria durante la ejecucion; no se guardan en el JSON.

El centro procesa primero los paquetes urgentes o de mas de 50 kg. A igual prioridad respeta el orden de ingreso al centro; un paquete devuelto al deshacer ingresa nuevamente. La estructura de prioridad existente busca el siguiente en O(n). El camion usa la pila enlazada existente: cargar, descargar y retirar la ultima carga son O(1). Reinsertar en el centro usa el `ArrayList` existente y cuesta O(1) amortizado.

# Analisis de complejidad

## MyOwnStack

- push(): O(1)
- pop(): O(1)
- peek(): O(1)
- size(): O(1)

Espacio utilizado: O(n)

## MyOwnPriorityStack

- push(): O(1)
- getPriorityElement(): O(n)
- size(): O(1)

Espacio utilizado: O(n)

## Camion

- cargarPaquete(): O(1)
- deshacerCarga(): O(1)
- descargarPaquete(): O(1)

Espacio utilizado: O(n)

## CentroDistribucion

- agregarPaquete(): O(1)
- procesarSiguiente(): O(n)
- cantidadPendientes(): O(1)

Espacio utilizado: O(n)

## JsonLoader

- cargarInventario(): O(n)

Espacio utilizado: O(n)
## Red de depositos (ABB manual)

Las opciones 7, 8 y 9 permiten cargar `depositos.json`, correr la auditoria y consultar un nivel (la raiz esta en el nivel 0). La carga reemplaza el arbol en memoria con los registros principales del arreglo `depositos`, en su orden de aparicion y ordenados por ID mediante el ABB existente. Se ignora `conexiones`; sus IDs no crean depositos adicionales. Una carga invalida conserva el arbol anterior.

El formato requiere `id` entero unico, `nombre` y `auditado` booleano. Como el JSON actual no incluye fechas, se considera `auditado: true` como auditado en la fecha de carga y `false` como sin auditoria previa. Esta es una convencion: el booleano por si solo no permite saber si pasaron 30 dias. Opcionalmente se admite `fechaUltimaAuditoria` en formato ISO local (ej. `2026-08-01T12:00:00`) o `null`, que prevalece sobre esa convencion.

La auditoria recorre izquierda, derecha y raiz (post-orden), y marca `visitado` solo si no hay fecha o es anterior a hace 30 dias. No cambia la fecha: identifica los depositos que requieren inspeccion. Los cambios quedan en memoria, no se escriben al JSON. El recorrido cuesta O(n); insertar cuesta O(h), con h igual a la altura del arbol.
