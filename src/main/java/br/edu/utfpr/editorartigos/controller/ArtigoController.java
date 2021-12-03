package br.edu.utfpr.editorartigos.controller;

import br.edu.utfpr.editorartigos.model.Artigo;
import br.edu.utfpr.editorartigos.service.ArtigoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("artigo/")
@RequiredArgsConstructor
public class ArtigoController {
    private final ArtigoService artigoService;

    @PostMapping("incluir")
    public Artigo incluir(@RequestBody Artigo artigo) throws Exception {
        return artigoService.cadastrarArtigo(artigo);
    }

    @PutMapping("atualizar")
    public Artigo alterar(@RequestBody Artigo artigo) throws Exception {
        return artigoService.cadastrarArtigo(artigo);
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable("id") Long id) {
        artigoService.deletarArtigo(id);
    }


    @GetMapping("pesquisar-todos")
    public List<Artigo> pesquisarTodos() {
        return artigoService.listarTodos();
    }

    @GetMapping("{id}")
    public Artigo findOne(@PathVariable("id") Long id) {
        return artigoService.findById(id);
    }

    @GetMapping("usuario")
    public Set<Artigo> artigosPorUsuario() {
        return artigoService.artigosPorUsuario();
    }

    @GetMapping("destaque")
    public Set<Artigo> artigosPorDestaque() {
        return artigoService.artigosMaisVistos();
    }

    @GetMapping("recomendacao")
    public Set<Artigo> artigosRecomendacao() {
        return artigoService.recomendacaoPorUsuario();
    }

    public Set<Artigo> artigosFiltro(){
        return null;
    }
}
