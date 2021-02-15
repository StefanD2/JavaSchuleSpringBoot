package at.ac.htlstp.deimel.springbootdemoserver.repository;

import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrtEntityRepository extends JpaRepository<OrtEntity, Integer>, JpaSpecificationExecutor<OrtEntity> {

}