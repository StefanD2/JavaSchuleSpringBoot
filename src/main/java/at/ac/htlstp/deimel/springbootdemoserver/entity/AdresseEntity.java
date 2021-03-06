package at.ac.htlstp.deimel.springbootdemoserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adresse")
@Data
public class AdresseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadresse", nullable = false)
    private Integer idAdresse;

    @Column(name = "Strasse", length = 40) // default länge, wenn datenbank generiert wird
    private String strasse;

    @Column(name = "Hausnr")
    private String hausNr;

    @ManyToOne
    @JoinColumn(name = "idort")
    private OrtEntity ort;

    @OneToMany(mappedBy = "adresse")
    private List<PersonWohntinAdresseEntity> personWohntinAdresse = new ArrayList<>();

}
