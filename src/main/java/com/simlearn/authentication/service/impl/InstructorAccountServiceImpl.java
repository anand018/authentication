package com.simlearn.authentication.service.impl;

import com.simlearn.authentication.dto.InstructorAccountResponseDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.exception.AccountNotFoundException;
import com.simlearn.authentication.service.InstructorAccountService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.simlearn.authentication.constants.ApplicatiopnConstants.ACCOUNT_NOT_FOUND;

@Service
public class InstructorAccountServiceImpl implements InstructorAccountService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<InstructorAccountResponseDto> findAllInstructors() {
        List<AccountEntity> accountEntities = mongoTemplate.find(new Query(Criteria.where("role").in("INSTRUCTOR")), AccountEntity.class);
        if(ObjectUtils.isEmpty(accountEntities))
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND);
        return createInstructorAccount(accountEntities);
    }
    private List<InstructorAccountResponseDto> createInstructorAccount(List<AccountEntity> accountEntities) {
        List<InstructorAccountResponseDto> instructorAccountResponseDtos = new ArrayList<>();
        accountEntities.stream().forEach(accountEntity -> {
            InstructorAccountResponseDto instructorAccountResponseDto = new InstructorAccountResponseDto();
            instructorAccountResponseDto.setEmail(accountEntity.getEmail());
            instructorAccountResponseDto.setUsername(accountEntity.getUsername());
            instructorAccountResponseDto.setFullName(accountEntity.getFirstName().concat(" ").concat(accountEntity.getLastName()));
            instructorAccountResponseDtos.add(instructorAccountResponseDto);
        });
        return instructorAccountResponseDtos;
    }
}
