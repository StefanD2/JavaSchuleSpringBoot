package at.ac.htlstp.deimel.springbootdemoserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrtDTO {

    private Integer idOrt;

    private String PLZ;

    private String ortsname;

    private String telefon;

    private List<AdressDTO> adressen;

}
