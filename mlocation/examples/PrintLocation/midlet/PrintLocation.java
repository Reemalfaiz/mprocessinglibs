import processing.core.*; import mjs.processing.mobile.mlocation.*; public class PrintLocation extends PMIDlet{public void setup() {
				
double[] coords = new double[2];
MLocation.location(coords);

int latitude = (int) (coords[0] * 10000);
int longitude = (int) (coords[1] * 10000);

text("Latitude : " + latitude,10,10);
text("Longitude : " + longitude,10,20);

noLoop(); }}