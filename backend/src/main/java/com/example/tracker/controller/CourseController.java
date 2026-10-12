package com.example.tracker.controller;

import com.example.tracker.dto.*;
import com.example.tracker.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    @PostMapping("/{courseId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse addTask(@PathVariable Long courseId, @Valid @RequestBody CreateTaskRequest request) {
        return courseService.addTask(courseId, request);
    }

    @PatchMapping("/{courseId}/tasks/{taskId}")
    public TaskResponse updateTask(@PathVariable Long courseId, @PathVariable Long taskId,
                                   @Valid @RequestBody UpdateTaskRequest request) {
        return courseService.updateTask(courseId, taskId, request);
    }

    @DeleteMapping("/{courseId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long courseId, @PathVariable Long taskId) {
        courseService.deleteTask(courseId, taskId);
    }
}
