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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by linzj on 2018.12.18 .
 */
public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${querymachine.libraryId}")
    private  Long libraryId;

    @Autowired
    private UserService userService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private PeopleRepository peopleRepository;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String readerCardNum = obtainUsername(request);
        //根据读者账号去查
        Reader reader = readerRepository.findReaderByReaderCardNumberAndLibraryId(readerCardNum,libraryId);
        if (reader == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
        People people = peopleRepository.findOne(reader.getPeopleId());
        if (people == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
        User user = userService.getUserById(people.getUserId(),reader.getReaderCardNumber(),reader.getLibraryId());



        if (!StringUtils.isEmpty(readerCardNum)) {
            request.setAttribute("remark", user.getCreateName());
            request.setAttribute("createName",user.getCreateName());
            return super.attemptAuthentication(request, response);
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
