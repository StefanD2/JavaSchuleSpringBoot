package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PersonWohntinAdresseEntityRepository extends JpaRepository<PersonWohntinAdresseEntity, Integer>, JpaSpecificationExecutor<PersonWohntinAdresseEntity> {

    List<PersonWohntinAdresseEntity> findAllByPerson(PersonEntity personEntity);

    List<PersonWohntinAdresseEntity> findAllByAdresse(AdresseEntity adresseEntity);

}