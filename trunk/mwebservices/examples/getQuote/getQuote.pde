import mjs.processing.mobile.mwebservices.*;

MWebService wService;

String endPoint = "http://services.xmethods.net/soap";
String namespace = "urn:xmethods-delayed-quotes";
String method = "getQuote";

void setup()
{
  noLoop();
}

void keyPressed()
{
  new Thread()
  {
    public void run()
    {
      wService = new MWebService(endPoint,namespace,method);
      wService.addParameter("symbol","sunw");
      
      println("Return : " + wService.call());
    }
  }.start();
}
