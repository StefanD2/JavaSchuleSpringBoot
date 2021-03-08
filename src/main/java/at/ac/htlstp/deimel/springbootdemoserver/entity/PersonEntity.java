package at.ac.htlstp.deimel.springbootdemoserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "person")
@Data
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperson", nullable = false)
    private Integer idPerson;

    @Column(name = "Vorname")
    private String vorname;

    @Column(name = "Famname", nullable = false)
    private String famname;

    @Column(name = "Telefon")
    private String telefon;

    @Column(name = "Geburt")
    private Date geburt;

    @Column(name = "email")
    private String email;

}
