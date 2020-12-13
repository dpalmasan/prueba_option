package com.dp.prueba.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PasajeroRecordsTest {
  @Test
  public void toJSON_Test() {
    String result = PasajeroRecords
        .toJson("50222525-1|Lane Weber Obama|nn@gmail.com|fake street 123, evergreen|+56943657435|1947/10/12");
    String expected = "{\"dni\":\"50222525-1\",\"nombre_completo\":\"Lane Weber O\",\"correo_electronico\":\"nn@gmail.com\","
        + "\"direccion\":\"fake street 123, evergreen\",\"telefono\":\"+56943657435\",\"fecha_de_nacimiento\":\"1947/10/12\"}";
    assertEquals(expected, result);
  }
}