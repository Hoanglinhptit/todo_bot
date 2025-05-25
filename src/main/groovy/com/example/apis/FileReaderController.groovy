package com.example.apis;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RestController
public class FileReaderController {

    private static final int NUM_THREADS = 10;
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    static int count = 0;


    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file: " + e.getMessage();
        }

        // Tạo các luồng để xử lý các dòng trong hàng đợi
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(this::processQueue);
        }

        executor.shutdown();
        return "File processing started!";
    }

    // Hàm xử lý các dòng từ hàng đợi
    private void processQueue() {
        try {
            String line;
            while ((line = queue.poll()) != null) {  // Lấy một dòng từ hàng đợi, hoặc null nếu hàng đợi rỗng
                System.out.println("Thread " + Thread.currentThread().getName() + " processing: " + line);
                count++;
                // Thực hiện logic xử lý dòng (ví dụ gửi request với dữ liệu)
            }
            System.out.println(count)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
