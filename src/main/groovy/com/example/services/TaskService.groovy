package com.example.services;

import com.example.entities.Task;
import com.example.repositories.TaskRepository;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private FF4j ff4j;

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksByChatId(Long chatId) {
        if (ff4j.check("get-task-feature")) {
            return taskRepository.findByChatId(chatId);
        }
        throw new RuntimeException("get-task-feature is not enabled");
    }

    public Optional<Task> getTaskByIdAndChatId(Long id, Long chatId) {
        if (ff4j.check("get-task-feature")) {
            return taskRepository.findByIdAndChatId(id, chatId);
        }
        throw new RuntimeException("get-task-feature is not enabled");
    }

    public Task saveOrUpdateTask(Task task) {
        if (ff4j.check("update-task-feature")) {
            return taskRepository.save(task);
        }

        throw new RuntimeException("update-task-feature is not enabled");
    }

    @Transactional
    public void deleteTaskByIdAndChatId(Long id, Long chatId) {
        if (ff4j.check("delete-task-feature")) {
            taskRepository.deleteByIdAndChatId(id, chatId);
        } else {
            throw new RuntimeException("delete-task-feature is not enabled");
        }
    }
}
