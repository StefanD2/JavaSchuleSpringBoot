package at.ac.htlstp.deimel.springbootdemoserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ort")
@Data
public class OrtEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idort", nullable = false)
    private Integer idOrt;

    @Column(name = "PLZ", nullable = false)
    private String PLZ;

    @Column(name = "Ortsname", nullable = false)
    private String ortsname;

    @Column(name = "Telefon")
    private String telefon;

    @OneToMany(mappedBy = "ort")
    private List<AdresseEntity> adressen = new ArrayList<>();

}
