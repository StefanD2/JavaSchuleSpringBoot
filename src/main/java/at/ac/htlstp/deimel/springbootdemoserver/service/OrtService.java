package at.ac.htlstp.deimel.springbootdemoserver.service;

import at.ac.htlstp.deimel.springbootdemoserver.dto.OrtDTO;
import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.OrtEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrtService {

    private final OrtEntityRepository ortEntityRepository;

    private final ModelMapper mapper;

    public OrtService(OrtEntityRepository ortEntityRepository) {
        this.ortEntityRepository = ortEntityRepository;
        mapper = new ModelMapper();
    }

    public List<OrtDTO> findAll() {
        List<OrtDTO> ortDTOList = new ArrayList<>();
        List<OrtEntity> ortEntityList = ortEntityRepository.findAll();
        ortEntityList.forEach(ortEntity -> ortDTOList.add(mapper.map(ortEntity, OrtDTO.class)));
        return ortDTOList;
    }

    public OrtDTO findByIdOrt(int idOrt) {
        OrtEntity ortEntity = ortEntityRepository.findByIdOrt(idOrt).orElse(null);
        if (ortEntity != null) {
            return mapper.map(ortEntity, OrtDTO.class);
        }
        return null;
    }

}
