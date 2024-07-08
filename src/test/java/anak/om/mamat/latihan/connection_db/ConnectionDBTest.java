package anak.om.mamat.latihan.connection_db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectionDBTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void testConnectionDB(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();
        entityManager.getTransaction().rollback();
        entityManager.close();

    }

}
