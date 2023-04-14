void setup() {
  pinMode(10,OUTPUT); //Led pin
  pinMode(2,INPUT_PULLUP); //LeftButton pin
  pinMode(3,INPUT_PULLUP); //UpButton pin
  pinMode(4,INPUT_PULLUP); //RightButton pin
  pinMode(5,INPUT_PULLUP); //DownButton pin 
  pinMode(9,INPUT_PULLUP); //SelectButton pin
  pinMode(13,OUTPUT); //Buzzer pin
  Serial.begin(9600);
}
void loop() {
  if(digitalRead(2)==LOW){
    Serial.write("LEFT");
    delay(500);
  }
  if(digitalRead(3)==LOW){
    Serial.write("UP");
    delay(500);
  }
  if(digitalRead(4)==LOW){
    Serial.write("RIGHT");
    delay(500);
  }
  if(digitalRead(5)==LOW){
    Serial.write("DOWN");
    delay(500);
  }
  if(digitalRead(9)==LOW){
    Serial.write("SELECT");
    digitalWrite(13,HIGH);
    delay(250);
    digitalWrite(13,LOW);
    delay(250);
  }
  if(Serial.available()){
    String message = Serial.readString();
    if (message=="FLAG"){
      digitalWrite(10,HIGH);
      delay(1000);
      digitalWrite(10,LOW);
      delay(1000); 
      noTone(12);
    }  
    if (message=="MINE"){
      tone(12, 1000);
      delay(1000);
      tone(12, 1000);
      noTone(12);
    }
  }
}