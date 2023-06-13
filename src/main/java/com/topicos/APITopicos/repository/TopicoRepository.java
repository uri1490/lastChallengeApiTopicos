package com.topicos.APITopicos.repository;

import com.topicos.APITopicos.model.TopicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<TopicoModel, Integer> {

}
