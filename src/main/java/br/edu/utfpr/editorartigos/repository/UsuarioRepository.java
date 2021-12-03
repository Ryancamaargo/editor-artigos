package br.edu.utfpr.editorartigos.repository;


import br.edu.utfpr.editorartigos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String email);
}
