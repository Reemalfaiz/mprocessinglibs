MRest Library for Mobile Processing
-----------------------------------

MRest library allows Mobile Processing to connect and retrive data from rest
services.

Two clases allow the access to the rest services, the MRequest encapsulate the
rest request, with the service url, the method and the parameters. 
The MResponse provide access to the server response giving access to the parts
of the response using a xpath like form.

  // Create the request
  MRestRequest mRequest = new MRestRequest("http://api.flickr.com/services/rest/");
  mRequest.parameter("method","flickr.test.echo");
  mRequest.parameter("value","12345");
  mRequest.parameter("api_key","xxx");
  
  // Grab the response
  MRestResponse mResponse = mRequest.waitForResponse();
  
  // Grab each element value
  println(mResponse.get("/rsp/@stat"));
  println(mResponse.get("/rsp/method/text()"));


The examples directory contains examples to show library features.

Installation :
--------------

- Copy this library to Mobile Processing libraries directory 


Enjoy!

http://mjs.darkgreenmedia.com
http://marlonj.darkgreenmedia.com

Copyright (c) 2007 Marlon J. Manrique (marlonj@darkgreenmedia.com)

$Id: README.txt 234 2007-06-05 23:15:27Z marlonj $
