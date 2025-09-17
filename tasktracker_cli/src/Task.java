import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements Serializable{
    int id;
    String description;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        TO_DO,
        IN_PROGRESS,
        DONE
    };

    public Task (int id, String description, Status status){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    

    @Override
    public String toString(){
        return "{\n\t\t\"id\":" + "\"" + id + "\"," + "\n\t\t\"description\":" + "\"" + description + "\"," + "\n\t\t\"status\":" + "\"" + status + "\"," + "\n\t\t\"createdAt\":" + "\"" + createdAt + "\"," + "\n\t\t\"updatedAt\":" + "\"" + updatedAt + "\"" + "\n\t}";
    }
}
