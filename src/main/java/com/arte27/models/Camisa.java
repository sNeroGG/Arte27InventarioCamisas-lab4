package com.arte27.models;

import java.util.Date;

public class Camisa {
    private Integer id;
    private String nombre;
    private Double precio;
    private String color; // e.g. "Blanco", "Negro", "Azul", "Rojo", "Verde"
    private String talla; // e.g. "S", "M", "L", "XL"
    private Integer stock;
    private Boolean disponible;
    private Date fechaRegistro;
    private String imagenUrl;
    private String categoria;

    // Default constructor
    public Camisa() {
        this.fechaRegistro = new Date();
    }

    // Constructor with fields
    public Camisa(Integer id, String nombre, Double precio, String color, String talla, Integer stock, Boolean disponible, String imagenUrl, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.color = color;
        this.talla = talla;
        this.stock = stock;
        this.disponible = disponible;
        this.fechaRegistro = new Date();
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getColorHex() {
        if (color == null) return "#6b7280"; // fallback grey
        switch (color.toLowerCase().trim()) {
            case "blanco":
                return "#ffffff";
            case "azul":
                return "#3b82f6";
            case "verde":
                return "#10b981";
            case "negro":
                return "#1e293b";
            case "rojo":
                return "#ef4444";
            case "amarillo":
                return "#eab308";
            case "naranja":
                return "#f97316";
            case "rosa":
                return "#ec4899";
            case "morado":
            case "púrpura":
            case "purpura":
                return "#a855f7";
            case "gris":
                return "#6b7280";
            case "café":
            case "cafe":
            case "marrón":
            case "marron":
                return "#78350f";
            case "celeste":
                return "#38bdf8";
            case "turquesa":
                return "#2dd4bf";
            default:
                return "#6b7280";
        }
    }
}
