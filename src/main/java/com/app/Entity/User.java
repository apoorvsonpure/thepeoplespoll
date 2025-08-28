package com.app.Entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails
{

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    private String phone;

    @NotBlank
    private String instaAccountLink;

    private Boolean isUserLoggedIn;

    private Boolean isAccountVerified;

    private Boolean isFirstLogin;

    private List<GrantedAuthority> springSecurityRoles;


    public @NotBlank String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName)
    {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName()
    {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Boolean isAccountVerified()
    {
        return isAccountVerified;
    }

    public void setAccountVerified(Boolean accountVerified)
    {
        isAccountVerified = accountVerified;
    }

    public @NotBlank String getEmail()
    {
        return email;
    }

    public void setEmail(@NotBlank String email)
    {
        this.email = email;
    }

    @ElementCollection
    @Override
    public List<GrantedAuthority> getAuthorities()
    {
        return this.getSpringSecurityRoles();
    }

    public @NotBlank String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    public void setPassword(@NotBlank String password)
    {
        this.password = password;
    }

    public @NotBlank String getCity()
    {
        return city;
    }

    public void setCity(@NotBlank String city)
    {
        this.city = city;
    }

    public @NotBlank String getState()
    {
        return state;
    }

    public void setState(@NotBlank String state)
    {
        this.state = state;
    }

    public @NotBlank String getInstaAccountLink()
    {
        return instaAccountLink;
    }

    public void setInstaAccountLink(@NotBlank String instaAccountLink)
    {
        this.instaAccountLink = instaAccountLink;
    }

    public Boolean isUserLoggedIn()
    {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(Boolean userLoggedIn)
    {
        isUserLoggedIn = userLoggedIn;
    }

    public Boolean isFirstLogin()
    {
        return isFirstLogin;
    }

    public void setFirstLogin(Boolean firstLogin)
    {
        isFirstLogin = firstLogin;
    }

    @ElementCollection
    public List<GrantedAuthority> getSpringSecurityRoles()
    {
        return springSecurityRoles;
    }

    public void setSpringSecurityRoles(
            List<GrantedAuthority> springSecurityRoles)
    {
        this.springSecurityRoles = springSecurityRoles;
    }
}
