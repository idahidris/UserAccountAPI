package com.idris.services;

import com.idris.dto.UserAccountDto;
import com.idris.entity.UserAccount;
import com.idris.entity.UserRole;
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

        return new UserAccount();
    }


    public UserAccount create(UserAccountDto userAccountdto){

        UserAccount userAccount = toUserAccount(userAccountdto);
        UserAccount existUser = userAccountRepository.getByEmail(userAccount.getEmail()).orElse(null);
       return (Objects.isNull(existUser))? userAccountRepository.save(userAccount): existUser;
    }


    public UserAccount update(UserAccountDto userAccount, Long id){
           UserAccount userDb = toUserAccount(userAccount, id);
           return Objects.isNull(userDb)? new UserAccount(): userAccountRepository.save(userDb);

    }


    public UserAccount toUserAccount(UserAccountDto userAccountDto){
        return toUserAccount(userAccountDto, 0L);
    }


    public UserAccount toUserAccount(UserAccountDto userAccountDto, long id){

        UserAccount userAccount = new UserAccount();

        if(id !=0){
            userAccount = userAccountRepository.findById(id).orElse(null);
        }

        if(Objects.nonNull(userAccount)) {
            if (Objects.nonNull(userAccountDto.getMobile())) {
                userAccount.setMobile(userAccountDto.getMobile());
            }
            if (Objects.nonNull(userAccountDto.getPassword())) {
                userAccount.setPassword(userAccountDto.getPassword());
            }

            if (Objects.nonNull(userAccountDto.getDeactivatedDate())) {
                userAccount.setDeactivatedDate(userAccountDto.getDeactivatedDate());
            }

            if (Objects.nonNull(userAccountDto.getRegistered())) {
                userAccount.setRegistered(userAccountDto.getRegistered());
            }

            if (userAccountDto.getRole() != 0) {
                UserRole userRole = new UserRole();
                if(Objects.nonNull(userAccount.getRole())){
                    userRole = userAccount.getRole();
                }
                userRole.setId(userAccountDto.getRole());
                userAccount.setRole(userRole);
            }
            if (Objects.nonNull(userAccountDto.getTitle())) {
                userAccount.setTitle(userAccountDto.getTitle());
            }

            if (userAccountDto.getStatus() != 0) {
                UserStatus userStatus = new UserStatus();
                if(Objects.nonNull(userAccount.getStatus())){
                    userStatus = userAccount.getStatus();
                }
                userStatus.setId(userAccountDto.getStatus());
                userAccount.setStatus(userStatus);
            }


            if (Objects.nonNull(userAccountDto.getVerifiedDate())) {
                userAccount.setVerifiedDate(userAccountDto.getVerifiedDate());
            }

            if (Objects.nonNull(userAccountDto.getVerified())) {
                userAccount.setVerified(userAccountDto.getVerified().equals("1"));
            }
            if (Objects.nonNull(userAccountDto.getFirstname())) {
                userAccount.setFirstname(userAccountDto.getFirstname());
            }

            if (Objects.nonNull(userAccountDto.getLastname())) {
                userAccount.setLastname(userAccountDto.getLastname());
            }

        }

        return userAccount;

    }
}
