package com.app.test.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.app.test.roles.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username" })
    }
)
@Getter
@Setter
@ToString
public class Users extends BaseEntity {
    private String username;
    private String password;
    
    private List<String> authorities = new ArrayList<String>();

    public void setAuthorities(Roles... roles) {
        for (Roles role: roles) {
            this.authorities.add(role.name());
        }
    }
}
