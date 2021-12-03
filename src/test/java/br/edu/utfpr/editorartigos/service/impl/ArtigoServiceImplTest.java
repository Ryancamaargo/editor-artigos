package br.edu.utfpr.editorartigos.service.impl;

import br.edu.utfpr.editorartigos.exception.CategoriaJaExisteException;
import br.edu.utfpr.editorartigos.model.Artigo;
import br.edu.utfpr.editorartigos.model.Categoria;
import br.edu.utfpr.editorartigos.model.Usuario;
import br.edu.utfpr.editorartigos.repository.ArtigoRepository;
import br.edu.utfpr.editorartigos.repository.CategoriaRepository;
import br.edu.utfpr.editorartigos.repository.UsuarioRepository;
import br.edu.utfpr.editorartigos.service.ArtigoService;
import br.edu.utfpr.editorartigos.service.CrudService;
import br.edu.utfpr.editorartigos.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArtigoServiceImplTest {

    @Mock
    private CrudService crudService;

    private ArtigoService artigoService;

    @Mock
    private ArtigoRepository artigoRepository;
    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        artigoService = new ArtigoServiceImpl(artigoRepository, usuarioService);
    }

    @Test()
    void validAutorSaveArtigo() throws Exception {
        var artigo = Artigo.builder().build();
        when(usuarioService.getUsuarioLogado()).thenReturn(Usuario.builder().id(1L).build());
        when(artigoService.save(artigo)).thenReturn(artigo);
        artigoService.cadastrarArtigo(artigo);
        verify(artigoRepository, times(1)).save(any());
    }

    @Test()
    void validAutorSaveArtigoIdNull() throws Exception {
        var artigo = Artigo.builder().build();
        when(usuarioService.getUsuarioLogado()).thenReturn(Usuario.builder().id(1L).build());
        when(artigoService.save(artigo)).thenReturn(artigo);
        artigo = artigoService.cadastrarArtigo(artigo);
        assertEquals(1L, artigo.getAutor().getId());
    }

    @Test()
    void validAutorSaveArtigoIdNotNull() throws Exception {
        var artigo = Artigo.builder().id(1L).build();
        when(artigoService.save(artigo)).thenReturn(artigo);
        artigo = artigoService.cadastrarArtigo(artigo);
        assertNull(artigo.getAutor());
    }


    @Test()
    void deletarArtigoTest() {
        artigoService.deletarArtigo(1L);
        verify(artigoRepository, times(1)).deleteById(1L);
    }

    @Test()
    void listarTodosTest() {
        artigoService.listarTodos();
        verify(artigoRepository, times(1)).findAll();
    }

    @Test()
    void artigosPorUsuarioTest() {
        when(usuarioService.getUsuarioLogado()).thenReturn(Usuario.builder().id(1L).build());
        artigoService.artigosPorUsuario();
        verify(artigoRepository, times(1)).findArtigosByAutorId(any());
    }

    @Test()
    void recomendacaoPorUsuarioTest() {
        when(usuarioService.getUsuarioLogado()).thenReturn(Usuario.builder().id(1L).build());
        when(usuarioService.findById(any())).thenReturn(Usuario.builder()
                .id(1L)
                .interesses(Set.of(Categoria.builder()
                        .id(1L)
                        .build())).build());
        artigoService.recomendacaoPorUsuario();
        verify(artigoRepository, times(1)).findArtigosByCategoria_IdIn(any());
    }

    @Test()
    void artigosMaisVistosTest() {
        artigoService.artigosMaisVistos();
        verify(artigoRepository, times(1)).findTop10ByOrderByVisualizacoesDesc();
    }

    @Test()
    void findByIdArtigoNullTest() {
        when(crudService.findById(any())).thenReturn(null);
        var artigo = artigoService.findById(1L);
        assertNull(artigo);
    }

    @Test()
    void findByIdArtigoNotNullTest() {
        when(artigoRepository.findById(any())).thenReturn(Optional.of(Artigo.builder().build()));
        var artigo = artigoService.findById(1L);
        assertNotNull(artigo);
    }

}
