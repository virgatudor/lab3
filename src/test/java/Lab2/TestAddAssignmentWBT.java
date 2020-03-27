package Lab2;

import Lab2.domain.Student;
import Lab2.domain.Tema;
import Lab2.repository.NotaXMLRepository;
import Lab2.repository.StudentXMLRepository;
import Lab2.repository.TemaXMLRepository;
import Lab2.service.Service;
import Lab2.validation.NotaValidator;
import Lab2.validation.StudentValidator;
import Lab2.validation.TemaValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.internal.Assignments;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestAddAssignmentWBT {
    Service service;
    StudentXMLRepository studentRepository;
    TemaXMLRepository assignmentRepository;
    NotaXMLRepository gradeRepository;

    @Before
    public void init(){
        studentRepository = new StudentXMLRepository(new StudentValidator(), "studenti.xml");
        assignmentRepository = new TemaXMLRepository(new TemaValidator(), "teme.xml");
        gradeRepository = new NotaXMLRepository(new NotaValidator(), "note.xml");
        service = new Service(studentRepository, assignmentRepository, gradeRepository);
    }

    @Test
    public void testAddAssignment()
    {
        String idAssignment = "35";
        String description = "description";
        int deadline = 2;
        int startline = 1;
        Tema assignment = new Tema(idAssignment, description, deadline, startline);

        assertNull(assignmentRepository.findOne(idAssignment));
        assignmentRepository.save(assignment);
        assertNotNull(assignmentRepository.findOne(idAssignment));
        assertEquals(assignmentRepository.findOne(idAssignment).getDescriere(),description);
    }

   @Test
    public void testAddAssignmentInvalid()
    {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String id = null;
        String description = "description";
        int deadline = 1;
        int startline = 12;
        Tema assigment = new Tema(id, description, deadline, startline);
        assignmentRepository.save(assigment);

        System.out.print(outContent.toString());


        try {
            assertNull(assignmentRepository.findOne(id));
            assert(false);
        } catch (IllegalArgumentException e){
            assertEquals("ID-ul nu poate fi null! \n", e.getMessage());
        }
    }
}