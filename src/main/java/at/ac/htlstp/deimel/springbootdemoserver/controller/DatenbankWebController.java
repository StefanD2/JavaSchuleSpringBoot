package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.AdresseEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.OrtEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/datenbank")
public class DatenbankWebController {

    private final AdresseEntityRepository adresseEntityRepository;
    private final OrtEntityRepository ortEntityRepository;
    private final PersonEntityRepository personEntityRepository;

    public DatenbankWebController(AdresseEntityRepository adresseEntityRepository, OrtEntityRepository ortEntityRepository, PersonEntityRepository personEntityRepository) {
        this.adresseEntityRepository = adresseEntityRepository;
        this.ortEntityRepository = ortEntityRepository;
        this.personEntityRepository = personEntityRepository;
    }

    @RequestMapping("")
    public String datenbank(Model model) {
        return "datenbank";
    }

    @PostMapping(value = "/getTable", consumes = "text/plain")
    public ResponseEntity<String> getTable(@RequestBody String value) {
        String out;
        if (value.equals("adresse")) {
            List<AdresseEntity> adresseEntityList = adresseEntityRepository.findAll();
            out = "<tr><th>Straße</th><th>Hausnummer</th><th>Ort</th></tr>";
            for (AdresseEntity adresseEntity : adresseEntityList) {
                out += "<tr><td>" + adresseEntity.getStrasse() + "</td><td>" + adresseEntity.getHausNr() + "</td>" +
                        "<td>" +
                        adresseEntity.getOrt().getPLZ() + " " + adresseEntity.getOrt().getOrtsname() + "</td" +
                        "></tr>";
            }
            return ResponseEntity.ok(out);
        } else if (value.equals("ort")) {
            List<OrtEntity> ortEntityList = ortEntityRepository.findAll();
            out = "<tr><th>PLZ</th><th>Ortsname</th><th>Vorwahl</th></tr>";
            for (OrtEntity ortEntity : ortEntityList) {
                out += "<tr><td>" + ortEntity.getPLZ() + "</td><td>" + ortEntity.getOrtsname() + "</td><td>" + ortEntity.getTelefon() + "</td></tr>";
            }
        } else if (value.equals("person")) {
            List<PersonEntity> personEntityList = personEntityRepository.findAll();
            out = "<tr><th>Vorname</th><th>Familienname</th><th>Geburtsdatum</th><th>Telefon</th><th>E-Mail</th></tr>";
            for (PersonEntity personEntity : personEntityList) {
                out += "<tr><td>" + personEntity.getVorname() + "</td><td>" + personEntity.getFamname() + "</td><td>" + personEntity.getGeburt() + "</td><td>" + personEntity.getTelefon() + "</td><td>" + personEntity.getEmail() + "</td></tr>";
            }
        } else {
            return ResponseEntity.ok("Table not found");
        }
        return ResponseEntity.ok(out);
    }

}
