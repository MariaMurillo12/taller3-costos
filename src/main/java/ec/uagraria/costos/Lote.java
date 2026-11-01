package ec.uagraria.costos;

import java.util.List;

public record Lote(String codigo, Cultivo cultivo, double hectareas,
                   List<Insumo> insumos) {

    public Lote {
        insumos = List.copyOf(insumos);   // copia defensiva: queda inmutable
    }

    public double costoInsumos() {
        double total = 0.0;
        for (Insumo i : insumos) {
            total += i.costo();
        }
        return total;
    }

    public int jornalesPorHectarea() {
        return switch (cultivo) {
            case Banano b -> 18;
            case Cacao c when c.organico() -> 25;  // el más específico primero
            case Cacao c -> 20;
            case Arroz a -> 12 * a.ciclosPorAnio();
        };   // sin default: la jerarquía sellada ya está completa
    }
}