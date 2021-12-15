package br.edu.utfpr.editorartigos.repository;

import br.edu.utfpr.editorartigos.model.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
    Set<Artigo> findArtigosByAutorId(Long id);

    Set<Artigo> findArtigosByCategoria_IdIn(Set<Long> ids);

    Set<Artigo> findTop10ByOrderByVisualizacoesDesc();

    List<Artigo> findArtigoByTituloContainsOrPalavrasChaveContainsOrderByTitulo(String titulo, String palavraChave);
}
