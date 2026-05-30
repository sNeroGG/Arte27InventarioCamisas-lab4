package com.arte27.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.arte27.models.Categoria;

@Service
public class CategoriaService {
    private final List<Categoria> categorias = new ArrayList<>();
    private int currentId = 1;

    public CategoriaService() {
        // Preload default categories specified by the user
        guardarCategoria(new Categoria(null, "Formal", "Camisas de vestir elegantes para eventos formales u oficina", true));
        guardarCategoria(new Categoria(null, "Casual", "Camisas cómodas y versátiles para el día a día", true));
        guardarCategoria(new Categoria(null, "Deportiva", "Camisas de corte deportivo, ligeras y transpirables", true));
        guardarCategoria(new Categoria(null, "Oversize", "Camisas de corte holgado y estilo urbano moderno", true));
    }

    public synchronized List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    public synchronized Categoria buscarCategoria(Integer id) {
        if (id == null) return null;
        return categorias.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

    public synchronized void guardarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(currentId++);
        }
        
        int index = -1;
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            categorias.set(index, categoria);
        } else {
            categorias.add(categoria);
        }
    }

    public synchronized void eliminarCategoria(Integer id) {
        if (id == null) return;
        categorias.removeIf(c -> id.equals(c.getId()));
    }
}
