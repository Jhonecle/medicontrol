package medicontrol.repository;

import medicontrol.entity.Medicamento; // <-- AQUI ESTÁ A CORREÇÃO

import org.springframework.data.jpa.repository.JpaRepository;

// Repository responsável pelas operações de banco de dados
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

}
