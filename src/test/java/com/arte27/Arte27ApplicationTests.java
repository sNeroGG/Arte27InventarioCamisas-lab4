package com.arte27;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.arte27.models.Camisa;
import com.arte27.models.Categoria;
import com.arte27.services.CamisaService;
import com.arte27.services.CategoriaService;

@SpringBootTest
class Arte27ApplicationTests {

    @Autowired
    private CamisaService camisaService;

    @Autowired
    private CategoriaService categoriaService;

    @Test
    void testCrudOperaciones() {
        Categoria nuevaCategoria = new Categoria(null, "Prueba", "Descripción de prueba", true);
        categoriaService.guardar(nuevaCategoria);
        assertNotNull(nuevaCategoria.getId());

        Categoria buscadaCat = categoriaService.buscarPorId(nuevaCategoria.getId());
        assertNotNull(buscadaCat);
        assertEquals("Prueba", buscadaCat.getNombre());

        Camisa nuevaCamisa = new Camisa(null, "Camisa de Prueba", 19.99, "Rojo", "M", 5, true, null, "Prueba");
        camisaService.guardar(nuevaCamisa);
        assertNotNull(nuevaCamisa.getId());

        Camisa buscadaCamisa = camisaService.buscarPorId(nuevaCamisa.getId());
        assertNotNull(buscadaCamisa);
        assertEquals("Camisa de Prueba", buscadaCamisa.getNombre());

        List<Camisa> todasLasCamisas = camisaService.buscarTodo();
        assertTrue(todasLasCamisas.stream().anyMatch(c -> c.getId().equals(nuevaCamisa.getId())));

        camisaService.eliminar(nuevaCamisa.getId());
        assertNull(camisaService.buscarPorId(nuevaCamisa.getId()));

        categoriaService.eliminar(nuevaCategoria.getId());
        assertNull(categoriaService.buscarPorId(nuevaCategoria.getId()));
    }
}
