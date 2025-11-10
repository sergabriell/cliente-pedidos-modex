package br.com.unifavip.cliente_pedidos.models.user;

import br.com.unifavip.cliente_pedidos.models.commons.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_group")
public class UserGroup extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_group_sequence")
    @SequenceGenerator(name = "user_group_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_admin", nullable = false)
    private boolean admin;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "observation")
    private String observation;

    @OneToMany(mappedBy = "userGroup")
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_group_mapping",
            joinColumns = @JoinColumn(name = "user_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserGroup userGroup = (UserGroup) o;
        return id != null && Objects.equals(id, userGroup.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
