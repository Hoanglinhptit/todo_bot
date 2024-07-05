package com.example.services


import com.example.entities.Task
import com.example.repositories.TaskRepository
import org.ff4j.FF4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService {
    @Autowired
    private FF4j ff4j;

    @Autowired
    private TaskRepository taskRepository

    List<Task> getAllTasksByChatId(Long chatId) {
        taskRepository.findByChatId(chatId)
    }

    Optional<Task> getTaskByIdAndChatId(Long id, Long chatId) {
        taskRepository.findByIdAndChatId(id, chatId)
    }

    Task saveOrUpdateTask(Task task) {
        taskRepository.save(task)
    }

    void deleteTaskByIdAndChatId(Long id, Long chatId) {
        taskRepository.deleteByIdAndChatId(id, chatId)
    }
}
