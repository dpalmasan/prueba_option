package com.dp.prueba.objects;

import org.apache.beam.vendor.grpc.v1p26p0.com.google.gson.Gson;

public class VueloRecords {
    private static enum Index {
        COD_AVION, CAPACIDAD, COD_TRIPULACION, COD_PILOTO, COD_VUELO, HORARIO_SALIDA, HORARIO_LLEGADA
    };

    private static class Vuelo {
        private int cod_avion;
        private int capacidad;
        private int cod_tripulacion;
        private int cod_piloto;
        private String cod_vuelo;
        private String horario_salida;
        private String horario_llegada;

        Vuelo(int cod_avion, int capacidad, int cod_tripulacion, int cod_piloto, String cod_vuelo,
                String horario_salida, String horario_llegada) {
            this.cod_avion = cod_avion;
            this.capacidad = capacidad;
            this.cod_tripulacion = cod_tripulacion;
            this.cod_piloto = cod_piloto;
            this.cod_vuelo = cod_vuelo;
            this.horario_salida = horario_salida;
            this.horario_llegada = horario_llegada;
        }

    }

    public static Vuelo parseVuelo(String record) {
        String[] processedRecord = record.split("\\|");

        if (processedRecord.length != 7) {
            throw new IllegalArgumentException("No se pudo procesar el registro: " + record);
        }

        int codeAvion = Integer.parseInt(processedRecord[Index.COD_AVION.ordinal()]);
        int capacidad = Integer.parseInt(processedRecord[Index.CAPACIDAD.ordinal()]);
        int codTripulacion = Integer.parseInt(processedRecord[Index.COD_TRIPULACION.ordinal()]);
        int codPiloto = Integer.parseInt(processedRecord[Index.COD_PILOTO.ordinal()]);
        String codVuelo = processedRecord[Index.COD_VUELO.ordinal()];
        String horarioSalida = processedRecord[Index.HORARIO_SALIDA.ordinal()];
        String horarioLlegada = processedRecord[Index.HORARIO_LLEGADA.ordinal()];
        return new Vuelo(codeAvion, capacidad, codTripulacion, codPiloto, codVuelo, horarioSalida, horarioLlegada);

    }

    public static String toJson(String record) {
        Vuelo vuelo = parseVuelo(record);
        Gson gson = new Gson();
        return gson.toJson(vuelo);
    }

}
