package com.topicos.APITopicos.service;

import com.topicos.APITopicos.model.TopicoModel;
import com.topicos.APITopicos.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicoService implements TopicoServicePage{
    private final TopicoRepository topicoRepository;
    public void createTopico(TopicoModel topico){
        topicoRepository.save(topico);
    }
    public List<TopicoModel> buscarTopico(){
       return topicoRepository.findAll();
    }
    public Optional<TopicoModel> buscarTopico(Integer id){
        return topicoRepository.findById(id);
    }
    public void borrarTopico(Integer id){
        topicoRepository.deleteById(id);
    }


    @Override
    public Page<TopicoModel> getAll(Pageable pageable) {
        return topicoRepository.findAll(pageable);
    }
}
