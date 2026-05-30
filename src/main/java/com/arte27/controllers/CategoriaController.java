package com.arte27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.arte27.models.Categoria;
import com.arte27.services.CategoriaService;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> listado = categoriaService.listarCategorias();
        model.addAttribute("categorias", listado);
        return "categorias/lista";
    }

    @GetMapping("/nueva")
    public String crearCategoriaForm(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoriaForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Categoria categoria = categoriaService.buscarCategoria(id);
        if (categoria == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "La categoría específica no existe.");
            return "redirect:/categorias";
        }
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(
            @ModelAttribute("categoria") Categoria categoria,
            RedirectAttributes redirectAttributes) {
        
        String redirectTarget = categoria.getId() != null ? "redirect:/categorias/editar/" + categoria.getId() : "redirect:/categorias/nueva";
        
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "El nombre de la categoría no puede estar vacío.");
            return redirectTarget;
        }
        
        categoriaService.guardarCategoria(categoria);
        redirectAttributes.addFlashAttribute("successMsg", "¡Categoría '" + categoria.getNombre() + "' guardada correctamente!");
        return "redirect:/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Categoria categoria = categoriaService.buscarCategoria(id);
        if (categoria == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "La categoría que intenta eliminar no existe.");
            return "redirect:/categorias";
        }
        
        categoriaService.eliminarCategoria(id);
        redirectAttributes.addFlashAttribute("successMsg", "¡Categoría '" + categoria.getNombre() + "' eliminada correctamente!");
        return "redirect:/categorias";
    }
}
