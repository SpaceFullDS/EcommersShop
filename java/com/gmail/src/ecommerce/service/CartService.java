package com.gmail.src.ecommerce.service;

import com.gmail.src.ecommerce.domain.Perfume;

import java.util.List;

public interface CartService {

    List<Perfume> getPerfumesInCart(String username);

    void addPerfumeToCart(String username, Long perfumeId);

    void removePerfumeFromCart(String username, Long perfumeId);
}
