package com.dp.prueba.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VentaRecordsTest {
  @Test
  public void toJSON_Test() {
    String result = VentaRecords
        .toJson("0037|000000000123|A36|50222525-1|69000.00|VENTA|20201019 00:00:00|20201020 00:00:00|ECONOMIC");
    String expected = "{\"cod_aerolinea\":37,\"cod_avion\":123,\"asiento\":\"A36\",\"dni\":\"50222525-1\","
        + "\"monto\":69000.0,\"estado\":\"VENTA\",\"fecha_reserva\":\"20201019 00:00:00\",\"fecha_compra\":\"20201020 00:00:00\""
        + ",\"categoria\":\"ECONOMIC\"}";
    assertEquals(expected, result);
  }
}
