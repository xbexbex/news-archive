package com.lukuvinkkikirjasto.domain;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BookTest {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
    }

    @Before
    public void initializeDatabase(){
        // Create H2 dba entity manager factory
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
    }


    @Test
    public void testFindEmpty() {
        Book book = em.find(Book.class, 1);
        assertNull(book);
    }


    @Test
    public void testFind() {
        // Add a new book "test" to the db
        em.getTransaction().begin();
        em.persist(new Book());
        em.getTransaction().commit();
        Book book = em.find(Book.class, 1);
        assertNotNull(book);
    }


    @AfterClass
    public static void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}