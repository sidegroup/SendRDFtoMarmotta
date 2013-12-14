import java.io.File;
import java.io.FileReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;


public class SendHttpClient {
  public static void main(String[] args) throws Exception {  
    HttpClient httpclient = new DefaultHttpClient();
    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

    HttpPost httppost = new HttpPost("http://localhost:8080/marmotta/import/upload");// ?context=http%3A%2F%2Fexample.org");
    File file = new File("SpeedProject2.rdf");
   
    int len;
    char[] chr = new char[4096];
    StringBuffer buffer = new StringBuffer();
    FileReader reader = new FileReader(file);
    try {
        while ((len = reader.read(chr)) > 0) {
            buffer.append(chr, 0, len);
        }
    } finally {
        reader.close();
    }
    
    StringEntity input = new StringEntity(buffer.toString());
    System.out.println(buffer.toString());
    input.setContentType("application/rdf+xml");
    httppost.setEntity(input);
    
    
    System.out.println("executing request " + httppost.getRequestLine());
    HttpResponse response = httpclient.execute(httppost);
    HttpEntity resEntity = response.getEntity();

    System.out.println(response.getStatusLine());
    if (resEntity != null) {
      System.out.println(EntityUtils.toString(resEntity));
    }
    if (resEntity != null) {
      resEntity.consumeContent();
    }

    httpclient.getConnectionManager().shutdown();
  }
}