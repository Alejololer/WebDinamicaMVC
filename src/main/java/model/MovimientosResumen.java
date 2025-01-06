package model;

public class MovimientosResumen {
    private final double totalIngresos;
    private final double totalEgresos;
    
    public MovimientosResumen(double totalIngresos, double totalEgresos) {
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
    }
    
    public double getTotalIngresos() {
        return totalIngresos;
    }
    
    public double getTotalEgresos() {
        return totalEgresos;
    }
}
