package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AdresseEntityRepository extends JpaRepository<AdresseEntity, Integer>, JpaSpecificationExecutor<AdresseEntity> {

    List<AdresseEntity> findAllByStrasse(String strasse);

    List<AdresseEntity> findAllByOrt(OrtEntity ortEntity);

    Optional<AdresseEntity> findByIdAdresse(int idAdresse);

}