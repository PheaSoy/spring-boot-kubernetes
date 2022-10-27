package org.soyphea.k8s;

public class Credentials2 {
  public void getCredentials() {
    String inputString = "s3cr37";
    byte[] key         = inputString.getBytes();
  }
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String input = req.getParameter("input");

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");
    engine.eval(input); // Noncompliant
  }
}
