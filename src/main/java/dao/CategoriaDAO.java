package dao;

import model.Categoria;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoriaDAO {
    public List<Categoria> getAllCategorias() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre", Categoria.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}