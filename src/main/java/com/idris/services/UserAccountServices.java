package com.idris.services;

import com.idris.entity.UserAccount;
import com.idris.entity.UserStatus;
import com.idris.repos.UserAccountRepository;
import com.idris.repos.UserStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserAccountServices {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    public List<UserAccount> findAll(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return userAccountRepository.findAll(pageable).toList();


    }

    public UserAccount findById(long id){
        return userAccountRepository.findById(id).orElse(null);

    }

    public UserAccount delById(long id){
        UserAccount userAccount = userAccountRepository.findById(id).orElse(null);
        if(Objects.nonNull(userAccount)){
            userAccount.setDeactivatedDate(new Date());
            UserStatus userStatus = userStatusRepository.findByName("DEACTIVATED").orElse(null);
            userAccount.setStatus(userStatus);
            return userAccountRepository.save(userAccount);
        }

        return userAccount;
    }
}
