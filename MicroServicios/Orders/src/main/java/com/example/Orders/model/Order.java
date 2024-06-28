package com.example.Orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El productId es obligatorio")
    private Long productId;

    @NotNull(message = "El unitPrice es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double unitPrice;

    @NotNull(message = "El quantity es obligatorio")
    @Min(value = 1, message = "Debe ser mayor a cero")
    private Long quantity;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double total;

    @NotNull(message = "El date es obligatorio")
    private LocalDate date;

    @NotBlank(message = "El notes es obligatorio")
    private String notes;
}
