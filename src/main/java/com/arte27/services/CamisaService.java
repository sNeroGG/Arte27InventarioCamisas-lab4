package com.arte27.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arte27.models.Camisa;

@Service
public class CamisaService {
    private final List<Camisa> camisas = new ArrayList<>();
    private int currentId = 1;

    public CamisaService() {
        // Populate initial premium inventory
        guardarCamisa(new Camisa(null, "Camisa Lino Premium", 45.99, "Blanco", "M", 15, true, null, "Formal"));
        guardarCamisa(new Camisa(null, "Camisa Oxford Casual", 39.50, "Azul", "L", 22, true, null, "Casual"));
        guardarCamisa(new Camisa(null, "Camisa Mao Algodón", 35.00, "Verde", "S", 8, true, null, "Casual"));
        guardarCamisa(new Camisa(null, "Camisa Formal Satín", 55.00, "Negro", "XL", 12, true, null, "Formal"));
        guardarCamisa(new Camisa(null, "Camisa Casual Leñador", 32.99, "Rojo", "M", 0, false, null, "Oversize"));
        guardarCamisa(new Camisa(null, "Camisa Slim Fit Lino", 48.00, "Blanco", "L", 10, true, null, "Formal"));
    }

    public List<Camisa> listarCamisas() {
        return new ArrayList<>(camisas);
    }

    public List<Camisa> buscarCamisas(String query, String color) {
        return camisas.stream()
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

    public synchronized void guardarCamisa(Camisa camisa) {
        if (camisa.getId() == null) {
            camisa.setId(currentId++);
        }
        // If image URL is not provided, assign the default white shirt image
        if (camisa.getImagenUrl() == null || camisa.getImagenUrl().trim().isEmpty()) {
            camisa.setImagenUrl("/images/camisa_blanca.png");
        }
        
        // Ensure stock is updated correctly
        if (camisa.getStock() == null) {
            camisa.setStock(0);
        }
        camisa.setDisponible(camisa.getStock() > 0);

        // Replace if exists, else add
        int index = -1;
        for (int i = 0; i < camisas.size(); i++) {
            if (camisas.get(i).getId().equals(camisa.getId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            camisas.set(index, camisa);
        } else {
            camisas.add(camisa);
        }
    }

    public synchronized Camisa buscarCamisa(Integer id) {
        if (id == null) return null;
        return camisas.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
    }

    public synchronized void eliminarCamisa(Integer id) {
        if (id == null) return;
        camisas.removeIf(c -> id.equals(c.getId()));
    }

    public List<String> colores() {
        List<String> dynamicColors = new ArrayList<>(Arrays.asList("Todos", "Blanco", "Azul", "Verde", "Negro", "Rojo"));
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
