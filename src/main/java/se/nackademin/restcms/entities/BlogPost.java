package se.nackademin.restcms.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blogpost")
@JsonIgnoreProperties(value = { "blog" })
public class BlogPost implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime lastUpdated;

    @Column
    @Lob
    private String postData;

    public BlogPost() {
    }


    public BlogPost(Blog blog, LocalDateTime created, LocalDateTime lastUpdated, String postData) {
        this.blog = blog;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.postData = postData;
    }
}
