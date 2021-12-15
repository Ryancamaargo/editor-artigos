package br.edu.utfpr.editorartigos.service.impl;

import br.edu.utfpr.editorartigos.exception.CategoriaJaExisteException;
import br.edu.utfpr.editorartigos.model.Categoria;
import br.edu.utfpr.editorartigos.repository.CategoriaRepository;
import br.edu.utfpr.editorartigos.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl extends CrudServiceImpl<Categoria, Long> implements CategoriaService{

    private final CategoriaRepository categoriaRepository;

    @Override
    public JpaRepository<Categoria, Long> getRepository() {
        return categoriaRepository;
    }

    @Override
    public void valid(Categoria entity) throws CategoriaJaExisteException {
        if(entity.getId() == null)
            if (categoriaRepository.findByDescricao(entity.getDescricao()).isPresent())
                throw new CategoriaJaExisteException("Categoria " + entity.getDescricao() + " já existe");
    }

    @Override
    public Categoria cadastrarCategoria(Categoria categoria) throws Exception {
        return save(categoria);
    }

    @Override
    public void deletarCategoria(Long id) {
        delete(id);
    }

    @Override
    public List<Categoria> listarTodos() {
        return findAll();
    }
}
