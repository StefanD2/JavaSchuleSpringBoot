package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdresseEntityRepository extends JpaRepository<AdresseEntity, Integer>, JpaSpecificationExecutor<AdresseEntity> {

    Optional<AdresseEntity> findByIdAdresse(int idAdresse);

}