package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.AdresseEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.OrtEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonWohntinAdresseEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/datenbank")
public class DatenbankWebController {

    private final AdresseEntityRepository adresseEntityRepository;
    private final OrtEntityRepository ortEntityRepository;
    private final PersonEntityRepository personEntityRepository;
    private final PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository;

    public DatenbankWebController(AdresseEntityRepository adresseEntityRepository, OrtEntityRepository ortEntityRepository, PersonEntityRepository personEntityRepository, PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository) {
        this.adresseEntityRepository = adresseEntityRepository;
        this.ortEntityRepository = ortEntityRepository;
        this.personEntityRepository = personEntityRepository;
        this.personWohntinAdresseEntityRepository = personWohntinAdresseEntityRepository;
    }

    @PostMapping(value = "/getTable", consumes = "text/plain")
    public ResponseEntity<String> getTable(@RequestBody String value) {
        String out;
        if (value.equals("adresse")) {
            List<AdresseEntity> adresseEntityList = adresseEntityRepository.findAll();
            out = adressenListToHtmlTable(adresseEntityList, true);
            return ResponseEntity.ok(out);
        } else if (value.equals("ort")) {
            List<OrtEntity> ortEntityList = ortEntityRepository.findAll();
            out = ortListToHtmlTable(ortEntityList, true);
        } else if (value.equals("person")) {
            List<PersonEntity> personEntityList = personEntityRepository.findAll();
            out = personListToHtmlTable(personEntityList, true);
        } else {
            return ResponseEntity.ok("Table not found");
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping(value = "/getPersonInAdress", consumes = "text/plain")
    public ResponseEntity<String> getPersonInAdress(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            AdresseEntity adresseEntity = adresseEntityRepository.findByIdAdresse(id).get();
            String out =
                    "<span>Personen in " + adresseEntity.getStrasse() + " " + adresseEntity.getHausNr() + ", " + adresseEntity.getOrt().getPLZ() + " " + adresseEntity.getOrt().getOrtsname() +
                            ":</span><br><br>";
            List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList =
                    personWohntinAdresseEntityRepository.findAllByAdresse(adresseEntity);
            List<PersonEntity> personEntityList = new ArrayList<>();
            personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> personEntityList.add(personWohntinAdresseEntity.getPerson()));
            out += "<table>" + personListToHtmlTable(personEntityList, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @PostMapping(value = "/getAdressenForPerson", consumes = "text/plain")
    public ResponseEntity<String> getAdressenForPerson(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            PersonEntity personEntity = personEntityRepository.findByIdPerson(id).get();
            String out =
                    "<span>Adressen für " + personEntity.getVorname() + " " + personEntity.getFamname() + ", geboren " +
                            "am " + personEntity.getGeburt() +
                            ":</span><br><br>";
            List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList =
                    personWohntinAdresseEntityRepository.findAllByPerson(personEntity);
            List<AdresseEntity> adresseEntityList = new ArrayList<>();
            personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> adresseEntityList.add(personWohntinAdresseEntity.getAdresse()));
            out += "<table>" + adressenListToHtmlTable(adresseEntityList, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @PostMapping(value = "/getAdressenInOrt", consumes = "text/plain")
    public ResponseEntity<String> getAdressenInOrt(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            OrtEntity ortEntity = ortEntityRepository.findByIdOrt(id).get();
            String out =
                    "<span>Adressen für " + ortEntity.getPLZ() + " " + ortEntity.getOrtsname() +
                            ":</span><br><br>";
            List<AdresseEntity> adresseEntityList = ortEntity.getAdressen();
            out += "<table>" + adressenListToHtmlTable(adresseEntityList, false, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    private String adressenListToHtmlTable(List<AdresseEntity> adresseEntityList, boolean get) {
        return adressenListToHtmlTable(adresseEntityList, get, true);
    }

    private String adressenListToHtmlTable(List<AdresseEntity> adresseEntityList, boolean get, boolean ort) {
        String out = "<tr><th>Straße</th><th>Hausnummer</th>" + (ort ?
                "<th>Ort</th>" : "") + (get ? "<th>Personen</th>" : "") +
                "</tr>";
        for (AdresseEntity adresseEntity : adresseEntityList) {
            out += "<tr><td>" + adresseEntity.getStrasse() + "</td><td>" + adresseEntity.getHausNr() + "</td>" +
                    (ort ? "<td>" +
                            adresseEntity.getOrt().getPLZ() + " " + adresseEntity.getOrt().getOrtsname() + "</td>" : "") +
                    (get ? "<td><button onclick=\"getPersonInAdress('" + adresseEntity.getIdAdresse() + "')\">get" +
                            "</button>" : "") + "</td>" +
                    "</tr>";
        }
        return out;
    }

    private String personListToHtmlTable(List<PersonEntity> personEntityList, boolean get) {
        String out = "<tr><th>Vorname</th><th>Familienname</th><th>Geburtsdatum</th><th>Telefon</th><th>E-Mail</th>" +
                (get ? "<th>Adressen</th>" : "") + "</tr>";
        for (PersonEntity personEntity : personEntityList) {
            out += "<tr><td>" + personEntity.getVorname() + "</td><td>" + personEntity.getFamname() + "</td><td>" + personEntity.getGeburt() +
                    "</td><td>" + personEntity.getTelefon() + "</td><td>" + personEntity.getEmail() + "</td>" +
                    (get ? "<th><button onclick=\"getAdressenForPerson('" + personEntity.getIdPerson() + "')\">get" +
                            "</button></th>" : "") + "</tr>";
        }
        return out;
    }

    private String ortListToHtmlTable(List<OrtEntity> ortEntityList, boolean get) {
        String out = "<tr><th>PLZ</th><th>Ortsname</th><th>Vorwahl</th> " + (get ?
                "<th>Adressen</th>" : "") + "</tr>";
        for (OrtEntity ortEntity : ortEntityList) {
            out += "<tr><td>" + ortEntity.getPLZ() + "</td><td>" + ortEntity.getOrtsname() + "</td><td>" + ortEntity.getTelefon() + "</td>" + (get ? "<td><button onclick=\"getAdressenInOrt('" + ortEntity.getIdOrt() + "')\">get" +
                    "</button></td>" : "") + "</tr>";
        }

        return out;
    }

}
