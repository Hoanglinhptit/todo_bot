package com.example.repositories

import com.example.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByChatId(Long chatId)
    Optional<Task> findByIdAndChatId(Long id, Long chatId)
    void deleteByIdAndChatId(Long id, Long chatId)
}
