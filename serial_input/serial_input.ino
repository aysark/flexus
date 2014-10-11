int sensorPin = A0;    // analog input pin to hook the sensor to
int sensorValue = 0;
int ledPin = 9;
int i = 0;
double avg = 0;

void setup() { 
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600); // initialize serial communications 
}
 
void loop() {
  sensorValue = analogRead(sensorPin); // read the value from the sensor
  
  delayMicroseconds(50); // for graph
//  delayMicroseconds(250); // for pusher/audio

  if (i < 1000) {
    avg = sensorValue + avg;  
    i++;
  } else {
    avg = avg / i;
    i=0;  
    Serial.println(avg/4);
    avg = 0;
  }
}
