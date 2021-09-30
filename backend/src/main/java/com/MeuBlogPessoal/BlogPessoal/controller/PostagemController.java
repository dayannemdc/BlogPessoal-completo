package com.MeuBlogPessoal.BlogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MeuBlogPessoal.BlogPessoal.model.Postagem;
import com.MeuBlogPessoal.BlogPessoal.repository.PostagemRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/postagem")
public class PostagemController {

	private @Autowired PostagemRepository repositorio;

	@GetMapping("/todas")
	public List<Postagem> pegarTodes() {
		return repositorio.findAll();
	}

	@PostMapping("/salvar")
	public ResponseEntity<Postagem> salvar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repositorio.save(novaPostagem));
	}

	@GetMapping("/{id_postagem}")
	public ResponseEntity<Postagem> buscarPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		Optional<Postagem> objetoPostagem = repositorio.findById(idPostagem);
		if (objetoPostagem.isPresent()) {
			return ResponseEntity.status(200).body(objetoPostagem.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> atualizar(@Valid @RequestBody Postagem postagemParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(postagemParaAtualizar));
	}

	@DeleteMapping("/deletar/{id_postagem}")
	public void deletarUsuarioPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		repositorio.deleteById(idPostagem);
	}

}
