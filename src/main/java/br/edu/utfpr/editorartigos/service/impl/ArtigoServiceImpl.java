package br.edu.utfpr.editorartigos.service.impl;

import br.edu.utfpr.editorartigos.model.Artigo;
import br.edu.utfpr.editorartigos.model.Categoria;
import br.edu.utfpr.editorartigos.repository.ArtigoRepository;
import br.edu.utfpr.editorartigos.service.ArtigoService;
import br.edu.utfpr.editorartigos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtigoServiceImpl extends CrudServiceImpl<Artigo, Long> implements ArtigoService {

    private final ArtigoRepository artigoRepository;
    private final UsuarioService usuarioService;

    @Override
    public JpaRepository<Artigo, Long> getRepository() {
        return artigoRepository;
    }

    @Override
    public void valid(Artigo entity) throws Exception {

    }

    @Override
    public Artigo cadastrarArtigo(Artigo artigo) throws Exception {
        if (artigo.getId() == null) {
            artigo.setAutor(usuarioService.getUsuarioLogado());
        }
        return save(artigo);
    }

    @Override
    public void deletarArtigo(Long id) {
        delete(id);
    }

    @Override
    public List<Artigo> listarTodos() {
        return findAll();
    }

    @Override
    @Transactional
    public Set<Artigo> artigosPorUsuario() {
        return artigoRepository.findArtigosByAutorId(usuarioService.getUsuarioLogado().getId());

    }

    @Override
    @Transactional
    public Set<Artigo> recomendacaoPorUsuario() {
        var interesses = getInteresses(usuarioService.getUsuarioLogado().getId());
        return artigoRepository.findArtigosByCategoria_IdIn(interesses);
    }

    private Set<Long> getInteresses(Long usuarioId) {
        return usuarioService
                .findById(usuarioId)
                .getInteresses()
                .stream()
                .map(Categoria::getId)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<Artigo> artigosMaisVistos() {
        return artigoRepository.findTop10ByOrderByVisualizacoesDesc();
    }

    @Override
    public Artigo findById(Long aLong) {

        var artigo = super.findById(aLong);
        if (artigo != null) {
            artigo.incrementarVisualizacoes();
            artigoRepository.saveAndFlush(artigo);
        }
        return artigo;
    }
}
