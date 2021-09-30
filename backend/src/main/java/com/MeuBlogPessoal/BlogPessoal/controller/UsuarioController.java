package com.MeuBlogPessoal.BlogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MeuBlogPessoal.BlogPessoal.model.Usuario;
import com.MeuBlogPessoal.BlogPessoal.model.Utility.UsuarioDTO;
import com.MeuBlogPessoal.BlogPessoal.repository.UsuarioRepository;
import com.MeuBlogPessoal.BlogPessoal.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin("*")
public class UsuarioController {

	private @Autowired UsuarioRepository repositorio;
	private @Autowired UsuarioService servicos;

	@GetMapping("/todes")
	public ResponseEntity<List<Usuario>> pegarTodes() {
		List<Usuario> objetoLista = repositorio.findAll();
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	@PostMapping("/salvar")
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario novoUsuario) {
		Optional<Object> objetoOptional = servicos.cadastrarUsuario(novoUsuario);
		if(objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		}else {
			return ResponseEntity.status(201).body(objetoOptional.get());
		}
	}
	
	@PutMapping("/credenciais")
	public ResponseEntity<Object> credenciais(@Valid @RequestBody UsuarioDTO usuarioParaAutenticar){
		Optional<?> objetoOptional = servicos.pegarCredenciais(usuarioParaAutenticar);
		if (objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		}else {
			return ResponseEntity.status(201).body(objetoOptional.get());
		}
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioDTO> Autentication(@RequestBody Optional<UsuarioDTO> user) {
		return servicos.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping("/{id_usuario}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable(value = "id_usuario") Long idUsuario) {
		Optional<Usuario> objetoUsuario = repositorio.findById(idUsuario);
		if (objetoUsuario.isPresent()) {
			return ResponseEntity.status(200).body(objetoUsuario.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	@GetMapping("/nome/{nome_usuario}")
	public ResponseEntity<List<Usuario>> buscarPorNomeI(@PathVariable(value = "nome_usuario") String nome) {
		List<Usuario> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@GetMapping("/pesquisa/email")
	public ResponseEntity<Usuario> buscarPorEmail (@RequestParam(defaultValue = "") String email){
		return repositorio.findByEmail(email)
				.map(batatinha -> ResponseEntity.ok(batatinha))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/pesquisa/nome")
	public ResponseEntity<List<Usuario>> buscarPorNomeII(@RequestParam(defaultValue = "") String nome) {
		List<Usuario> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	/*@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(usuarioParaAtualizar));
	}*/
	
	@PutMapping("/atualizar")
	public ResponseEntity<Object> atualizarUsuario(@Valid @RequestBody Usuario usuarioParaAtualizar){
		Optional<Object> objetoOptional = servicos.atualizarUsuario(usuarioParaAtualizar);
		
		if (objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		}else {
			return ResponseEntity.status(201).body(repositorio.save(usuarioParaAtualizar));
		}
	}

	/*@DeleteMapping("/deletar/{id_usuario}")
	public void deletarUsuarioPorId(@PathVariable(value = "id_usuario") Long idUsuario) {
		repositorio.deleteById(idUsuario);
	}*/
	
	@DeleteMapping("/deletar/{idUsuario}")
	public ResponseEntity<Object> deletarUsuario (@PathVariable(value = "idUsuario") Long idUsuario){
		Optional<Usuario> objetoOptional = repositorio.findById(idUsuario);
		if (objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		}else {
			repositorio.deleteById(idUsuario);
			return ResponseEntity.status(200).build();
		}
	}
}
