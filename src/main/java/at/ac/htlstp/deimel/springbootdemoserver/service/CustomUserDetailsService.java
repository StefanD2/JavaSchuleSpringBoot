package at.ac.htlstp.deimel.springbootdemoserver.service;

import at.ac.htlstp.deimel.springbootdemoserver.dto.database.UserDTO;
import at.ac.htlstp.deimel.springbootdemoserver.service.database.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDTO userDTO;
        if ((userDTO = userService.findByUsername(userName)) != null) {
            return User.withUsername(userDTO.getUsername())
                    .password(userDTO.getEncryptedPassword())
                    .roles(userDTO.getRoles()).build();
        }
        throw new UsernameNotFoundException("Username " + userName + " not found");

    }

    public void updateUserEncryptedPassword(String username, String encryptedPassword) {
        username = username.trim();
        encryptedPassword = encryptedPassword.trim();
        if (username.length() != 0) {
            UserDTO user = userService.findByUsername(username);
            if (user == null) {
                return;
            }
            user.setEncryptedPassword(encryptedPassword);
            userService.save(user);
        }
    }

    public void updateUserPassword(String username, String password) {
        username = username.trim();
        password = password.trim();
        if (username.length() != 0) {
            UserDTO user = userService.findByUsername(username);
            if (user == null)
                return;
            user.setPassword(password);
            userService.save(user);
        }
    }

    public void addUserRoles(String username, String... roles) {
        if (username.length() != 0) {
            UserDTO user = userService.findByUsername(username);
            if (user == null)
                return;
            user.addRole(roles);
            userService.save(user);
        }
    }

    public void removeUserRoles(String username, String... roles) {
        if (username.length() != 0) {
            UserDTO user = userService.findByUsername(username);
            if (user == null)
                return;
            user.removeRole(roles);
            userService.save(user);
        }
    }

}
