package ec.uagraria.costos;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        // Lote de banano de la tabla de verificación
        Lote lote = new Lote("L-01", new Banano("Cavendish"), 4.0, List.of(
                new Insumo("Urea 50 kg", 12, "saco", 32.50),
                new Insumo("Fungicida", 8, "litro", 21.75),
                new Insumo("Semilla certificada", 1, "lote", 180.00)));

        CalculadoraCostos calc = new CalculadoraCostos();

        // 1. Sin estrategia configurada
        System.out.println("Sin estrategia: " + calc.calcular(lote));

        // 2. Costo directo
        calc.setEstrategia(Estrategias.costoDirecto());
        mostrar("Costo directo", calc.calcular(lote).orElseThrow());

        // 3. Insumos + mano de obra
        calc.setEstrategia(Estrategias.costoConManoDeObra());
        mostrar("Insumos + mano de obra", calc.calcular(lote).orElseThrow());

        // 4. Costo por hectárea
        calc.setEstrategia(Estrategias.costoPorHectarea());
        mostrar("Costo por hectarea", calc.calcular(lote).orElseThrow());

        // 5. Subsidio del 20 % a la urea
        calc.setEstrategia(Estrategias.costoConSubsidioFertilizante(0.20));
        mostrar("Con subsidio 20% a la urea", calc.calcular(lote).orElseThrow());

        // 6. IVA del 15 % (composición de estrategias)
        calc.setEstrategia(Estrategias.conIva(Estrategias.costoConManoDeObra()));
        mostrar("Con IVA 15%", calc.calcular(lote).orElseThrow());

        // 7. Lote de cacao orgánico (rama del switch con when)
        Lote cacao = new Lote("L-02", new Cacao("Nacional", true), 2.5, List.of(
                new Insumo("Urea 50 kg", 5, "saco", 32.50),
                new Insumo("Abono organico", 30, "saco", 9.80)));

        System.out.println("Cacao organico, jornales por hectarea: "
                + cacao.jornalesPorHectarea());
        calc.setEstrategia(Estrategias.costoConManoDeObra());
        mostrar("Cacao organico: insumos + mano de obra",
                calc.calcular(cacao).orElseThrow());
    }

    // Imprime el valor redondeado a dos decimales
    private static void mostrar(String etiqueta, double valor) {
        System.out.println(etiqueta + ": USD "
                + String.format(Locale.US, "%.2f", CalculadoraCostos.redondear(valor)));
    }
}