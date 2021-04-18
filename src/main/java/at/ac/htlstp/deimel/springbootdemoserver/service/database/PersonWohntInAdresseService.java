package at.ac.htlstp.deimel.springbootdemoserver.service.database;

import at.ac.htlstp.deimel.springbootdemoserver.dto.database.AdressDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.database.PersonDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.database.PersonWohntInAdresseDTO;
import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonWohntinAdresseEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonWohntInAdresseService {

    private final PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository;

    private final ModelMapper mapper;

    public PersonWohntInAdresseService(PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository) {
        this.personWohntinAdresseEntityRepository = personWohntinAdresseEntityRepository;
        mapper = new ModelMapper();
    }

    public List<PersonWohntInAdresseDTO> findAllByAdresse(AdressDTO adressDTO) {
        List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList = personWohntinAdresseEntityRepository.findAllByAdresse(mapper.map(adressDTO, AdresseEntity.class));
        List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList = new ArrayList<>();
        personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> personWohntInAdresseDTOList.add(mapper.map(personWohntinAdresseEntity, PersonWohntInAdresseDTO.class)));
        return personWohntInAdresseDTOList;
    }

    public List<PersonWohntInAdresseDTO> findAllByPerson(PersonDTO personDTO) {
        List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList =
                personWohntinAdresseEntityRepository.findAllByPerson(mapper.map(personDTO, PersonEntity.class));
        List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList = new ArrayList<>();
        personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> personWohntInAdresseDTOList.add(mapper.map(personWohntinAdresseEntity, PersonWohntInAdresseDTO.class)));
        return personWohntInAdresseDTOList;
    }

}
