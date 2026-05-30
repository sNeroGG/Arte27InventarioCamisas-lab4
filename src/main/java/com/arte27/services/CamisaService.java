package com.arte27.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import com.arte27.models.Camisa;
import com.arte27.repositories.CamisaRepository;

@Service
public class CamisaService {

    @Autowired
    private CamisaRepository camisaRepository;

    @PostConstruct
    public void init() {
        if (camisaRepository.count() == 0) {
            guardar(new Camisa(null, "Camisa Lino Premium", 45.99, "Blanco", "M", 15, true, null, "Formal"));
            guardar(new Camisa(null, "Camisa Oxford Casual", 39.50, "Azul", "L", 22, true, null, "Casual"));
            guardar(new Camisa(null, "Camisa Mao Algodón", 35.00, "Verde", "S", 8, true, null, "Casual"));
            guardar(new Camisa(null, "Camisa Formal Satín", 55.00, "Negro", "XL", 12, true, null, "Formal"));
            guardar(new Camisa(null, "Camisa Casual Leñador", 32.99, "Rojo", "M", 0, false, null, "Oversize"));
            guardar(new Camisa(null, "Camisa Slim Fit Lino", 48.00, "Blanco", "L", 10, true, null, "Formal"));
        }
    }

    public List<Camisa> buscarTodo() {
        return camisaRepository.findAll();
    }

    public List<Camisa> buscarPorFiltros(String query, String color) {
        return camisaRepository.findAll().stream()
                .filter(c -> {
                    if (query == null || query.trim().isEmpty()) {
                        return true;
                    }
                    return c.getNombre().toLowerCase().contains(query.toLowerCase().trim());
                })
                .filter(c -> {
                    if (color == null || color.trim().isEmpty() || color.equals("Todos")) {
                        return true;
                    }
                    return c.getColor().equalsIgnoreCase(color.trim());
                })
                .collect(Collectors.toList());
    }

    public void guardar(Camisa camisa) {
        if (camisa.getImagenUrl() == null || camisa.getImagenUrl().trim().isEmpty()) {
            camisa.setImagenUrl("/images/camisa_blanca.png");
        }
        
        if (camisa.getStock() == null) {
            camisa.setStock(0);
        }
        camisa.setDisponible(camisa.getStock() > 0);

        if (camisa.getFechaRegistro() == null) {
            camisa.setFechaRegistro(new java.util.Date());
        }

        camisaRepository.save(camisa);
    }

    public Camisa buscarPorId(Integer id) {
        if (id == null) return null;
        return camisaRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        if (id == null) return;
        camisaRepository.deleteById(id);
    }

    public List<String> obtenerColores() {
        List<String> dynamicColors = new ArrayList<>(Arrays.asList("Todos", "Blanco", "Azul", "Verde", "Negro", "Rojo"));
        List<Camisa> camisas = camisaRepository.findAll();
        for (Camisa c : camisas) {
            String color = c.getColor();
            if (color != null && !color.trim().isEmpty()) {
                boolean exists = dynamicColors.stream().anyMatch(dc -> dc.equalsIgnoreCase(color.trim()));
                if (!exists) {
                    String capitalizedColor = color.trim().substring(0, 1).toUpperCase() + color.trim().substring(1).toLowerCase();
                    dynamicColors.add(capitalizedColor);
                }
            }
        }
        return dynamicColors;
    }
}
