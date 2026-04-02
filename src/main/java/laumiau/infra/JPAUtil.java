package laumiau.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("laumiau");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void fechar() {
        factory.close();
    }
}