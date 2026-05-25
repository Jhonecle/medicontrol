package medicontrol.controller;

import medicontrol.entity.Medicamento;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MedicamentoController {

    @GetMapping("/medicamentos")
    public List<Medicamento> listarMedicamentos() {

        List<Medicamento> lista = new ArrayList<>();

        Medicamento medicamento = new Medicamento();

        medicamento.setId(1L);
        medicamento.setNome("Dipirona");
        medicamento.setDosagem("500mg");
        medicamento.setHorario("08:00");
        medicamento.setObservacao("Tomar após café");

        lista.add(medicamento);

        return lista;
    }
}