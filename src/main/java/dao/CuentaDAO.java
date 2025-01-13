package dao;

import model.Cuenta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import util.JPAUtil;

public class CuentaDAO {
    public Cuenta getCuentaById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            // Use TypedQuery to ensure proper retrieval
            TypedQuery<Cuenta> query = em.createQuery(
                "SELECT c FROM Cuenta c WHERE c.id = :id", 
                Cuenta.class
            );
            query.setParameter("id", id);
            
            Cuenta cuenta = query.getSingleResult();
            
            // Force load of relationships if any
            em.refresh(cuenta);
            
            tx.commit();
            return cuenta;
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error getting account: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error retrieving account with ID: " + id, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}