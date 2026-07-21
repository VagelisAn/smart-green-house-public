#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <ArduinoJson.h>

// =========================================================================
// 1. ΡΥΘΜΙΣΕΙΣ ΔΙΚΤΥΟΥ & MQTT BROKER (Διορθωμένο & Τσεκαρισμένο!)
// =========================================================================
const char* ssid         = "wifiName";
const char* password     = "password";
const char* mqtt_server  = "ip";
const int   mqtt_port    = port;
const char* mqtt_topic   = "topic";

WiFiClient espClient;
PubSubClient client(espClient);

// =========================================================================
// 2. ΣΥΝΑΡΤΗΣΗ ΣΥΝΔΕΣΗΣ ΣΤΟ WI-FI
// =========================================================================
void setup_wifi() {
  delay(10);
  WiFi.disconnect(true);
  delay(500);
  WiFi.persistent(false);
  WiFi.mode(WIFI_STA);

  WiFi.begin(ssid, password);

  // Καθαρίζουμε τη Serial για να μην μπουκώσει κατά το boot
  while (Serial.available()) {
    Serial.read();
  }

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    yield();
  }
}

// =========================================================================
// 3. ΣΥΝΑΡΤΗΣΗ ΕΠΑΝΑΣΥΝΔΕΣΗΣ MQTT
// =========================================================================
void reconnect() {
  while (!client.connected()) {
    const char* clientId = "greenhouse-esp8266";

    if (client.connect(clientId)) {
      // Συνδέθηκε με επιτυχία!
    } else {
      for (int i = 0; i < 30; i++) {
        delay(100);
        yield();
      }
    }
  }
}

// =========================================================================
// 4. ΣΥΝΑΡΤΗΣΗ ΑΠΟΣΤΟΛΗΣ JSON
// =========================================================================
void sendSensorData(String deviceId, float value) {
  if (value == -1.00 || value == -1) {
    return;
  }

  StaticJsonDocument<128> doc;
  doc["deviceId"] = deviceId;
  doc["value"]    = value;

  char buffer[128];
  serializeJson(doc, buffer);
  client.publish(mqtt_topic, buffer);
}

// =========================================================================
// 5. SETUP
// =========================================================================
void setup() {
  // Ξεκινάμε τη Hardware Serial στα 9600
  Serial.begin(9600);
  Serial.setTimeout(50);

  delay(1000);

  setup_wifi();
  client.setServer(mqtt_server, mqtt_port);
}

// =========================================================================
// 6. LOOP
// =========================================================================
void loop() {
  yield();

  // Έλεγχος σύνδεσης MQTT
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  yield();

  // Διάβασμα πραγματικών δεδομένων από το Uno
  if (Serial.available() > 0) {
    String dataLine = Serial.readStringUntil('\n');
    dataLine.trim();

    if (dataLine.length() == 0) return;

    // Αν δεν έχει κόμμα, είναι σκουπίδι, το πετάμε
    if (dataLine.indexOf(',') == -1) {
      while(Serial.available()) Serial.read();
      return;
    }

    int comma1 = dataLine.indexOf(',');
    int comma2 = dataLine.indexOf(',', comma1 + 1);
    int comma3 = dataLine.indexOf(',', comma2 + 1);
    int comma4 = dataLine.indexOf(',', comma3 + 1);
    int comma5 = dataLine.indexOf(',', comma4 + 1);

    if (comma1 != -1 && comma2 != -1 && comma3 != -1 && comma4 != -1 && comma5 != -1) {

      float temp1 = dataLine.substring(0, comma1).toFloat();
      float hum1  = dataLine.substring(comma1 + 1, comma2).toFloat();
      float temp2 = dataLine.substring(comma2 + 1, comma3).toFloat();
      float hum2  = dataLine.substring(comma3 + 1, comma4).toFloat();
      float soil1 = dataLine.substring(comma4 + 1, comma5).toFloat();
      float soil2 = dataLine.substring(comma5 + 1).toFloat();

      // Αποστολή των πραγματικών τιμών με μικρό delay για ασφάλεια
      sendSensorData("3", temp1); delay(50); yield();
      sendSensorData("4", hum1);  delay(50); yield();
      sendSensorData("1", temp2); delay(50); yield();
      sendSensorData("2", hum2);  delay(50); yield();
      sendSensorData("5", soil1); delay(50); yield();
      sendSensorData("6", soil2); delay(50); yield();
    }

    yield();
  }
}