package EstudoDeCaso_Alexandre.SistemaAutomotivo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import EstudoDeCaso_Alexandre.SistemaAutomotivo.model.Veiculo;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    // O JpaRepository já traz prontos métodos como save(), findAll(), findById() e deleteById().
    
    // Aqui nós vamos adicionar os filtros específicos que o desafio pediu:
    List<Veiculo> findByTipo(String tipo);
    
    List<Veiculo> findByAno(Integer ano);
    
    // Esse método vai buscar veículos que custem até um valor máximo determinado
    List<Veiculo> findByPrecoLessThanEqual(Double preco);
}