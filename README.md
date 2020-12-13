## Prueba Option

### Compilar código beam (DataFlow bits)

```
mvn clean install -U -B
```

Correr job dataflow directamente ejecutando el `JAR`

```
java -jar target/prueba-tecnica-1.jar \
    --project=diego-palma \
    --gcpTempLocation=gs://diego-palma/tmp/ \
    --jobName=dataflow-vuelos \
    --region=us-central1
```

Dentro de la carpeta `src` está todo el código en `Java` usando el SDK Beam para crear el pipeline. Se tomó un enfoque simple, como se muestra en el documento adjunto al correo. En la carpeta `test` hay tests unitarios.

### Orquestación de ETL con airflow (composer)

El código del DAG implementado está bajo la carpeta dags.
