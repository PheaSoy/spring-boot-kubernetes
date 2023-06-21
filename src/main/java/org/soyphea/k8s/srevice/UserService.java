package org.soyphea.k8s.srevice;

import org.soyphea.k8s.domain.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.security.SecureRandom;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.io.IOException;

import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    List<User> users = Arrays.asList(new User(1, "Dara"), new User(2, "Seyha"));

    public List<User> getUser(String containName) {

        return users.stream().filter(user -> user.getName().contains(containName)).collect(Collectors.toList());
    }
    
    public static void vulnerabilidad() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        char[] chars = {'r', 's', 't', 'u', 'v'};
        PBEParameterSpec cipherSpec = new PBEParameterSpec(salt, 10000); // Compliant
        PBEKeySpec spec = new PBEKeySpec(chars, salt, 10000, 256); // Compliant
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String xml = "<node id=\"" + req.getParameter("id") + "\"></node>";
        FileOutputStream fos = new FileOutputStream("output.xml");
        fos.write(xml.getBytes(Charset.forName("UTF-8")));  // Noncompliant
    }

}
