#include <DHT.h>
#include <SoftwareSerial.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// ==========================================
//                 PINS CONFIG
// ==========================================
#define PIR_PIN     5
#define DHT_1_PIN   11
#define DHT_2_PIN   10

#define SOIL_1_PIN  A0
#define SOIL_2_PIN  A1

#define RELAY_FAN   6
#define RELAY_HEAT  7
#define RELAY_PUMP  8

#define DHTTYPE DHT22

LiquidCrystal_I2C lcd(0x27, 16, 2);
SoftwareSerial WemosSerial(2, 3);

DHT dht1(DHT_1_PIN, DHTTYPE);
DHT dht2(DHT_2_PIN, DHTTYPE);

// Χρονικά διαστήματα (σε χιλιοστά του δευτερολέπτου)
const unsigned long INTERVAL_TEST = 1 * 60 * 1000UL;  // 1 λεπτό για τα πρώτα τεστ
const unsigned long INTERVAL_PROD = 30 * 60 * 1000UL; // 30 λεπτά για μετά

unsigned long currentInterval = INTERVAL_TEST; // Ξεκινάμε με το 1 λεπτό
unsigned long previousMillis = 0;
int sendCount = 0; // Μετρητής αποστολών

float temp1, hum1, temp2, hum2;
int soil1Raw, soil2Raw;
int soil1Percent, soil2Percent;

void setup() {
  Serial.begin(9600);
  WemosSerial.begin(9600);

  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Greenhouse v4.7");
  delay(2000);
  lcd.clear();
  lcd.noBacklight();

  pinMode(PIR_PIN, INPUT);
  pinMode(RELAY_FAN, OUTPUT);
  pinMode(RELAY_HEAT, OUTPUT);
  pinMode(RELAY_PUMP, OUTPUT);

  // ΑΡΧΙΚΟΠΟΙΗΣΗ: Ξεκινάνε LOW (ΣΒΗΣΤΑ)
  digitalWrite(RELAY_FAN, LOW);
  digitalWrite(RELAY_HEAT, LOW);
  digitalWrite(RELAY_PUMP, LOW);

  dht1.begin();
  dht2.begin();

  Serial.println(F("--- Arduino Uno: Έτοιμο (v4.7 Έξυπνη Αποστολή) ---"));
}

void loop() {
  // 1. ΑΝΑΓΝΩΣΗ ΑΙΣΘΗΤΗΡΩΝ
  temp1 = dht1.readTemperature();
  hum1 = dht1.readHumidity();
  temp2 = dht2.readTemperature();
  hum2 = dht2.readHumidity();

  soil1Raw = analogRead(SOIL_1_PIN);
  soil2Raw = analogRead(SOIL_2_PIN);

  soil1Percent = map(soil1Raw, 750, 370, 0, 100);
  soil1Percent = constrain(soil1Percent, 0, 100);

  soil2Percent = map(soil2Raw, 750, 370, 0, 100);
  soil2Percent = constrain(soil2Percent, 0, 100);

  if (isnan(hum1) || isnan(temp1)) { temp1 = -1; hum1 = -1; }
  if (isnan(hum2) || isnan(temp2)) { temp2 = -1; hum2 = -1; }

  // =======================================================
  // 2. ΣΥΝΕΧΕΙΣ ΑΥΤΟΜΑΤΙΣΜΟΙ
  // =======================================================

  // 🔥 Έλεγχος Ανεμιστήρα: Ανάβει πάνω από 40.0 βαθμούς, σβήνει από κάτω
  if (temp1 > 40.0 && temp1 != -1) {
    digitalWrite(RELAY_FAN, HIGH);  // Άναψε πάνω από 40
  } else {
    digitalWrite(RELAY_FAN, LOW);   // Σβήσε κάτω από 40
  }

  // Έλεγχος Αντλίας
  if (soil1Raw > 750 || soil2Raw > 750) {
    digitalWrite(RELAY_PUMP, HIGH);
  } else {
    digitalWrite(RELAY_PUMP, LOW);
  }

  // Έλεγχος Θέρμανσης (Μόνιμα σβηστό)
  digitalWrite(RELAY_HEAT, LOW);

  // =======================================================
  // 3. ΟΘΟΝΗ LCD (ΚΑΝΟΝΙΚΗ ΛΕΙΤΟΥΡΓΙΑ ΜΕ PIR)
  // =======================================================
  int motion = digitalRead(PIR_PIN);
  if (motion == HIGH) {
    lcd.backlight();
    lcd.setCursor(0, 0);
    lcd.print("T1:"); lcd.print(temp1, 1); lcd.print("C ");
    lcd.print("S1:"); lcd.print(soil1Percent); lcd.print("%  ");

    lcd.setCursor(0, 1);
    lcd.print("S2:"); lcd.print(soil2Percent); lcd.print("% ");

    if (digitalRead(RELAY_PUMP) == HIGH) {
      lcd.print("PUMP:ON ");
    } else {
      lcd.print("PUMP:OFF");
    }
  } else {
    lcd.noBacklight();
    lcd.clear();
  }

  // =======================================================
  // 4. ΕΞΥΠΝΗ ΑΠΟΣΤΟΛΗ ΣΤΟ WEMOS
  // =======================================================
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= currentInterval) {
    previousMillis = currentMillis;

    // 🔥 4α. ΕΝΔΕΙΞΗ ΣΤΗΝ LCD ΟΤΙ ΣΤΕΛΝΕΙ
    lcd.backlight();
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("--- SENDING ---");
    lcd.setCursor(0, 1);
    lcd.print("---  DATA  ---");

    // 4β. ΠΡΑΓΜΑΤΙΚΗ ΑΠΟΣΤΟΛΗ ΔΕΔΟΜΕΝΩΝ
    WemosSerial.print(temp1);        WemosSerial.print(",");
    WemosSerial.print(hum1);         WemosSerial.print(",");
    WemosSerial.print(temp2);        WemosSerial.print(",");
    WemosSerial.print(hum2);         WemosSerial.print(",");
    WemosSerial.print(soil1Percent); WemosSerial.print(",");
    WemosSerial.println(soil2Percent);

    // Εκτύπωση και στο Serial Monitor του Uno για έλεγχο
    Serial.print(F("📡 Στάλθηκε (No ")); Serial.print(sendCount + 1); Serial.print(F(") -> "));
    Serial.print(F("🏠 Μέσα: ")); Serial.print(temp1, 1); Serial.print(F("°C | "));
    Serial.print(F("🌱 Χώμα S1: ")); Serial.print(soil1Percent); Serial.print(F("%\n"));

    // Κρατάμε το μήνυμα στην οθόνη για 2 δευτερόλεπτα
    delay(2000);
    lcd.clear();
    if (digitalRead(PIR_PIN) == LOW) {
      lcd.noBacklight(); // Σβήνει η οθόνη αν δεν υπάρχει κίνηση
    }

    // 4γ. ΕΛΕΓΧΟΣ ΜΕΤΡΗΤΗ ΓΙΑ ΑΛΛΑΓΗ ΧΡΟΝΟΥ
    sendCount++;
    if (sendCount == 5) {
      currentInterval = INTERVAL_PROD; // Μετά από 5 αποστολές, γυρίζει στα 30 λεπτά
      Serial.println(F("⏳ Τα πρώτα 5 τεστ έγιναν! Αλλαγή χρόνου αποστολής σε κάθε 30 λεπτά."));
    }
  }
}