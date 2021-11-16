package examTask.users.entities;

import lombok.ToString;
import org.hibernate.Hibernate;
import javax.persistence.*;

//@Table(name="users", schema = "public")
@Entity
@ToString(of = {"id", "name", "email", "status"})
public class Users {

    @Id
    @SequenceGenerator(name = "users", sequenceName = "userSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users")
    private long id;

    private String name;
    private String email;
    private String status;

    public Users() {
    }

    public Users(String name, String email, String status) {
//      this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    //    @Column(updatable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime newUserCreationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public LocalDateTime getNewUserCreationTime() {
//        return newUserCreationTime;
//    }
//
//    public void setNewUserCreationTime(LocalDateTime newUserCreationTime) {
//        this.newUserCreationTime = newUserCreationTime;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Users users = (Users) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
