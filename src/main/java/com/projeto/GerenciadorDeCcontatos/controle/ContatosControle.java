package com.projeto.GerenciadorDeCcontatos.controle;

import com.projeto.GerenciadorDeCcontatos.modelo.Contato;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {

    private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

    static {
        Contato contato = new Contato("1", "joao", "12343 0000");
        Contato contato1 = new Contato("2", "maria", "00000 0000");
        Contato contato2 = new Contato("3", "carla", "65796 0000");
        Contato contato3 = new Contato("4", "antonio", "65789 0000");

        LISTA_CONTATOS.add(contato);
        LISTA_CONTATOS.add(contato1);
        LISTA_CONTATOS.add(contato2);
        LISTA_CONTATOS.add(contato3);

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    
    
    @GetMapping("/contatos")
    public ModelAndView listar() {

        ModelAndView modelAndView = new ModelAndView("listar");

        modelAndView.addObject("contatos", LISTA_CONTATOS);

        return modelAndView;
    }

    
    
    @GetMapping("/contatos/novo")
    public ModelAndView novo() {

        ModelAndView modelAndView = new ModelAndView("formulario");

        modelAndView.addObject("contato", new Contato());

        return modelAndView;

    }

    
    @PostMapping("/contatos")
    public String cadastrar(Contato contato) {

        String id = UUID.randomUUID().toString();

        contato.setId(id);

        LISTA_CONTATOS.add(contato);

        return "redirect:/contatos";

    }

    
    
    @GetMapping("/contatos/{id}/editar")
    public ModelAndView editar(@PathVariable String id) {

        Contato contato = procurarContato(id);

        ModelAndView modelAndView = new ModelAndView("formulario");

        modelAndView.addObject("contato", contato);

        return modelAndView;

    }

    
    
    @PutMapping("/contatos/{id}")
    public String atualizar(Contato contato) {

        Integer indice = procurarIndiceContato(contato.getId());

        Contato contatoVelho = LISTA_CONTATOS.get(indice);

        LISTA_CONTATOS.remove(contatoVelho);

        LISTA_CONTATOS.add(indice, contato);

        return "redirect:/contatos";

    }

    
    
    @DeleteMapping("/contatos/{id}")
    public String remover(@PathVariable String id) {

        Contato contato = procurarContato(id);

        LISTA_CONTATOS.remove(contato);
        
        return "redirect:/contatos";
    }

    
    public Contato procurarContato(String id) {

        for (int i = 0; i < LISTA_CONTATOS.size(); i++) {

            Contato contato = LISTA_CONTATOS.get(i);

            if (contato.getId().equals(id)) {
                return contato;
            }

        }
        return null;
    }

    public Integer procurarIndiceContato(String id) {

        for (int i = 0; i < LISTA_CONTATOS.size(); i++) {

            Contato contato = LISTA_CONTATOS.get(i);

            if (contato.getId().equals(id)) {
                return i;
            }

        }
        return null;
    }

}
