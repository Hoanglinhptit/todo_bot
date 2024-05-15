package repositories

import entities.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository extends JpaRepository<TaskEntity,String> {


}