package com.MeuBlogPessoal.BlogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.MeuBlogPessoal.BlogPessoal.model.Usuario;
import com.MeuBlogPessoal.BlogPessoal.model.Utility.UsuarioDTO;
import com.MeuBlogPessoal.BlogPessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private @Autowired UsuarioRepository repositorio;
	
	/**
	 * Método para criar novo usuário no sistema
	 * @param novoUsuario
	 * @return Optional com usuario criado.
	 */
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {
		return repositorio.findByEmail(novoUsuario.getEmail()).map(usuarioExistente ->{
			return Optional.empty();
		}).orElseGet(()->{
			
			BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
			String result = enconder.encode(novoUsuario.getSenha());
			novoUsuario.setSenha(result);
			
			return Optional.ofNullable(repositorio.save(novoUsuario));
		});
	}
	
	public Optional<?> atualizarUsuario (UsuarioDTO usuarioParaAtualizar) {
		return repositorio.findById(usuarioParaAtualizar.getIdUsuario()).map(usuarioExistente ->{
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(usuarioParaAtualizar.getSenha());
			usuarioParaAtualizar.setSenha(senhaCriptografada);
			usuarioParaAtualizar.setNome(usuarioExistente.getNome());
			usuarioParaAtualizar.setFoto(usuarioExistente.getFoto());
			usuarioParaAtualizar.setTipo(usuarioExistente.getTipo());
			usuarioParaAtualizar.setEmail(usuarioExistente.getEmail());
			return Optional.ofNullable(repositorio.save(usuarioExistente));
		}).orElseGet(()->{
			return Optional.empty();
		});
	}
	
	public Optional<?> pegarCredenciais(UsuarioDTO usuarioParaAutenticar){
		return repositorio.findByEmail(usuarioParaAutenticar.getEmail()).map(usuarioExistente -> {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(usuarioParaAutenticar.getSenha(), usuarioExistente.getSenha())) {
				
				String estruturaBasic = usuarioParaAutenticar.getEmail() + ":" + usuarioParaAutenticar.getSenha(); // gustavoboaz@gmail.com:134652
				byte[] autorizacaoBase64 = Base64.encodeBase64(estruturaBasic.getBytes(Charset.forName("US-ASCII"))); // hHJyigo-o+i7%0ÍUG465sas=-
				String autorizacaoHeader = "Basic " + new String(autorizacaoBase64); // Basic hHJyigo-o+i7%0ÍUG465sas=-

				usuarioParaAutenticar.setToken(autorizacaoHeader);
				usuarioParaAutenticar.setIdUsuario(usuarioExistente.getIdUsuario());
				usuarioParaAutenticar.setNome(usuarioExistente.getNome());
				usuarioParaAutenticar.setSenha(usuarioExistente.getSenha());
				usuarioParaAutenticar.setFoto(usuarioExistente.getFoto());
				usuarioParaAutenticar.setTipo(usuarioExistente.getTipo());
				return Optional.ofNullable(usuarioParaAutenticar); // Usuario Credenciado
			} else {
				return Optional.empty();
			}
			
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
	
	public Optional<UsuarioDTO> Logar(Optional<UsuarioDTO> usuarioLogar) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repositorio.findByEmail(usuarioLogar.get().getEmail());
		
		if(usuario.isPresent()) {
			if(encoder.matches(usuarioLogar.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = usuarioLogar.get().getEmail() + ":" + usuarioLogar.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogar.get().setToken(authHeader);
				usuarioLogar.get().setIdUsuario(usuario.get().getIdUsuario());
				usuarioLogar.get().setNome(usuario.get().getNome());
				usuarioLogar.get().setFoto(usuario.get().getFoto());
				usuarioLogar.get().setTipo(usuario.get().getTipo());
				
				return usuarioLogar;
			}
		}
		return null;
	}
}
