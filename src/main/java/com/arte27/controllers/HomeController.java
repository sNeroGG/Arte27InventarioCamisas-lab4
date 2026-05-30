package com.arte27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.arte27.models.Camisa;
import com.arte27.services.CamisaService;
import com.arte27.services.CategoriaService;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    
    @Autowired
    private CamisaService camisaService;
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/")
    public String index(
            @RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "color", required = false) String color,
            Model model) {
        
        List<Camisa> camisas = camisaService.buscarPorFiltros(buscar, color);
        List<String> colores = camisaService.obtenerColores();
        
        model.addAttribute("camisas", camisas);
        model.addAttribute("colores", colores);
        model.addAttribute("buscarActive", buscar != null ? buscar : "");
        model.addAttribute("colorActive", color != null ? color : "Todos");
        
        return "home";
    }

    @GetMapping("/camisas/nueva")
    public String crearCamisaForm(Model model) {
        Camisa camisa = new Camisa();
        camisa.setStock(10);
        camisa.setColor("Blanco");
        camisa.setTalla("M");
        
        List<String> listColores = new ArrayList<>(camisaService.obtenerColores().stream()
                .filter(c -> !c.equalsIgnoreCase("Todos"))
                .collect(Collectors.toList()));
        
        model.addAttribute("camisa", camisa);
        model.addAttribute("colores", listColores);
        model.addAttribute("tallas", List.of("S", "M", "L", "XL"));
        model.addAttribute("categorias", categoriaService.buscarTodo().stream()
                .filter(com.arte27.models.Categoria::isActivo)
                .collect(Collectors.toList()));
        
        return "nueva";
    }

    @GetMapping("/camisas/editar/{id}")
    public String editarCamisaForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Camisa camisa = camisaService.buscarPorId(id);
        if (camisa == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "La camisa especificada no existe.");
            return "redirect:/";
        }
        
        List<String> listColores = new ArrayList<>(camisaService.obtenerColores().stream()
                .filter(c -> !c.equalsIgnoreCase("Todos"))
                .collect(Collectors.toList()));
        
        if (camisa.getColor() != null && !camisa.getColor().trim().isEmpty()) {
            boolean exists = listColores.stream().anyMatch(lc -> lc.equalsIgnoreCase(camisa.getColor().trim()));
            if (!exists) {
                listColores.add(camisa.getColor().trim());
            }
        }
        
        model.addAttribute("camisa", camisa);
        model.addAttribute("colores", listColores);
        model.addAttribute("tallas", List.of("S", "M", "L", "XL"));
        model.addAttribute("categorias", categoriaService.buscarTodo().stream()
                .filter(com.arte27.models.Categoria::isActivo)
                .collect(Collectors.toList()));
        
        return "nueva";
    }

    @PostMapping("/camisas/guardar")
    public String guardarCamisa(
            @ModelAttribute("camisa") Camisa camisa,
            @RequestParam(name = "imagenArchivo", required = false) MultipartFile imagenArchivo,
            RedirectAttributes redirectAttributes) {
        
        String redirectTarget = camisa.getId() != null ? "redirect:/camisas/editar/" + camisa.getId() : "redirect:/camisas/nueva";
        
        if (camisa.getNombre() == null || camisa.getNombre().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "El nombre de la camisa no puede estar vacío.");
            return redirectTarget;
        }
        
        if (camisa.getPrecio() == null || camisa.getPrecio() < 0) {
            redirectAttributes.addFlashAttribute("errorMsg", "El precio debe ser un número válido mayor o igual a 0.");
            return redirectTarget;
        }
        
        if (camisa.getStock() == null || camisa.getStock() < 0) {
            redirectAttributes.addFlashAttribute("errorMsg", "El stock debe ser un número válido mayor o igual a 0.");
            return redirectTarget;
        }

        if (imagenArchivo != null && !imagenArchivo.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/images/";
                java.io.File directory = new java.io.File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                
                String targetDir = "target/classes/static/images/";
                java.io.File targetDirectory = new java.io.File(targetDir);
                if (!targetDirectory.exists()) {
                    targetDirectory.mkdirs();
                }
                
                String fileName = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.write(path, imagenArchivo.getBytes());
                
                java.nio.file.Path targetPath = java.nio.file.Paths.get(targetDir + fileName);
                java.nio.file.Files.write(targetPath, imagenArchivo.getBytes());
                
                camisa.setImagenUrl("/images/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMsg", "Error al subir la imagen: " + e.getMessage());
                return redirectTarget;
            }
        }
        
        camisaService.guardar(camisa);
        redirectAttributes.addFlashAttribute("successMsg", "¡Camisa '" + camisa.getNombre() + "' guardada correctamente!");
        return "redirect:/";
    }

    @GetMapping("/camisas/eliminar/{id}")
    public String eliminarCamisa(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Camisa camisa = camisaService.buscarPorId(id);
        if (camisa == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "La camisa que intenta eliminar no existe.");
            return "redirect:/";
        }
        
        camisaService.eliminar(id);
        redirectAttributes.addFlashAttribute("successMsg", "¡Camisa '" + camisa.getNombre() + "' eliminada correctamente!");
        return "redirect:/";
    }
}
