package com.mediscreen.patientdatabase;

import com.mediscreen.patientdatabase.controller.PatientController;
import com.mediscreen.patientdatabase.entity.Patient;
import com.mediscreen.patientdatabase.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @DisplayName("GET : /patient-info/{id}")
    @Test
    void findPatientByIdTest() throws Exception{
        // ARRANGE
        when(patientService.findById(any(Integer.class))).thenReturn(new Patient());
        // ACT
        MvcResult mvcResult = this.mockMvc.perform(get("/patient-info/1")).andDo(print()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // ASSERT
        assertEquals(status, 200);
        verify(patientService, times(1)).findById(any(Integer.class));
    }

    @DisplayName("GET : /patient/firstName&lastName")
    @Test
    void findPatientByFirstNameAndLastNameTest() throws Exception{
        // ARRANGE
        when(patientService.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(new Patient());
        // ACT
        MvcResult mvcResult = this.mockMvc.perform(get("/patient?firstName=firstName&lastName=lastName")).andDo(print()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // ASSERT
        assertEquals(status, 200);
        verify(patientService, times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
    }

    @DisplayName("GET : /patients")
    @Test
    void findAllPatientsTest() throws Exception{
        // ARRANGE
        Patient patient1 = new Patient("lastName1", "firstName1", "dateOfBirth", "gender", "homeAddress", "phoneNumber");
        Patient patient2 = new Patient("lastName2", "firstName2", "dateOfBirth", "gender", "homeAddress", "phoneNumber");
        List<Patient> patientList = new ArrayList<Patient>();
        patientList.add(patient1);
        patientList.add(patient2);
        when(patientService.findAllPatients()).thenReturn(patientList);
        // ACT
        MvcResult mvcResult = this.mockMvc.perform(get("/patients")).andDo(print()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // ASSERT
        assertEquals(status, 200);
        verify(patientService, times(1)).findAllPatients();
    }

    @DisplayName("POST : /patient")
    @Test
    void savePatientTest() throws Exception{
        // ARRANGE
        Patient patientToSave = new Patient("lastName", "firstName", "dateOfBirth", "gender", "homeAddress", "phoneNumber");
        when(patientService.savePatient(patientToSave)).thenReturn(patientToSave);
        // ACT
        MvcResult mvcResult = mockMvc.perform(post("/patient").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"firstName\"}"))
                .andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @DisplayName("DELETE : /patient/{id}")
    @Test
    void deletePatient() throws Exception{
        // ARRANGE
        Integer id = 1;
        when(patientService.deletePatient(id)).thenReturn(any(String.class));
        // ACT
        MvcResult mvcResult = mockMvc.perform(delete("/patient/" + id).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(204, response.getStatus());
        verify(patientService, times(1)).deletePatient(id);
    }

    @DisplayName("PUT : /patient/{id}")
    @Test
    void updatePatient() throws Exception{
        // ARRANGE
        int id = 1;
        Patient patientToUpdate = new Patient("lastName", "firstName", "dateOfBirth", "gender", "homeAddress", "phoneNumber");
        when(patientService.updatePatient(id, patientToUpdate)).thenReturn(patientToUpdate);
        // ACT
        MvcResult mvcResult = mockMvc.perform(put("/patient/" + id).contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"firstName\"}")).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        // ASSERT
        assertEquals(200, response.getStatus());
    }

}
