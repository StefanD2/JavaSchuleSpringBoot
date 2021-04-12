package at.ac.htlstp.deimel.springbootdemoserver.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestUser {

    private String name = "";

    private String endcodedPassword = "";

    private String roles[] = new String[0];

    public void setPassword(String password) {
        if (password.length() != 0)
            this.endcodedPassword = new BCryptPasswordEncoder().encode(password);
        else
            this.endcodedPassword = "";
    }

    public void addRole(String... roles) {
        for (String r : roles) {
            r = r.toUpperCase();
            if (Arrays.stream(this.roles).anyMatch(r::equals))
                continue;
            this.roles = Arrays.copyOf(this.roles, this.roles.length + 1);
            this.roles[this.roles.length - 1] = r;
        }
    }

    public void removeRole(String... roles) {
        for (String r : roles) {
            r = r.toUpperCase();
            if (!Arrays.stream(this.roles).anyMatch(r::equals))
                continue;
            this.roles = Stream.of(this.roles).dropWhile(r::equals).toArray(String[]::new);
        }
    }

}
