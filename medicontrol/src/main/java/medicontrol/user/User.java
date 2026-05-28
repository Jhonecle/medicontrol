package medicontrol.user;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    // =========================================
    // ID
    // =========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================
    // NOME
    // =========================================
    private String nome;

    // =========================================
    // EMAIL
    // =========================================
    @Column(unique = true)
    private String email;

    // =========================================
    // SENHA
    // =========================================
    private String senha;

    // =========================================
    // ROLE
    // =========================================
    @Enumerated(EnumType.STRING)
    private Role role;

    // =========================================
    // GETTERS E SETTERS
    // =========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // =========================================
    // SPRING SECURITY
    // =========================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );
    }

    @Override
    public String getPassword() {
        return senha;
    }

   @Override
public String getUsername() {
    return email;
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
    return true;
    }
}
