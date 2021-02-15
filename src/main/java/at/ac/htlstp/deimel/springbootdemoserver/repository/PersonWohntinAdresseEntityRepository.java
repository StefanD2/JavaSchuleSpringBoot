package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonWohntinAdresseEntityRepository extends JpaRepository<PersonWohntinAdresseEntity, Integer>, JpaSpecificationExecutor<PersonWohntinAdresseEntity> {

}