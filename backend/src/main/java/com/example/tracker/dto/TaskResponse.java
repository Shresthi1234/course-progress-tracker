package com.example.tracker.dto;

import com.example.tracker.model.WorkTask;

public record TaskResponse(Long id, String title, boolean completed) {
    public static TaskResponse from(WorkTask task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.isCompleted());
    }
}
