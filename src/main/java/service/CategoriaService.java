package service;

import dao.CategoriaDAO;
import model.Categoria;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;
    
    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }
    
    public List<Categoria> getAllCategorias() throws SQLException {
        return categoriaDAO.getAllCategorias();
    }
    
    public boolean existeCategoria(String categoriaId) throws SQLException {
        if (categoriaId == null || categoriaId.isEmpty()) {
            return false;
        }
        
        try {
            int id = Integer.parseInt(categoriaId);
            return getAllCategorias().stream()
                .anyMatch(cat -> cat.getId() == id);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public Optional<Categoria> getCategoriaById(int id) throws SQLException {
        return getAllCategorias().stream()
            .filter(cat -> cat.getId() == id)
            .findFirst();
    }
}