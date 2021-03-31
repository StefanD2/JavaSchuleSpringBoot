package at.ac.htlstp.deimel.springbootdemoserver.dto.database;

import lombok.Data;

import java.sql.Date;

@Data
public class PersonWohntInAdresseDTO {

    private Integer idWohntin;

    private Date seit;

    private PersonDTO person;

    private AdressDTO adresse;

}
