package ec.uagraria.costos;

public final class Estrategias {

    public static final double VALOR_JORNAL = 18.50;   // jornal referencial (USD)
    public static final double IVA = 0.15;             // IVA vigente en Ecuador

    private Estrategias() { }   // clase de utilidades: no se instancia

    // Estrategia 1: solo la suma de los insumos
    public static EstrategiaCosto costoDirecto() {
        return lote -> lote.costoInsumos();
    }

    // Estrategia 2: insumos + mano de obra (jornales x hectáreas x valor del jornal)
    public static EstrategiaCosto costoConManoDeObra() {
        return lote -> lote.costoInsumos()
                + lote.jornalesPorHectarea() * lote.hectareas() * VALOR_JORNAL;
    }

    // Estrategia 3: costo con mano de obra dividido para las hectáreas
    public static EstrategiaCosto costoPorHectarea() {
        return lote -> costoConManoDeObra().calcular(lote) / lote.hectareas();
    }

    // Estrategia 4: descuenta un porcentaje del costo de la urea (recibe un parámetro)
    public static EstrategiaCosto costoConSubsidioFertilizante(double porcentaje) {
        return lote -> {
            double descuento = lote.insumos().stream()
                    .filter(i -> i.nombre().toLowerCase().contains("urea"))
                    .mapToDouble(Insumo::costo)     // referencia a método
                    .sum() * porcentaje;
            return costoConManoDeObra().calcular(lote) - descuento;
        };
    }

    // Estrategia 5: envuelve otra estrategia y le suma el IVA (composición)
    public static EstrategiaCosto conIva(EstrategiaCosto base) {
        return lote -> base.calcular(lote) * (1 + IVA);
    }
}