package service;

import dao.MovimientoDAO;
import model.Movimiento;
import model.TipoMovimiento;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovimientoService {
	private MovimientoDAO movimientoDAO;

	public MovimientoService() {
		this.movimientoDAO = new MovimientoDAO();
	}

	public List<Movimiento> getMovimientosFiltrados(List<Movimiento> movimientos, Date fechaInicio, Date fechaFin,
			String categoria, String tipo) {
		return movimientos.stream().filter(mov -> cumpleFiltros(mov, fechaInicio, fechaFin, categoria, tipo))
				.collect(Collectors.toList());
	}

	private boolean cumpleFiltros(Movimiento mov, Date fechaInicio, Date fechaFin, String categoria, String tipo) {
		if (fechaInicio != null && mov.getFecha().before(fechaInicio)) {
			return false;
		}

		if (fechaFin != null && mov.getFecha().after(fechaFin)) {
			return false;
		}

		if (categoria != null && !categoria.isEmpty() && mov.getCategoria().getId() != Integer.parseInt(categoria)) {
			return false;
		}

		if (tipo != null && !tipo.isEmpty() && !validarTipoMovimiento(mov, tipo)) {
			return false;
		}

		return true;
	}

	private boolean validarTipoMovimiento(Movimiento mov, String tipo) {
		return switch (tipo) {
		case "TRANSFERENCIA" -> mov.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE
				|| mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE;
		default -> mov.getTipo() == TipoMovimiento.valueOf(tipo);
		};
	}

	public List<Movimiento> getAllMovimientos(int cuentaId) throws SQLException {
		List<Movimiento> movimientos = movimientoDAO.getAllMovimientos(cuentaId);
		return movimientos;
	}
}