package com.gravifox.tvb.domain.member.service.impl;
import com.gravifox.tvb.domain.admin.domain.Term;
import com.gravifox.tvb.domain.member.domain.UserTerm;
import com.gravifox.tvb.domain.member.repository.UserTermRepository;
import com.gravifox.tvb.domain.member.service.UserTermService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional
@RequiredArgsConstructor
public class UserTermServiceImpl implements UserTermService {
    @Autowired
    private UserTermRepository userTermRepository;

    @Override
    public void agreeToTerm(Long userNo, Long termId) {
//        UserTerm userTerm = UserTerm.builder()
//                .userNo(userNo)
//                .termId(term.getTermId())
//                .agreedVersion(term.getTermVersion())
//                .build();

    }
}
