package model.base;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractJPAEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdObiektuDanych() {
        return null;
    }
}
