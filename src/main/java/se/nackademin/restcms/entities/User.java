package se.nackademin.restcms.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor

@Entity
@ToString(exclude = {"blog"})
@Table(name = "user")
@JsonIgnoreProperties(value = { "password"})
@EqualsAndHashCode(exclude="blog")
public class User implements UserDetails {
    public User(String username, String email, String password, Authority authority) {
        this.roles.add(authority);
        this.email = email;
        this.password = password;
        this.username=username;
        enabled=true;
    }
    public User(String email, String password, Authority authority) {
        this.roles.add(authority);
        this.email = email;
        this.password = password;
        enabled=true;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        enabled=true;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Blog blog;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column
    @Lob
    private String profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> roles = new HashSet<>();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
