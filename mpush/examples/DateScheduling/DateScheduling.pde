import mjs.processing.mobile.mpush.*;

// Date values to show
int yearValue, monthValue, dayValue, hourValue, 
    minuteValue,secondValue;

void setup()
{  
  // Register
  MPush mPush = new MPush(this);

  // Get current time values
  yearValue = year();
  monthValue = month() + 1;
  dayValue = day();
  hourValue = hour();
  minuteValue = minute();
  secondValue = second();
  
  // Execute this application in 1 month from now
  mPush.executeAt(yearValue,monthValue,dayValue,
                  hourValue,minuteValue,secondValue);

  // Execute this application the given date
  // mPush.executeAt(2007,3,2,15,00,00);
  
  // Set font
  textFont(loadFont());
  textAlign(CENTER); 
}

void draw()
{
  background(0);  
  translate(width/2,height/2);  
  text("Thanks for Execute Me !!!",0,0);
  text("If the application is closed",0,15);
  text("will be execute at " + yearValue + "/" + (monthValue+1) + 
      "/" + dayValue + " " + hourValue + ":" + minuteValue + ":" +
      secondValue,0,30);
}
