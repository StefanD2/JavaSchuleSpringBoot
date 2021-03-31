package at.ac.htlstp.deimel.springbootdemoserver.service.database;

import at.ac.htlstp.deimel.springbootdemoserver.dto.database.PersonDTO;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonEntityRepository personEntityRepository;

    private final ModelMapper mapper;

    public PersonService(PersonEntityRepository personEntityRepository) {
        this.personEntityRepository = personEntityRepository;
        mapper = new ModelMapper();
    }

    public List<PersonDTO> findAll() {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<PersonEntity> personEntityList = personEntityRepository.findAll();
        personEntityList.forEach(personEntity -> personDTOList.add(mapper.map(personEntity, PersonDTO.class)));
        return personDTOList;
    }

    public PersonDTO findByIdPerson(int idPerson) {
        PersonEntity personEntity = personEntityRepository.findByIdPerson(idPerson).orElse(null);
        if (personEntity != null) {
            return mapper.map(personEntity, PersonDTO.class);
        }
        return null;
    }

}
