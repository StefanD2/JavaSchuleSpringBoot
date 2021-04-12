package at.ac.htlstp.deimel.springbootdemoserver.service;

import at.ac.htlstp.deimel.springbootdemoserver.model.RestUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private HashMap<String, RestUser> users = new HashMap<>(); // TODO das ändern zu dantebank ?

    public CustomUserDetailsService() {
        updateUser("GUEST", "GUEST", "GUEST");
        updateUser("USER", "USER", "GUEST", "USER");
        updateUser("ADMIN", "ADMIN", "ADMIN");
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (users.containsKey(userName)) {
            RestUser restUser = users.get(userName);
            restUser.addRole("ADMIN");
            UserDetails user =
                    User.withUsername(restUser.getName())
                            .password(restUser.getEndcodedPassword())
                            .roles(restUser.getRoles()).build();
            return user;
            // TODO add userList und so (datenbank?)
        }
        throw new UsernameNotFoundException("Username " + userName + " not found");

    }

    private Collection<? extends GrantedAuthority> getAuthorities(RestUser restUser) {
        return AuthorityUtils.createAuthorityList(restUser.getRoles());
    }

    public void updateUserEncryptedPassword(String user, String encryptedPassword) {
        user = user.trim();
        encryptedPassword = encryptedPassword.trim();
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.setEndcodedPassword(encryptedPassword);
            users.put(user, restUser);
        }
    }

    public void updateUserPassword(String user, String password) {
        user = user.trim();
        password = password.trim();
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.setPassword(password);
            users.put(user, restUser);
        }
    }

    public void addUserRoles(String user, String... roles) {
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.addRole(roles);
            users.put(user, restUser);
        }
    }

    public void removeUserRoles(String user, String... roles) {
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.removeRole(roles);
            users.put(user, restUser);
        }
    }

    public void updateUser(String user, String password, String... roles) {
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.setPassword(password);
            restUser.setRoles(roles);
            users.put(user, restUser); // TODO add datenbank?
        }
    }

    public void updateUserEncrypted(String user, String enccryptedPassword, String... roles) {
        if (user.length() != 0) {
            RestUser restUser = users.getOrDefault(user, new RestUser(user, "", null));
            restUser.setEndcodedPassword(enccryptedPassword);
            restUser.setRoles(roles);
            users.put(user, restUser); // TODO add datenbank?
        }
    }


}
