package kr.co.kh.model;

import kr.co.kh.model.audit.DateAudit;
import kr.co.kh.validation.annotation.NullOrNotBlank;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "USERS")
@ToString
public class User extends DateAudit {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // vo에서쓰는 변수명은 id , DB에서 쓰는 column명은 USER_NUMBER
    private Long id;

    @NaturalId
    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Column(name = "USERNAME", unique = true, length = 30)
    @NullOrNotBlank(message = "아이디는 필수 항목입니다.")
    private String username;

    @Column(name = "PASSWORD")
    @NotNull(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Column(name = "NAME")
    @NullOrNotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    //2025-07-09 윤호야 여기 손봐야한다(성공)
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "ROLE_NO")
    private Long roleNo;

    //2025-07-09 조윤호 생년월일 성별 디비 정보 추가 작업 진행중
    @Column(name = "BIRTH_DATE")
    private String birthDate;

    @Column(name = "USER_GENDER")
    private String gender;

    //구독 여부
    @Column(name = "IS_SUB", nullable = false)
    private Boolean SUB;

    public Boolean getSUB() {
        return SUB;
    }

    public void setSUB(Boolean SUB) {
        this.SUB = SUB;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles = new HashSet<>();

    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
    private Boolean isEmailVerified;

    public User() {
        super();
    }

    public User(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        name = user.getName();
        email = user.getEmail();
        active = user.getActive();
        roles = user.getRoles();
        isEmailVerified = user.getEmailVerified();
        birthDate = user.getBirthDate();
        gender = user.getGender();
        //2025-07-07 조윤호
        roleNo = user.getRoleNo();
        //2025-07-23 구독 유뮤
        SUB = user.getSUB();
    }
    //2025-07-07 조윤호
    public Long getRoleNo() {
        return roleNo;
    }
    //2025-07-07 조윤호
    public void setRoleNo(Long roleNo) {
        this.roleNo = roleNo;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUserList().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUserList().remove(this);
    }

    public void markVerificationConfirmed() {
        setEmailVerified(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> authorities) {
        roles = authorities;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }


    public void setPaid(boolean b) {

    }
}
