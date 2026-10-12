package com.example.tracker.dto;

import com.example.tracker.model.Course;
import java.time.LocalDate;
import java.util.List;

public record CourseResponse(
        Long id,
        String name,
        String description,
        LocalDate targetDate,
        int totalTasks,
        int completedTasks,
        int progressPercent,
        List<TaskResponse> tasks
) {
    public static CourseResponse from(Course course) {
        List<TaskResponse> tasks = course.getTasks().stream().map(TaskResponse::from).toList();
        int total = tasks.size();
        int completed = (int) tasks.stream().filter(TaskResponse::completed).count();
        int percent = total == 0 ? 0 : Math.round(completed * 100.0f / total);
        return new CourseResponse(course.getId(), course.getName(), course.getDescription(),
                course.getTargetDate(), total, completed, percent, tasks);
    }
}
