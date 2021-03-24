package at.ac.htlstp.deimel.springbootdemoserver.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PersonDTO {

    private Integer idPerson;

    private String vorname;

    private String famname;

    private String telefon;

    private Date geburt;

    private String email;

    private List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList;

}
