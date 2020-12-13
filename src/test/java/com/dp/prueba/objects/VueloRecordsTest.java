package com.dp.prueba.objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VueloRecordsTest {
    @Test
    public void toJSON_Test() {
        String result = VueloRecords.toJson("000000000123|00097|0276|0098|LA2543|11:00:00|22:00:00");
        String expected = "{\"cod_avion\":123,\"capacidad\":97,\"cod_tripulacion\":276,\"cod_piloto\":98"
                + ",\"cod_vuelo\":\"LA2543\",\"horario_salida\":\"11:00:00\",\"horario_llegada\":\"22:00:00\"}";
        assertEquals(expected, result);
    }
}
