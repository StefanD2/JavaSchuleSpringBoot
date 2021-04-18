package at.ac.htlstp.deimel.springbootdemoserver.dto.database;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.stream.Stream;

@Data
public class UserDTO {

    private Integer idUser;

    private String username;

    private String encryptedPassword;

    private String[] roles = new String[0];

    public void setPassword(String password) {
        if (password.length() != 0)
            this.encryptedPassword = new BCryptPasswordEncoder().encode(password);
        else
            this.encryptedPassword = "";
    }

    public void addRole(String... roles) {
        for (String r : roles) {
            r = r.toUpperCase();
            if (Arrays.asList(this.roles).contains(r))
                continue;
            this.roles = Arrays.copyOf(this.roles, this.roles.length + 1);
            this.roles[this.roles.length - 1] = r;
        }
    }

    public void removeRole(String... roles) {
        for (String r : roles) {
            r = r.toUpperCase();
            if (Arrays.stream(this.roles).noneMatch(r::equals))
                continue;
            this.roles = Stream.of(this.roles).dropWhile(r::equals).toArray(String[]::new);
        }
    }

    public String getRolesString() {
        StringBuilder out = new StringBuilder();
        for (String r : roles)
            out.append(r).append(", ");
        return out.substring(0, out.length() - 2);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[Username: " + username + ", Roles: [");
        for (String s : roles)
            out.append(s).append(",");
        out.append("]]");
        return out.toString();
    }

}
