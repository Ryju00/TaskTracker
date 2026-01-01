package com.theo.tasktracker.controller;

import com.theo.tasktracker.entity.Task;
import com.theo.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;



@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping    // Lista zadań
    public String list(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        model.addAttribute("task", new Task());
        return "tasks/list";  // templates/tasks/list.html
    }

    @PostMapping  // Dodaj
    public String save(@Valid @ModelAttribute Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tasks", service.getAllTasks());
            return "tasks/list";
        }
        service.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")  // Edytuj
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("task", service.getTaskById(id).orElse(new Task()));
        model.addAttribute("tasks", service.getAllTasks());
        return "tasks/list";
    }

    @PostMapping("/delete/{id}")  // Usuń
    public String delete(@PathVariable Long id) {
        service.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        model.addAttribute("tasks", service.searchTasks(name));
        model.addAttribute("task", new Task());
        return "tasks/list";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        model.addAttribute("tasks", service.getCompletedTasks());
        model.addAttribute("task", new Task());
        return "tasks/list";
    }

    @GetMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Task> optionalTask = service.getTaskById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(!task.isCompleted());
            service.saveTask(task);
        }
        return "redirect:/tasks";
    }
}

