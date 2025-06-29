package com.gmail.src.ecommerce.service.Impl;

import com.gmail.src.ecommerce.domain.Perfume;
import com.gmail.src.ecommerce.domain.User;
import com.gmail.src.ecommerce.repos.PerfumeRepository;
import com.gmail.src.ecommerce.repos.UserRepository;
import com.gmail.src.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final PerfumeRepository perfumeRepository;

    @Override
    public List<Perfume> getPerfumesInCart(String username) {
        User user = userRepository.findByUsername(username);
        return user.getPerfumeList();
    }

    @Override
    @Transactional
    public void addPerfumeToCart(String username, Long perfumeId) {
        User user = userRepository.findByUsername(username);
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().add(perfume);
    }

    @Override
    @Transactional
    public void removePerfumeFromCart(String username, Long perfumeId) {
        User user = userRepository.findByUsername(username);
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().remove(perfume);
    }
}
