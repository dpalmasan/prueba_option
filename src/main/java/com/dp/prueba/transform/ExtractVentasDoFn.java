package com.dp.prueba.transform;

import com.dp.prueba.objects.VentaRecords;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;

public class ExtractVentasDoFn extends DoFn<String, String> {

  @ProcessElement
  public void processElement(ProcessContext c) {
    String record = c.element();
    c.output(VentaRecords.toJson(record));
  }
}
