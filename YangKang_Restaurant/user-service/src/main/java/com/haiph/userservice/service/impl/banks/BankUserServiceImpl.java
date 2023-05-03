//package com.haiph.userservice.service.impl.banks;
//
//import com.haiph.common.dto.response.Response;
//import com.haiph.common.exception.CommonException;
//import com.haiph.userservice.entity.bank.BankUser;
//import com.haiph.userservice.model.request.banks.BankUserCreate;
//import com.haiph.userservice.model.request.banks.BankUserUpdate;
//import com.haiph.userservice.model.response.dto.BankUserDTO;
//import com.haiph.userservice.repository.bank.BankUserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class BankUserServiceImpl implements com.haiph.userservice.service.BankUserService {
//    @Autowired
//    private BankUserRepository bankUserRepository;
//    @Autowired
//    private ModelMapper mapper;
//
//    public Page<BankUserDTO> findPage(Pageable pageable) {
//        Page<BankUser> page =bankUserRepository.findAll(pageable);
//        List<BankUser> bankUsers = page.getContent();
//        List<BankUserDTO> dtos = new ArrayList<>();
//        for (BankUser bankUser : bankUsers) {
//            BankUserDTO user = BankUserDTO
//                    .build(
//                            bankUser.getBankUserNumber(),
//                            bankUser.getUserOwenrName(),
//                            bankUser.getBankName(),
//                            bankUser.getCountPrice()
//            );
//            dtos.add(user);
//        }
//        return new PageImpl<>(dtos,pageable, page.getTotalPages());
//    }
//
//    @Override
//    public BankUserDTO findById(Integer id) {
//        BankUser bankUser =  bankUserRepository.findById(id).orElseThrow(() -> {throw new CommonException(Response.NOT_FOUND,"Cannot find id: " +id);});
//        BankUserDTO user = BankUserDTO
//                .build(
//                        bankUser.getBankUserNumber(),
//                        bankUser.getUserOwenrName(),
//                        bankUser.getBankName(),
//                        bankUser.getCountPrice()
//                );
//        return user;
//    }
//
//    @Override
//    public String create(BankUserCreate create) {
//        BankUser bankUser = new BankUser(
//                create.getPersonCode(),
//                create.getBankUserNumber(),
//                create.getUserOwenrName(),
//                create.getBankName(),
//                0d
//        );
//        bankUserRepository.save(bankUser);
//        return "Create Success";
//    }
//
//    @Override
//    public String update(BankUserUpdate update) {
//        BankUserDTO bankUser = findById(update.getId());
//        if (bankUser!= null) {
//            BankUser updateNew = BankUser.build(
//                    update.getPersonCode(),
//                    update.getBankUserNumber(),
//                    update.getUserOwenrName(),
//                    update.getBankName(),
//                    0d
//            );
//            bankUserRepository.save(updateNew);
//            return "update Success";
//        }
//        return "update Success";
//    }
//
//    @Override
//    public String deleteById(Integer id) {
//        bankUserRepository.deleteById(id);
//    }
//}
