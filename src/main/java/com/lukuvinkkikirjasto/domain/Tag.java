package com.lukuvinkkikirjasto.domain;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Tag extends AbstractPersistable<Long> {

    private String name;
    
    @ManyToMany
    private List<Book> Books;
}
