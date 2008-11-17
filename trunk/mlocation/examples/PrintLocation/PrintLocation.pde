import javax.microedition.lcdui.*;

import mjs.processing.mobile.mlocation.*;

textFont(new PFont(Font.getDefaultFont()));
textAlign(CENTER);

double[] coords = new double[2];
MLocation.location(coords);

int latitude = (int) (coords[0] * 10000);
int longitude = (int) (coords[1] * 10000);

text("Latitude : " + latitude,width/2,10);
text("Longitude : " + longitude,width/2,20);

