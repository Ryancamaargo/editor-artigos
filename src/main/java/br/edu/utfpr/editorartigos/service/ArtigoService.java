package br.edu.utfpr.editorartigos.service;

import br.edu.utfpr.editorartigos.model.Artigo;

import java.util.List;
import java.util.Set;

public interface ArtigoService extends CrudService<Artigo, Long> {

    Artigo cadastrarArtigo(Artigo artigo) throws Exception;

    void deletarArtigo(Long id);

    List<Artigo> listarTodos();

    Set<Artigo> artigosPorUsuario();

    Set<Artigo> recomendacaoPorUsuario();

    Set<Artigo> artigosMaisVistos();
}
