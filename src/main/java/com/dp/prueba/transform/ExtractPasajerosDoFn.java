package com.dp.prueba.transform;

import com.dp.prueba.objects.PasajeroRecords;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;

public class ExtractPasajerosDoFn extends DoFn<String, String> {

  @ProcessElement
  public void processElement(ProcessContext c) {
    String record = c.element();
    c.output(PasajeroRecords.toJson(record));
  }
}
