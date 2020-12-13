-- Obtener los no cancelados
WITH vigentes AS (
    SELECT dni, cod_avion, fecha_compra, categoria
    FROM diego-palma.prueba_option.ventas
    WHERE 
        dni NOT IN (SELECT DISTINCT dni from diego-palma.prueba_option.ventas WHERE estado = "CANCELACION")
),
diferencial AS (SELECT 
    v.dni,
    v.cod_avion,
    PARSE_DATE('%Y%m%d', SPLIT(v.fecha_compra, " ")[OFFSET(0)]) as fecha_compra,
    DATE_DIFF( CURRENT_DATE, DATE(p.fecha_de_nacimiento), YEAR) as edad,
    v.categoria
FROM vigentes v
INNER JOIN diego-palma.prueba_option.pasajeros p
ON p.dni = v.dni
),

rangos as (SELECT t.dni, t.fecha_compra, t.cod_avion, t.edad, 
          (SELECT Count(*) FROM diferencial sub 
           WHERE sub.fecha_compra <= t.fecha_compra 
           AND sub.cod_avion = t.cod_avion) AS rango
FROM diferencial t
WHERE edad < 14 OR edad >= 60 OR categoria IN ("PREMIUM", "VIP")

),

prioridades as (SELECT dni, fecha_compra, cod_avion, edad,
CASE 
    WHEN edad >= 60 THEN -1
    WHEN edad < 14 THEN 0
    ELSE rango
END AS prioridad
FROM rangos), 

compensados AS (SELECT 
    cod_avion,
    ARRAY_AGG(STRUCT(dni, prioridad) ORDER BY prioridad LIMIT 3) top
FROM prioridades p1
GROUP BY 1),

compensaciones_dni AS (SELECT
    t.dni,
    CASE
        WHEN t.prioridad = -1 THEN "ASIENTO_PREFERENCIAL"
        ELSE "ASISTENCIA_PREFERENCIAL"
    END as compensacion
FROM compensados, UNNEST(top) as t)

SELECT 
    ve.cod_aerolinea,
    v.capacidad,
    v.cod_tripulacion,
    v.cod_piloto,
    v.cod_vuelo,
    v.horario_salida,
    v.horario_llegada,
    v.cod_avion,
    ve.asiento,
    ve.dni,
    p.nombre_completo,
    p.correo_electronico,
    p.direccion,
    p.telefono,
    p.fecha_de_nacimiento,
    CASE
        WHEN estado = "CANCELACION" THEN ve.monto*-1
        ELSE ve.monto
    END as monto,
    ve.estado,
    ve.fecha_reserva,
    ve.fecha_compra,
    ve.categoria,
    COALESCE(c.compensacion, "NO APLICA") as compensacion
    FROM 
        diego-palma.prueba_option.vuelos v
    INNER JOIN diego-palma.prueba_option.ventas ve
    ON v.cod_avion = ve.cod_avion
    INNER JOIN diego-palma.prueba_option.pasajeros p
    ON p.dni = ve.dni
    LEFT JOIN compensaciones_dni c
    ON ve.dni = c.dni
