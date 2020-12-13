package com.dp.prueba;

import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.runners.dataflow.DataflowRunner;

import com.dp.prueba.transform.ExtractFlights;
import com.dp.prueba.transform.ExtractPasajeros;
import com.dp.prueba.transform.ExtractVentas;

public class VuelosETL {

  public static void main(String[] args) {
    DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);

    options.setStagingLocation("gs://prueba-option/tmp");
    options.setProject("diego-palma");
    options.setRunner(DataflowRunner.class);
    Pipeline p = Pipeline.create(options);

    p.apply(TextIO.read().from("gs://prueba-option/input-data/vuelo.csv")).apply("Parse records", new ExtractFlights())
        .apply(TextIO.write().to("gs://prueba-option/output/vuelos/*"));

    p.apply(TextIO.read().from("gs://prueba-option/input-data/venta.csv")).apply("Parse records", new ExtractVentas())
        .apply(TextIO.write().to("gs://prueba-option/output/ventas/*"));

    p.apply(TextIO.read().from("gs://prueba-option/input-data/pasajero.csv"))
        .apply("Parse records", new ExtractPasajeros())
        .apply(TextIO.write().to("gs://prueba-option/output/pasajeros/*"));

    p.run().waitUntilFinish();
  }
}
