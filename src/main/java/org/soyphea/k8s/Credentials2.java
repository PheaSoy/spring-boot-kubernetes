import org.h2.security.SHA256;

public class Credentials2 {
  String inputString = "s3cr37";
  byte[] key         = inputString.getBytes();
  SHA256.getHMAC(key, message);  // Noncompliant
}