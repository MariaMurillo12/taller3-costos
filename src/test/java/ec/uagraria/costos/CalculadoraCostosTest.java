package ec.uagraria.costos;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraCostosTest {

    // Lote de banano de la tabla de verificación
    private Lote loteBanano() {
        return new Lote("L-01", new Banano("Cavendish"), 4.0, List.of(
                new Insumo("Urea 50 kg", 12, "saco", 32.50),
                new Insumo("Fungicida", 8, "litro", 21.75),
                new Insumo("Semilla certificada", 1, "lote", 180.00)));
    }

    // Lote de cacao orgánico de la tabla de verificación
    private Lote loteCacaoOrganico() {
        return new Lote("L-02", new Cacao("Nacional", true), 2.5, List.of(
                new Insumo("Urea 50 kg", 5, "saco", 32.50),
                new Insumo("Abono organico", 30, "saco", 9.80)));
    }

    @Test
    void sinEstrategiaDevuelveOptionalVacio() {
        assertTrue(new CalculadoraCostos().calcular(loteBanano()).isEmpty());
    }

    @Test
    void costoDirectoSumaLosInsumos() {
        var calc = new CalculadoraCostos(Estrategias.costoDirecto());
        assertEquals(744.00, calc.calcular(loteBanano()).orElseThrow(), 0.01);
    }

    @Test
    void costoConManoDeObraSumaLosJornales() {
        var calc = new CalculadoraCostos(Estrategias.costoConManoDeObra());
        assertEquals(2076.00, calc.calcular(loteBanano()).orElseThrow(), 0.01);
    }

    @Test
    void costoPorHectareaDivideParaLasHectareas() {
        var calc = new CalculadoraCostos(Estrategias.costoPorHectarea());
        assertEquals(519.00, calc.calcular(loteBanano()).orElseThrow(), 0.01);
    }

    @Test
    void subsidioDescuentaElVeintePorCientoDeLaUrea() {
        var calc = new CalculadoraCostos(Estrategias.costoConSubsidioFertilizante(0.20));
        assertEquals(1998.00, calc.calcular(loteBanano()).orElseThrow(), 0.01);
    }

    @Test
    void ivaSeComponeConOtraEstrategia() {
        var calc = new CalculadoraCostos(
                Estrategias.conIva(Estrategias.costoConManoDeObra()));
     }
    @Test
        void cacaoOrganicoUsaLaRamaConWhen() {
        assertEquals(25, loteCacaoOrganico().jornalesPorHectarea());
        var calc = new CalculadoraCostos(Estrategias.costoConManoDeObra());
        assertEquals(1612.75, calc.calcular(loteCacaoOrganico()).orElseThrow(), 0.01);
    }
}