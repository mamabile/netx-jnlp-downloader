package netx.jnlp.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.security.MessageDigest;

public class MD5Checksum
{

  public static String FILENOTFOUND = "fnf";
    
  private static byte[] createChecksum(String filename) throws Exception
  {
    InputStream fis = new FileInputStream(filename);

    byte[] buffer = new byte[1024];
    MessageDigest complete = MessageDigest.getInstance("MD5");
    int numRead;

    do
    {
      numRead = fis.read(buffer);
      if(numRead > 0)
      {
        complete.update(buffer, 0, numRead);
      }
    }
    while(numRead != -1);

    fis.close();
    return complete.digest();
  }

  // see this How-to for a faster way to convert
  // a byte array to a HEX string
  public static String getMD5Checksum(String filename) throws Exception
  {
    byte[] b = createChecksum(filename);
    String result = "";

    for(int i = 0; i < b.length; i++)
    {
      result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
    }
    return result;
  }


  public static String getMD5ChecksumServer(String urlResource) throws Exception {
    //System.out.println("getMD5ChecksumServer() urlResource="+urlResource);
    String checkSum = null;

    try {
       URL url = new URL(urlResource);
       URLConnection connection = url.openConnection();
       connection.connect();

       // download the file
       InputStream input = new BufferedInputStream(url.openStream());

       byte bytes[] = new byte[1024];

       input.read(bytes);
       checkSum = new String(bytes, "UTF-8").trim();
       input.close();

       return checkSum;
    }
    catch(MalformedURLException e)
    {
       System.out.println("IOException e="+e);
       return "";
    }
    catch(FileNotFoundException e)
    {
       //System.out.println("FileNotFoundException e="+e);
       return FILENOTFOUND;
    }    
    catch(IOException e)
    {
       System.out.println("IOException e="+e);
       return "";
    }
  }
}

