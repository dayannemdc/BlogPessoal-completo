package com.MeuBlogPessoal.BlogPessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MeuBlogPessoal.BlogPessoal.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long>{

}
