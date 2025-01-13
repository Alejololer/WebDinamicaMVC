package dao;

import model.*;
import util.JPAUtil;
import jakarta.persistence.*;
import java.util.*;

public class MovimientoDAO {
    public List<Movimiento> getAllMovimientos(int cuentaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT m FROM Movimiento m " +
                         "WHERE m.cuentaId = :cuentaId " +
                         "ORDER BY m.fecha ASC";
            
            List<Movimiento> movimientos = em.createQuery(jpql, Movimiento.class)
                .setParameter("cuentaId", cuentaId)
                .getResultList();
            
            // Calculate saldoDespues for each movement
            double saldo = 0;
            for (Movimiento m : movimientos) {
                if (m.getTipo() == TipoMovimiento.INGRESO || 
                    m.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                    saldo += m.getValor();
                } else {
                    saldo -= m.getValor();
                }
                m.setSaldoDespues(saldo);
            }
            
            return movimientos;
        } finally {
            em.close();
        }
    }
}