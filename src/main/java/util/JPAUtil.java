package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf;
    
    static {
        try {
            // Inicializar EntityManagerFactory con las propiedades de persistence.xml
            emf = Persistence.createEntityManagerFactory("WebDinamicaMVC");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing JPA", e);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized");
        }
        return emf.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}