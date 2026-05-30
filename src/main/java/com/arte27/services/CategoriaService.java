package com.arte27.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import com.arte27.models.Categoria;
import com.arte27.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostConstruct
    public void init() {
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria(null, "Formal", "Camisas de vestir elegantes para eventos formales u oficina", true));
            categoriaRepository.save(new Categoria(null, "Casual", "Camisas cómodas y versátiles para el día a día", true));
            categoriaRepository.save(new Categoria(null, "Deportiva", "Camisas de corte deportivo, ligeras y transpirables", true));
            categoriaRepository.save(new Categoria(null, "Oversize", "Camisas de corte holgado y estilo urbano moderno", true));
        }
    }

    public List<Categoria> buscarTodo() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        if (id == null) return null;
        return categoriaRepository.findById(id).orElse(null);
    }

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {
        if (id == null) return;
        categoriaRepository.deleteById(id);
    }
}
