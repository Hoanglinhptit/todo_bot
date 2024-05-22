package com.example.apis


import com.example.entities.Task
import com.example.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskControllers {

    @Autowired
    private TaskService taskService

    @GetMapping("/test")
    String displayData() {
        String message = "Welcome to GeeksForGeeks"
        return message
    }

    @GetMapping("/list")
    List<Task> getAllTasksByChatId(@RequestBody Long chatId) {
        taskService.getAllTasksByChatId(chatId)
    }

    @GetMapping("/detail/{id}")
    Task getTaskByIdAndChatId(@PathVariable Long id, @RequestBody Long chatId) {
        taskService.getTaskByIdAndChatId(id, chatId)
                .orElseThrow { new RuntimeException("Task not found with id: $id for chatId: $chatId") }
    }

    @PostMapping("/create")
    Task createTask(@RequestBody Task task) {
        taskService.saveOrUpdateTask(task)
    }

    @PutMapping("/update/{id}")
    Task updateTask(@PathVariable Long id, @RequestBody Long chatId, @RequestBody Task task) {
        task.id = id
        task.chatId = chatId
        taskService.saveOrUpdateTask(task)
    }

    @DeleteMapping("/delete/{id}")
    void deleteTask(@PathVariable Long id, @RequestParam Long chatId) {
        taskService.deleteTaskByIdAndChatId(id, chatId)
    }
}
