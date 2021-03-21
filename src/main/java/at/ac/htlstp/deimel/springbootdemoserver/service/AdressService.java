package at.ac.htlstp.deimel.springbootdemoserver.service;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.AdresseEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class AdressService {

    private final AdresseEntityRepository adresseEntityRepository;

    public AdressService(AdresseEntityRepository adresseEntityRepository) {
        this.adresseEntityRepository = adresseEntityRepository;
    }

    public String getOrtsnameFromAdressID(int idAdresse) {
        AdresseEntity adresseEntity = adresseEntityRepository.findByIdAdresse(idAdresse).orElse(null);
        if (adresseEntity != null)
            return adresseEntity.getOrt().getOrtsname();
        return null;
    }

}
