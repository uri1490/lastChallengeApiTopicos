package com.topicos.APITopicos.service;

import com.topicos.APITopicos.model.TopicoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicoServicePage {
    Page<TopicoModel>getAll(Pageable pageable);
}
