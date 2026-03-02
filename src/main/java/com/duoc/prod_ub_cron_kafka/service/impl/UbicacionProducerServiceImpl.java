package com.duoc.prod_ub_cron_kafka.service.impl;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.duoc.prod_ub_cron_kafka.config.KafkaProducerConfig;
import com.duoc.prod_ub_cron_kafka.dto.UbicacionVehiculoDTO;
import com.duoc.prod_ub_cron_kafka.service.UbicacionProducerService;

@Service
public class UbicacionProducerServiceImpl implements UbicacionProducerService {

    @Autowired
    private KafkaTemplate<String, UbicacionVehiculoDTO> kafkaTemplate;

    private final Random random = new Random();

    // Simulacion de vehiculos del transporte publico
    private static final List<String> VEHICULOS = Arrays.asList(
            "BUS-001", "BUS-002", "BUS-003", "BUS-004", "BUS-005"
    );

    private static final List<String> RUTAS = Arrays.asList(
            "Ruta 101 - Providencia", "Ruta 202 - Las Condes",
            "Ruta 303 - Santiago Centro", "Ruta 404 - Maipu",
            "Ruta 505 - La Florida"
    );

    // Coordenadas base de Santiago de Chile
    private static final double LAT_BASE = -33.4489;
    private static final double LON_BASE = -70.6693;

    @Override
    public void enviarUbicacion(UbicacionVehiculoDTO ubicacion) {
        ubicacion.setId(System.nanoTime());
        ubicacion.setTimestamp(Instant.now().toString());

        kafkaTemplate.send(KafkaProducerConfig.TOPIC_UBICACIONES, ubicacion);
        System.out.println("Ubicacion enviada a Kafka: " + ubicacion.toString());
    }

    private static final int MENSAJES_POR_CICLO = 2;

    @Override
    public void generarYEnviarUbicaciones() {
        for (int i = 0; i < MENSAJES_POR_CICLO; i++) {
            UbicacionVehiculoDTO ubicacion = new UbicacionVehiculoDTO();
            ubicacion.setId(System.nanoTime());
            ubicacion.setVehiculoId(VEHICULOS.get(i));
            ubicacion.setLatitud(LAT_BASE + (random.nextDouble() * 0.1 - 0.05));
            ubicacion.setLongitud(LON_BASE + (random.nextDouble() * 0.1 - 0.05));
            ubicacion.setRuta(RUTAS.get(i));
            ubicacion.setTimestamp(Instant.now().toString());

            kafkaTemplate.send(KafkaProducerConfig.TOPIC_UBICACIONES, ubicacion);
            System.out.println("[CRON] Ubicacion enviada: " + ubicacion.toString());
        }
    }
}
