package com.dp.prueba.objects;

import org.apache.beam.vendor.grpc.v1p26p0.com.google.gson.Gson;

public class VentaRecords {
  private static enum Index {
    COD_AEROLINEA, COD_AVION, ASIENTO, DNI, MONTO, ESTADO, FECHA_RESERVA, FECHA_COMPRA, CATEGORIA
  };

  private static class Venta {
    private int cod_aerolinea;
    private int cod_avion;
    private String asiento;
    private String dni;
    private double monto;
    private String estado;
    private String fecha_reserva;
    private String fecha_compra;
    private String categoria;

    Venta(int cod_aerolinea, int cod_avion, String asiento, String dni, double monto, String estado,
        String fecha_reserva, String fecha_compra, String categoria) {
      this.cod_aerolinea = cod_aerolinea;
      this.cod_avion = cod_avion;
      this.asiento = asiento;
      this.dni = dni;
      this.monto = monto;
      this.estado = estado;
      this.fecha_reserva = fecha_reserva;
      this.fecha_compra = fecha_compra;
      this.categoria = categoria;
    }

  }

  public static Venta parseVenta(String record) {
    String[] processedRecord = record.split("\\|");

    if (processedRecord.length != 9) {
      throw new IllegalArgumentException("No se pudo procesar el registro: " + record);
    }

    int codeAerolinea = Integer.parseInt(processedRecord[Index.COD_AEROLINEA.ordinal()]);
    int codeAvion = Integer.parseInt(processedRecord[Index.COD_AVION.ordinal()]);
    String asiento = processedRecord[Index.ASIENTO.ordinal()];
    String dni = processedRecord[Index.DNI.ordinal()];
    double monto = Double.parseDouble(processedRecord[Index.MONTO.ordinal()]);
    String estado = processedRecord[Index.ESTADO.ordinal()];
    String fechaReserva = processedRecord[Index.FECHA_RESERVA.ordinal()];
    String fechaCompra = processedRecord[Index.FECHA_COMPRA.ordinal()];
    String categoria = processedRecord[Index.CATEGORIA.ordinal()];
    return new Venta(codeAerolinea, codeAvion, asiento, dni, monto, estado, fechaReserva, fechaCompra, categoria);

  }

  public static String toJson(String record) {
    Venta vuelo = parseVenta(record);
    Gson gson = new Gson();
    return gson.toJson(vuelo);
  }
}
