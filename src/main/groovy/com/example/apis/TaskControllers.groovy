package com.example.apis


import com.example.entities.Task
import com.example.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

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
    List<Task> getAllTasksByChatId(@RequestParam("chatId") Long chatId) {
        taskService.getAllTasksByChatId(chatId)
    }

    @GetMapping("/detail/{id}")
    Task getTaskByIdAndChatId(@PathVariable("id") Long id, @RequestParam("chatId") Long chatId) {
        taskService.getTaskByIdAndChatId(id, chatId)
                .orElseThrow { new RuntimeException("Task not found with id: $id for chatId: $chatId") }
    }

    @PostMapping("/create")
    Task createTask(@RequestBody Task task) {
        taskService.saveOrUpdateTask(task)
    }

    @PutMapping("/{id}")
    Task updateTask(@PathVariable("id") Long id, @RequestParam("chatId") Long chatId, @RequestBody Task task) {
        task.id = id
        task.chatId = chatId
        taskService.saveOrUpdateTask(task)
    }

    @DeleteMapping("/{id}")
    String deleteTask(@PathVariable("id") Long id, @RequestParam("chatId") Long chatId) {
        taskService.deleteTaskByIdAndChatId(id, chatId)
        return "OK"
    }

    @PostMapping("/upload")
    String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Đọc dữ liệu từ file vào hàng đợi
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                queue.add(line);
            }
        }

        // Bắt đầu gửi yêu cầu từ hàng đợi tới API
        sendRequests(queue);
        return "File uploaded and processing started";
    }

    private void sendRequests(BlockingQueue<String> queue) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int TPS = 10;
        AtomicInteger index = new AtomicInteger(0);  // Using AtomicInteger to keep track of the index

        Runnable task = () -> {
            for (int i = 0; i < TPS; i++) {
                String request = queue.poll();
                if (request == null) {
                    scheduler.shutdown();
                    break;
                }
                sendRequest(request, index.getAndAdd(10));  // Use getAndAdd to increment and retrieve the current index
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    private void sendRequest(String request, int index) {
        // Print the index along with the request
        System.out.println("Line " + index + ": " + request);
    }
}
