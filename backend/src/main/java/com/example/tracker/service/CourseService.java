package com.example.tracker.service;

import com.example.tracker.dto.*;
import com.example.tracker.model.Course;
import com.example.tracker.model.WorkTask;
import com.example.tracker.repository.CourseRepository;
import com.example.tracker.repository.WorkTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final WorkTaskRepository taskRepository;

    public CourseService(CourseRepository courseRepository, WorkTaskRepository taskRepository) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCourses() {
        return courseRepository.findAll().stream().map(CourseResponse::from).toList();
    }

    @Transactional
    public CourseResponse createCourse(CreateCourseRequest request) {
        Course course = new Course(request.name().trim(),
                request.description() == null ? "" : request.description().trim(),
                request.targetDate());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse addTask(Long courseId, CreateTaskRequest request) {
        Course course = findCourse(courseId);
        course.addTask(new WorkTask(request.title().trim()));
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public TaskResponse updateTask(Long courseId, Long taskId, UpdateTaskRequest request) {
        Course course = findCourse(courseId);
        WorkTask task = course.getTasks().stream().filter(t -> t.getId().equals(taskId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (request.title() != null && !request.title().isBlank()) task.setTitle(request.title().trim());
        if (request.completed() != null) task.setCompleted(request.completed());
        taskRepository.save(task);
        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(Long courseId, Long taskId) {
        Course course = findCourse(courseId);
        WorkTask task = course.getTasks().stream().filter(t -> t.getId().equals(taskId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Task not found"));
        course.removeTask(task);
        courseRepository.save(course);
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }
}
