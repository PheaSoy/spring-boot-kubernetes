package org.soyphea.k8s.srevice;

import org.soyphea.k8s.domain.User;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.security.SecureRandom;
import javax.crypto.spec.PBEParameterSpec;

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

        PBEParameterSpec cipherSpec = new PBEParameterSpec(salt, 10000); // Compliant
        PBEKeySpec spec = new PBEKeySpec(chars, salt, 10000, 256); // Compliant
    }

}
