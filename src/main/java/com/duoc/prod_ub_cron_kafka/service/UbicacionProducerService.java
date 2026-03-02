package com.duoc.prod_ub_cron_kafka.service;

import com.duoc.prod_ub_cron_kafka.dto.UbicacionVehiculoDTO;

public interface UbicacionProducerService {

    void enviarUbicacion(UbicacionVehiculoDTO ubicacion);

    void generarYEnviarUbicaciones();
}
