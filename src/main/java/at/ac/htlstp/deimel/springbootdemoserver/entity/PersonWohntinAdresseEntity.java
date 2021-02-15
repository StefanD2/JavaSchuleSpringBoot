package at.ac.htlstp.deimel.springbootdemoserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "person_wohntin_adresse")
public class PersonWohntinAdresseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWohntin", nullable = false)
    private Integer idWohntin;

    @Column(name = "seit")
    private Date seit;

    @Column(name = "idPerson", nullable = false)
    private Integer idPerson;

    @Column(name = "idAdresse", nullable = false)
    private Integer idAdresse;

}