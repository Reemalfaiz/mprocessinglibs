import mjs.processing.mobile.mrest.*;

/**
 * Yahoo Local Search example
 *
 * $Id: YahooLocalSearch.pde 234 2007-06-05 23:15:27Z marlonj $
 */
 
MRequest mRequest;

void setup()
{
  mRequest = new MRequest(this,"http://local.yahooapis.com/LocalSearchService/V3/localSearch");
  mRequest.parameter("appid","YcqpwRfV34HsjH29JFxu4vwaV6R_vSU2AwUU1m7EKBALeMHusw7FYQMyDPytmc7pjXMGfA--");
  mRequest.parameter("query","bike");
  mRequest.parameter("zip","90210");
  mRequest.send();
}

void libraryEvent(Object library, int event, Object data)
{
  if(library == mRequest)
  {
     MResponse mResponse = (MResponse) data;
     println("num results: " + int(mResponse.get("/ResultSet/@totalResultsReturned")));
     
     MResult[] results = mResponse.getResults("/ResultSet/Result");
     
     for(int i=0; i<results.length; i++)
     {
       println(results[i].get("/Result/@id"));
       println(results[i].get("/Result/Title/text()"));
       println(results[i].get("/Result/Address/text()"));
     }
  }
}
