package com.mediscreen.patientdatabase.service;

import com.mediscreen.patientdatabase.dao.PatientDao;
import com.mediscreen.patientdatabase.entity.Patient;
import com.mediscreen.patientdatabase.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PatientService {

    @Autowired
     PatientDao patientDao;

    public Patient findById(int id){
         return patientDao.findById(id);
    }

    public Patient findByFirstNameAndLastName(String firstName, String lastName){
        return patientDao.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Patient> findAllPatients() {
        return patientDao.findAll();
    }

    public Patient savePatient(Patient patient){
        Patient patientDatabase = findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
        if(Objects.isNull(patientDatabase)){
            patientDao.save(patient);
            patientDatabase = findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
        } else {
            throw new PatientNotFoundException("Patient " + patient.getFirstName() + " " + patient.getLastName() + " already exist in database");
        }
        return patientDatabase;
    }

    public String deletePatient(int id){
        Patient patientDeleted = findById(id);
        String result = "Patient successfully deleted";
        if(Objects.isNull(patientDeleted)){
            throw new PatientNotFoundException("Patient with id " + id + " to delete doesn't exist");
        } else {
            patientDao.delete(patientDeleted);
        }
        return result;
    }

    public Patient updatePatient(int id, Patient patient){
        Patient patientUpdated = findById(id);
        if(Objects.isNull(patientUpdated)){
            throw new PatientNotFoundException("Patient with id " + id + " to update doesn't exist");
        }
        if(!Objects.isNull(patientUpdated)){
            patientUpdated.setFirstName(patient.getFirstName());
            patientUpdated.setLastName(patient.getLastName());
            patientUpdated.setDateOfBirth(patient.getDateOfBirth());
            patientUpdated.setGender(patient.getGender());
            patientUpdated.setAddress(patient.getAddress());
            patientUpdated.setPhone(patient.getPhone());
            patientDao.save(patientUpdated);
        }
        return patientUpdated;
    }
}
