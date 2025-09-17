package mohamed.code81.lms.common.service;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.auth.user.UserRepository;
import mohamed.code81.lms.auth.user.exception.UserNotFoundException;
import mohamed.code81.lms.auth.user.projection.EmailAndPasswordAndRoleProjection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmailAndPasswordAndRoleProjection user = this.userRepository.findEmailAndPasswordAndRoleByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.email())
                .password(user.password())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.role().toString())))
                .build();
    }
}
