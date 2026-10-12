package com.example.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateCourseRequest(
        @NotBlank @Size(max = 140) String name,
        @Size(max = 1000) String description,
        LocalDate targetDate
) {}
