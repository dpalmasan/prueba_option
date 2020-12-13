package com.dp.prueba.transform;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class ExtractPasajeros extends PTransform<PCollection<String>, PCollection<String>> {
  @Override
  public PCollection<String> expand(PCollection<String> records) {

    // Convert lines of text into individual words.
    PCollection<String> jsonRecords = records.apply(ParDo.of(new ExtractPasajerosDoFn()));

    return jsonRecords;
  }
}