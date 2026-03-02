package com.duoc.prod_ub_cron_kafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.prod_ub_cron_kafka.dto.UbicacionVehiculoDTO;
import com.duoc.prod_ub_cron_kafka.service.UbicacionProducerService;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionProducerService ubicacionProducerService;

    /**
     * POST - Enviar una ubicacion manualmente a Kafka
     */
    @PostMapping
    public ResponseEntity<String> enviarUbicacion(@RequestBody UbicacionVehiculoDTO ubicacion) {
        ubicacionProducerService.enviarUbicacion(ubicacion);
        return ResponseEntity.ok("Ubicacion enviada a Kafka: " + ubicacion.toString());
    }

    /**
     * POST - Enviar multiples ubicaciones manualmente a Kafka
     */
    @PostMapping("/batch")
    public ResponseEntity<String> enviarUbicacionesBatch(@RequestBody List<UbicacionVehiculoDTO> ubicaciones) {
        for (UbicacionVehiculoDTO ubicacion : ubicaciones) {
            ubicacionProducerService.enviarUbicacion(ubicacion);
        }
        return ResponseEntity.ok("Se enviaron " + ubicaciones.size() + " ubicaciones a Kafka");
    }

    /**
     * POST - Trigger manual del CRON para generar ubicaciones simuladas
     */
    @PostMapping("/generar")
    public ResponseEntity<String> generarUbicaciones() {
        ubicacionProducerService.generarYEnviarUbicaciones();
        return ResponseEntity.ok("Ubicaciones generadas y enviadas a Kafka exitosamente");
    }

    /**
     * GET - Health check del servicio
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Servicio prod-ub-cron-kafka activo");
    }
}
