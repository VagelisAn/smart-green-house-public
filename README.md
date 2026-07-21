# 🌿 Autonomous IoT Smart Greenhouse

An enterprise-grade, solar-powered IoT Smart Greenhouse automation system designed for real-time environmental monitoring and intelligent control.

The system combines **Edge Computing**, **MQTT communication**, **Spring Boot backend services**, **PostgreSQL data storage**, and an **Angular web dashboard** to monitor and automate greenhouse conditions such as temperature, humidity, soil moisture, lighting, and device status.

---

# 🏗️ System Architecture

## Software Stack

* **Frontend:** Angular application deployed through Nginx
* **Backend:** Spring Boot REST API with MQTT client integration
* **Database:** PostgreSQL
* **MQTT Broker:** Eclipse Mosquitto
* **Containerization:** Docker & Docker Compose

## Hardware Stack

* Raspberry Pi (Edge Server / Local Gateway)
* Arduino Uno (Sensor acquisition and local automation)
* ESP8266 / WeMos D1 Mini (Wi-Fi MQTT Gateway)
* Environmental sensors
* Relay-controlled actuators
* Solar-powered energy system

---

# 🚀 Deployment Workflow

The deployment process follows these steps:

1. Build Spring Boot backend.
2. Build Angular production frontend.
3. Transfer application artifacts to the Raspberry Pi.
4. Rebuild Docker containers.
5. Start the complete IoT stack.
6. Verify MQTT communication.
7. Monitor logs for troubleshooting.

---

# 📦 Application Build

## Spring Boot Backend

Build the backend application:

```bash
mvn clean package -DskipTests
```

The generated JAR file is produced inside:

```text
target/app.jar
```

---

# 🚚 Deployment to Raspberry Pi

## Copy Project Files

Example:

```bash
scp -r iot-stack user@<RASPBERRY_PI_IP>:/home/user/
```

Replace:

```text
<RASPBERRY_PI_IP>
```

with the local IP address of your Raspberry Pi.

---

# Backend Deployment

If permission issues occur:

```bash
sudo chown -R user:user /home/user/iot-stack/backend
```

Remove previous application files:

```bash
cd /home/user/iot-stack/backend

rm -f app.jar
```

Upload the new backend artifact:

```bash
scp target/app.jar user@<RASPBERRY_PI_IP>:/home/user/iot-stack/backend/
```

---

# Frontend Deployment

Build Angular production files:

```bash
ng build --configuration production
```

Upload the generated files:

```bash
scp -r dist/<frontend-build>/* user@<RASPBERRY_PI_IP>:/home/user/iot-stack/frontend/
```

---

# 🐳 Docker Management

Navigate to the project directory:

```bash
cd ~/iot-stack
```

## Stop Existing Services

```bash
docker compose down
```

## Rebuild Containers

```bash
docker compose build --no-cache
```

## Start Complete System

```bash
docker compose up -d
```

---

# Useful Docker Commands

| Command                          | Description             |
| -------------------------------- | ----------------------- |
| `docker ps`                      | Show running containers |
| `docker ps -a`                   | Show all containers     |
| `docker logs -f <container>`     | Monitor container logs  |
| `docker restart <container>`     | Restart a service       |
| `docker exec -it <container> sh` | Enter container shell   |

---

# 📡 MQTT Testing

## Register a Test Sensor

Example MQTT message:

```bash
mosquitto_pub \
-h <MQTT_HOST> \
-p 1883 \
-t greenhouse/sensor/register \
-m '{
"name":"Greenhouse Sensor",
"location":"Zone A",
"model":"Arduino UNO",
"type":"TEMPERATURE"
}'
```

---

## Send Test Sensor Data

```bash
mosquitto_pub \
-h <MQTT_HOST> \
-p 1883 \
-t greenhouse/sensor/data \
-m '{
"deviceId":"5",
"type":"TEMPERATURE",
"value":27.3
}'
```

---

# Monitor MQTT Traffic

Open Mosquitto container:

```bash
docker exec -it mosquitto sh
```

Subscribe to sensor data:

```bash
mosquitto_sub -t "greenhouse/sensor/data" -v
```

Monitor all MQTT topics:

```bash
mosquitto_sub -t "#" -v
```

---

# 🖥️ System Services

The deployed Docker environment contains:

```text
Angular Frontend
        |
        |
Spring Boot Backend
        |
        |
PostgreSQL Database


ESP8266
   |
 MQTT
   |
Mosquitto Broker
   |
Spring Boot MQTT Client
```

---

# 🔒 Security Notes

Before deploying publicly:

* Do not commit passwords or API keys.
* Do not publish private IP addresses.
* Do not expose MQTT ports directly to the internet.
* Use environment variables for sensitive configuration.
* Enable MQTT authentication.
* Use HTTPS when exposing the dashboard externally.
* Restrict database access to internal services only.

---

# 🔌 Raspberry Pi Management

Safe shutdown:

```bash
sudo shutdown now
```

---

# 📌 Future Improvements

Possible future extensions:

* Mobile application integration
* Artificial intelligence based crop analysis
* Weather API integration
* Advanced irrigation prediction
* Additional environmental sensors
* Cloud synchronization

---

# 📖 Project Documentation

The complete project documentation includes:

* Hardware architecture
* Solar power subsystem
* Sensor integration
* MQTT communication design
* Backend implementation
* Database schema
* Angular dashboard
* Automation logic

Source code and additional technical documentation will be published progressively.
