package service;

import dao.CategoriaDAO;
import model.Categoria;
import java.sql.SQLException;
import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;
    
    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }
    
    public List<Categoria> getAllCategorias() throws SQLException {
        return categoriaDAO.getAllCategorias();
    }
}
