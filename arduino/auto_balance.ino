#include <Servo.h>
#include <EEPROM.h>
#define address 3
int res=500;
int deg = 75;
int speed=200;
int PIN_ESC = 9, value = 1000;
Servo srvESC;
Servo myservo;
int val = 0;
long serial_t, ser_t, esc_t;
char r='0';
void setup() {
  for(int i =4;i<=7;i++){
    pinMode(i,OUTPUT);
    digitalWrite(i,LOW);
  }
  Serial.begin(9600);
  res=word(EEPROM.read(address),EEPROM.read(address+1));
  Serial.println(res);
  srvESC.attach(9);
  myservo.attach(2);
  myservo.write(deg);

  

  srvESC.writeMicroseconds(700);
  delay(2000);
  srvESC.writeMicroseconds(1000);

  serial_t = millis();
  ser_t = serial_t;
  esc_t= serial_t;
}

void loop() {

  if(Serial.available()){
    r=Serial.read();
    switch(r){
      case 'a':
        digitalWrite(4,LOW);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        digitalWrite(7,LOW);
        break;
      case 'b':
        analogWrite(4,speed);
        digitalWrite(5,LOW);
        digitalWrite(6,LOW);
        analogWrite(7,speed);
        break;
      case 'c':
        digitalWrite(4,LOW);
        analogWrite(5,speed);
        analogWrite(6,speed );
        digitalWrite(7,LOW);
        break;
      case 's':
        res=Serial.parseInt();
        EEPROM.write(address,highByte(res));
        EEPROM.write(address+1,lowByte(res));
        // Serial.println(EEPROM.read(address));
        break;
      case 'm':
        Serial.print("m");
        Serial.println(res);
        break;
      case 'p':
        speed=Serial.parseInt();
        break;
      case 'd':
        Serial.println(speed);
        break;
    }
  }

  if (millis() - serial_t > 100) {
    Serial.print("r");
    Serial.println(val);
    serial_t = millis();
  }

  //Serial.println(val);
  srvESC.writeMicroseconds(value);

  if (millis() - esc_t > 10) {
    if (value < 2000) {
      value++;
    }
    esc_t = millis();
  }

  val = analogRead(A0);

  if (millis() - ser_t > 5) {
    if (val > res+10 && deg < 150) {
      deg++;
    }
    else if (val < res-10 && deg >0) {
      deg--;
    }
    else {  
      if (deg > 75)
        deg--;
      else if (deg < 75)
        deg++ ;
    }
    ser_t = millis();
  }

  myservo.write(deg);

}
