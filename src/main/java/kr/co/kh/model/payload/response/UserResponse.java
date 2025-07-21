package kr.co.kh.model.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.kh.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String email;
    private String username;
    private Set<Role> roles;
    private Long id;
    private boolean active;
    private String name;
    private String birthdate;
    private Long roleno;

    public UserResponse(Long id, String username, String email, Set<Role> roles, String name, String birthdate ,Long roleno ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
        this.birthdate = birthdate;
        this.roleno= roleno;

    }

    public UserResponse(String username, String email, Set<Role> roles, Long id, boolean active, String name) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.id = id;
        this.active = active;
        this.name = name;
    }
}
