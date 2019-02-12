package com.zt.queryplatform.security;

import com.zt.queryplatform.entity.People;
import com.zt.queryplatform.entity.Reader;
import com.zt.queryplatform.entity.User;
import com.zt.queryplatform.repository.PeopleRepository;
import com.zt.queryplatform.repository.ReaderRepository;
import com.zt.queryplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义认证实现
 * Created by linzj on 2018.12.19 .
 */
public class AuthProvider implements AuthenticationProvider {

    @Value("${querymachine.libraryId}")
    private  Long libraryId;

    @Autowired
    private UserService userService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    private final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String readerCardNum = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();

        //根据读者账号去查
        Reader reader = readerRepository.findReaderByReaderCardNumberAndLibraryId(readerCardNum,libraryId);
        if (reader == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
        People people = peopleRepository.findOne(reader.getPeopleId());
        if (people == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
        User user = userService.getUserById(people.getUserId(),readerCardNum,libraryId);

        if (this.passwordEncoder.isPasswordValid(user.getPassword(), inputPassword, null)) {
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("authError");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
