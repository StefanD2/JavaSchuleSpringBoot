package at.ac.htlstp.deimel.springbootdemoserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "adresse")
@Data
public class AdresseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadresse", nullable = false)
    private Integer idAdresse;

    @Column(name = "Strasse", length = 40) // default länge, wenn datenbank generiert wird
    private String strasse;

    @Column(name = "Hausnr")
    private String hausNr;

    // @Column(name = "idOrt", nullable = false)
    // private Integer idOrt;

    @ManyToOne
    @JoinColumn(name="idort")
    private OrtEntity ort;

}
