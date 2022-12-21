package com.ural.authserver.service;

import com.ural.authserver.entities.ContextUser;
import com.ural.authserver.entities.Role;
import com.ural.authserver.entities.UserEntity;
import com.ural.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByEmail(username);

        if (user.isPresent()) {

            UserEntity userEntity = user.get();

            /*
               this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
             */

            return new ContextUser(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.isEnabled(),
                    userEntity.isAccountNonExpired(),
                    userEntity.isCredentialsNonExpired(),
                    userEntity.isAccountNonLocked(),
                    getAuthorities(userEntity.getRoles()),
                    userEntity.getFirstName(),
                    userEntity.getLastName()

            )
                    ;
        } else throw new UsernameNotFoundException("User not found for email" + username);


    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

}
