## Prueba Option

### Compilar código beam (DataFlow bits)

```
mvn clean install -U -B
```

Correr job dataflow directamente ejecutando el `JAR`

```
java -jar target/prueba-tecnica-1.jar \
  --runner=DataFlowRunner \
  --project=diego-palma \
  --tempLocation=gs://prueba-option/temp/
```

Dentro de la carpeta `src` está todo el código en `Java` usando el SDK Beam para crear el pipeline. Se tomó un enfoque simple, como se muestra en el documento adjunto al correo.

### Orquestación de ETL con airflow (composer)

El código del DAG implementado está bajo la carpeta dags.
