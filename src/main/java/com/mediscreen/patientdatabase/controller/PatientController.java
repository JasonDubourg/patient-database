package com.mediscreen.patientdatabase.controller;

import com.mediscreen.patientdatabase.entity.Patient;
import com.mediscreen.patientdatabase.exception.PatientNotFoundException;
import com.mediscreen.patientdatabase.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@Api(description = "Gestion des patients")
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @ApiOperation(value = "Récupère un patient selon son ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient with id { id } doesn't exist in database")})
    @GetMapping(value = "/patient-info/{id}")
    public Patient getPatientById(@PathVariable("id") int id) {
        Patient patient = patientService.findById(id);
        if(Objects.isNull(patient)){
            throw new PatientNotFoundException("Patient with id " + id + " doesn't exist in database");
        }
        return patient;
    }

    @ApiOperation(value = "Récupère un patient selon son prénom et son nom")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient { firstName lastName } doesn't exist in database")})
    @GetMapping(value = "/patient")
    public Patient getPatient(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        Patient patient = patientService.findByFirstNameAndLastName(firstName, lastName);
        if(Objects.isNull(patient)){
            throw new PatientNotFoundException("Patient " + firstName + " " + lastName +" doesn't exist in database");
        }
        return patient;
    }

    @ApiOperation(value = "Récupère tous les patients")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Any patients saved in database")})
    @GetMapping(value = "/patients")
    public List<Patient> findAllPatients(){
        List<Patient> patientList = patientService.findAllPatients();
        if(patientList.isEmpty()){
            throw new PatientNotFoundException("Any patients saved in database");
        }
        return patientList;
    }

    @ApiOperation(value = "Sauvegarde un patient")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient { firstName lastName } already exist in database")})
    @PostMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePatient(@RequestBody Patient patient) {
        String response =  patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @ApiOperation(value = "Supprimer un patient selon son ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient with id { id } to delete doesn't exist")})
    @DeleteMapping(value = "/patient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable("id") int id){
        String response = patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @ApiOperation(value = "Met à jour un patient selon son ID")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Patient with id { id } to update doesn't exist")})
    @PutMapping(value = "/patient/{id}")
    public Patient updatePatient(@PathVariable("id") int id,  @RequestBody Patient patient){
        return patientService.updatePatient(id, patient);
    }
}
