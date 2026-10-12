package com.example.tracker.dto;

import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @Size(max = 220) String title,
        Boolean completed
) {}
