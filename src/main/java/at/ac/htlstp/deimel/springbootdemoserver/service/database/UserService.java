package at.ac.htlstp.deimel.springbootdemoserver.service.database;

import at.ac.htlstp.deimel.springbootdemoserver.dto.database.UserDTO;
import at.ac.htlstp.deimel.springbootdemoserver.entity.UserEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<UserEntity> userEntityList = userEntityRepository.findAll();
        userEntityList.forEach(userEntity -> userDTOList.add(map(userEntity)));
        return userDTOList;
    }

    public UserDTO findByID(int id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElse(null);
        if (userEntity != null)
            return map(userEntity);
        return null;
    }

    public UserDTO findByUsername(String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElse(null);
        if (userEntity != null) {
            return map(userEntity);
        }
        return null;
    }

    public void save(UserDTO userDTO) {
        UserEntity userEntity = map(userDTO);
        userEntityRepository.saveAndFlush(userEntity);
    }

    public UserDTO map(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(userEntity.getIdUser());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEncryptedPassword(userEntity.getPassword());
        userDTO.setRoles(userEntity.getRole().split(";"));
        return userDTO;
    }

    public UserEntity map(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(userDTO.getIdUser());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getEncryptedPassword());
        StringBuilder role = new StringBuilder();
        for (String r : userDTO.getRoles())
            role.append(r).append(";");
        userEntity.setRole(role.toString());
        return userEntity;
    }

}
