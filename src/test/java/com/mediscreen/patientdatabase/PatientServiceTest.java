package com.mediscreen.patientdatabase;

import com.mediscreen.patientdatabase.controller.PatientController;
import com.mediscreen.patientdatabase.dao.PatientDao;
import com.mediscreen.patientdatabase.entity.Patient;
import com.mediscreen.patientdatabase.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientServiceTest {

    @MockBean
    PatientDao patientDao;

    @Autowired
    PatientService patientService;

    Patient patientTest1= new Patient();
    Patient patientTest2= new Patient();
    Patient patientTest4= new Patient("John","Snow","01/01/01","Homme","address","phone");

    List<Patient> patientList = new ArrayList<>();

    @Test
    void findByIdTest(){
        Patient patientTest3 = new Patient();
        Mockito.when(patientService.findById(1)).thenReturn(patientTest3);
        assertNotNull(patientTest3);
    }

    @Test
    void findByFirstnameAndLastNameTest(){
        Mockito.when(patientService.findByFirstNameAndLastName("John", "Snow")).thenReturn(patientTest4);
        assertNotNull(patientTest4);
    }

    @Test
    void findAllPatientsTest(){
        patientList.add(patientTest1);
        patientList.add(patientTest2);
        Mockito.when(patientService.findAllPatients()).thenReturn(patientList);
        assertEquals(2, patientList.size());
    }

    @Test
    void savePatientTest(){
        String address = "address";
        Mockito.when(patientService.findByFirstNameAndLastName(patientTest4.getFirstName(), patientTest4.getLastName())).thenReturn(null);
        assertEquals(address, patientTest4.getAddress());
    }

    @Test
    void updatePatientTest(){
        patientTest4.setFirstName("Louis");
        Mockito.when(patientService.savePatient(patientTest4)).thenReturn(patientTest4);
        assertEquals("Louis", patientTest4.getFirstName());
    }

}
