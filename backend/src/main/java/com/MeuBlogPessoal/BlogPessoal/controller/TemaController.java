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

import com.MeuBlogPessoal.BlogPessoal.model.Tema;
import com.MeuBlogPessoal.BlogPessoal.repository.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/tema")
public class TemaController {

	private @Autowired TemaRepository repositorio;

	@GetMapping("/todos")
	public List<Tema> pegarTodes() {
		return repositorio.findAll();
	}

	@PostMapping("/salvar")
	public ResponseEntity<Tema> salvar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));
	}

	@GetMapping("/{id_tema}")
	public ResponseEntity<Tema> buscarPorId(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoTema = repositorio.findById(idTema);
		if (objetoTema.isPresent()) {
			return ResponseEntity.status(200).body(objetoTema.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Tema> atualizar(@Valid @RequestBody Tema temaParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(temaParaAtualizar));
	}

	@DeleteMapping("/deletar/{id_tema}")
	public void deletarUsuarioPorId(@PathVariable(value = "id_tema") Long idTema) {
		repositorio.deleteById(idTema);
	}

}
