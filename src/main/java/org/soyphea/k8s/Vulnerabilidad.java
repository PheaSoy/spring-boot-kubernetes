
public class Vulnerabilidad {
  public static void vulnerabilidad() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    PBEParameterSpec cipherSpec = new PBEParameterSpec(salt, 10000); // Compliant
    PBEKeySpec spec = new PBEKeySpec(chars, salt, 10000, 256); // Compliant
  }
}
