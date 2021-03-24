package at.ac.htlstp.deimel.springbootdemoserver.service;

import at.ac.htlstp.deimel.springbootdemoserver.dto.AdressDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.PersonWohntInAdresseDTO;
import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.AdresseEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdressService {

    private final AdresseEntityRepository adresseEntityRepository;

    private final ModelMapper mapper;

    public AdressService(AdresseEntityRepository adresseEntityRepository) {
        this.adresseEntityRepository = adresseEntityRepository;
        mapper = new ModelMapper();
    }

    public List<AdressDTO> findAll() {
        List<AdressDTO> adressDTOList = new ArrayList<>();
        List<AdresseEntity> adresseEntityList = adresseEntityRepository.findAll();
        adresseEntityList.forEach(adresseEntity -> adressDTOList.add(mapper.map(adresseEntity, AdressDTO.class)));
        return adressDTOList;
    }

    public AdressDTO findByIdAdresse(int idAdresse) {
        AdresseEntity adresseEntity = adresseEntityRepository.findByIdAdresse(idAdresse).orElse(null);
        if (adresseEntity != null) {
            return mapper.map(adresseEntity, AdressDTO.class);
        }
        return null;
    }

    public List<AdressDTO> findAllByPersonWohntInAdresse(List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList) {
        List<AdressDTO> adressDTOList = new ArrayList<>();
        personWohntInAdresseDTOList.forEach(personWohntInAdresseDTO -> adressDTOList.add(mapper.map(personWohntInAdresseDTO.getPerson(), AdressDTO.class)));
        return adressDTOList;
    }

}
