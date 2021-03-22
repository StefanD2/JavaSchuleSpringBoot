package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonEntityRepository extends JpaRepository<PersonEntity, Integer>, JpaSpecificationExecutor<PersonEntity> {

    Optional<PersonEntity> findByIdPerson(int idPerson);
}