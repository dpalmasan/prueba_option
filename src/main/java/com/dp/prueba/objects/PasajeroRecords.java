package com.dp.prueba.objects;

import org.apache.beam.vendor.grpc.v1p26p0.com.google.gson.Gson;

public class PasajeroRecords {

  // Dni|nombre_completo|correo_electronico|dirección|teléfono|fecha_de_nacimiento
  private static enum Index {
    DNI, NOMBRE_COMPLETO, CORREO, DIRECCION, TELEFONO, FECHA_NAC
  };

  private static class Pasajero {
    private String dni;
    private String nombre_completo;
    private String correo_electronico;
    private String direccion;
    private String telefono;
    private String fecha_de_nacimiento;

    Pasajero(String dni, String nombre_completo, String correo_electronico, String direccion, String telefono,
        String fecha_de_nacimiento) {
      this.dni = dni;
      this.nombre_completo = nombre_completo;
      this.correo_electronico = correo_electronico;
      this.direccion = direccion;
      this.telefono = telefono;
      this.fecha_de_nacimiento = fecha_de_nacimiento;
    }
  }

  public static Pasajero parsePasajero(String record) {
    String[] processedRecord = record.split("\\|");

    if (processedRecord.length != 6) {
      throw new IllegalArgumentException("No se pudo procesar el registro: " + record);
    }

    String dni = processedRecord[Index.DNI.ordinal()];
    String nombre = processedRecord[Index.NOMBRE_COMPLETO.ordinal()];

    // Simplificacion para proteger apellido
    int apellidoIndex = nombre.lastIndexOf(" ");
    String nombreProtegido = nombre.substring(0, apellidoIndex + 2);
    String correo = processedRecord[Index.CORREO.ordinal()];
    String direccion = processedRecord[Index.DIRECCION.ordinal()];
    String telefono = processedRecord[Index.TELEFONO.ordinal()];
    String nac = processedRecord[Index.FECHA_NAC.ordinal()];
    return new Pasajero(dni, nombreProtegido, correo, direccion, telefono, nac);

  }

  public static String toJson(String record) {
    Pasajero vuelo = parsePasajero(record);
    Gson gson = new Gson();
    return gson.toJson(vuelo);
  }
}
