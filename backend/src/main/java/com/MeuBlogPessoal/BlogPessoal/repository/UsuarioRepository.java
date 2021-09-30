package com.MeuBlogPessoal.BlogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MeuBlogPessoal.BlogPessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/**
	 * Método utilizado para pesquisar coluna nome
	 * @param nome do tipo String
	 * @return List de Usuarios
	 * @author Dayanne
	 */
	List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
	
	/**
	 * Método utilizado para pesquisar coluna email
	 * @param email do tipo String
	 * @return Optional com Usuario
	 * @since 1.0
	 */
	Optional<Usuario> findByEmail(String email);
}
