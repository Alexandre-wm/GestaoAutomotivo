package EstudoDeCaso_Alexandre.SistemaAutomotivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import EstudoDeCaso_Alexandre.SistemaAutomotivo.model.Veiculo;
import EstudoDeCaso_Alexandre.SistemaAutomotivo.repository.VeiculoRepository;

@Controller // Note que aqui é apenas @Controller, para devolver HTML
public class VeiculoViewController {

    @Autowired
    private VeiculoRepository repository;

// 1. Abre a página inicial com a lista de carros (AGORA COM FILTRO DE BUSCA!)
    @GetMapping("/")
    public String listarVeiculos(Model model, @RequestParam(required = false) String palavraChave) {
        java.util.List<Veiculo> veiculosDaTela;
        
        // Se o usuário digitou algo na busca, nós filtramos a lista
        if (palavraChave != null && !palavraChave.isEmpty()) {
            veiculosDaTela = repository.findAll().stream()
                    .filter(v -> v.getMarca().toLowerCase().contains(palavraChave.toLowerCase()) || 
                                 v.getModelo().toLowerCase().contains(palavraChave.toLowerCase()))
                    .toList();
        } else {
            // Se não digitou nada, mostra todos os carros
            veiculosDaTela = repository.findAll();
        }

        model.addAttribute("veiculos", veiculosDaTela);
        model.addAttribute("novoVeiculo", new Veiculo());
        return "index";
    }

    // 2. Rota para cadastrar via formulário da página
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Veiculo veiculo) {
        repository.save(veiculo);
        return "redirect:/"; // Recarrega a página para mostrar o novo carro
    }

    // 3. Rota para excluir clicando no botão da página
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // 4. Rota para buscar o carro pelo ID e abrir a tela de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Veiculo veiculo = repository.findById(id).orElse(null);
        if (veiculo != null) {
            model.addAttribute("veiculoExistente", veiculo);
            return "editar"; 
        }
        return "redirect:/"; 
    }
}