package at.ac.htlstp.deimel.springbootdemoserver.dto.database;

import lombok.Data;

import java.util.List;

@Data
public class AdressDTO {

    private Integer idAdresse;

    private String strasse;

    private String hausNr;

    private OrtDTO ort;

    private List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList;

}
