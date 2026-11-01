package ec.uagraria.costos;

@FunctionalInterface
public interface EstrategiaCosto {

    double calcular(Lote lote);

    default String descripcion() {
        return "Estrategia de costo";
    }
}
