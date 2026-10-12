package com.example.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String name;

    @Column(length = 1000)
    private String description;

    private LocalDate targetDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<WorkTask> tasks = new ArrayList<>();

    protected Course() {}

    public Course(String name, String description, LocalDate targetDate) {
        this.name = name;
        this.description = description;
        this.targetDate = targetDate;
    }

    public void addTask(WorkTask task) {
        tasks.add(task);
        task.setCourse(this);
    }

    public void removeTask(WorkTask task) {
        tasks.remove(task);
        task.setCourse(null);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getTargetDate() { return targetDate; }
    public List<WorkTask> getTasks() { return tasks; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
}
