# prod-ub-cron-kafka

Microservicio productor que simula la generación de ubicaciones GPS de vehículos del transporte público (buses) en Santiago de Chile. Genera datos para 5 buses en 5 rutas distintas y los publica automáticamente en un topic de Apache Kafka cada 5 minutos mediante un CRON programado.

## Tecnologías

- Java 21
- Spring Boot 3.3.1
- Spring Kafka
- Apache Kafka (3 brokers)
- Docker

## Puerto

```
8081
```

## Topic Kafka

| Dirección | Topic | Particiones | Réplicas | Retención |
|-----------|-------|-------------|----------|-----------|
| Produce   | `ubicaciones_vehiculos` | 3 | 2 | 12 horas |

## Endpoints REST

Base: `/api/ubicaciones`

| Método | Path | Descripción |
|--------|------|-------------|
| `POST` | `/api/ubicaciones` | Envía una ubicación manual a Kafka |
| `POST` | `/api/ubicaciones/batch` | Envía múltiples ubicaciones a Kafka |
| `POST` | `/api/ubicaciones/generar` | Trigger manual del CRON (genera 5 ubicaciones simuladas) |
| `GET`  | `/api/ubicaciones/health` | Health check del servicio |

## Estructura del mensaje producido

```json
{
  "id": 1,
  "vehiculoId": "BUS-001",
  "latitud": -33.4512,
  "longitud": -70.6693,
  "ruta": "Ruta 101 - Providencia",
  "timestamp": "2026-03-02T03:14:00Z"
}
```

## Programación CRON

- **Expresión:** `0 */5 * * * *` (cada 5 minutos)
- **Configurable:** propiedad `ubicacion.cron.expression`
- Genera 1 ubicación por cada uno de los 5 vehículos (BUS-001 a BUS-005)

## Ejecución

### Con Docker Compose (recomendado)

Desde el directorio raíz del proyecto:

```bash
docker compose build prod-ub-cron-kafka
docker compose up -d prod-ub-cron-kafka
```

### Con Docker directamente

```bash
docker build -t prod-ub-cron-kafka .
docker run -p 8081:8081 \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka-1:9092,kafka-2:9092,kafka-3:9092 \
  --network kafka-net \
  prod-ub-cron-kafka
```

### Local con Maven

```bash
./mvnw spring-boot:run
```

Requiere Kafka corriendo en `localhost:29092,localhost:39092,localhost:49092`.

## Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Brokers Kafka | `localhost:29092,localhost:39092,localhost:49092` |
| `SERVER_PORT` | Puerto del servicio | `8081` |
