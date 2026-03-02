package com.duoc.prod_ub_cron_kafka.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duoc.prod_ub_cron_kafka.service.UbicacionProducerService;

@Component
public class UbicacionScheduler {

    @Autowired
    private UbicacionProducerService ubicacionProducerService;

    /**
     * Tarea programada que se ejecuta cada 5 minutos.
     * Genera ubicaciones simuladas de vehiculos y las envia a Kafka.
     */
    @Scheduled(cron = "${ubicacion.cron.expression}")
    public void enviarUbicacionesProgramadas() {
        System.out.println("=== [CRON] Ejecutando envio programado de ubicaciones ===");
        ubicacionProducerService.generarYEnviarUbicaciones();
        System.out.println("=== [CRON] Envio programado completado ===");
    }
}
