package com.gmail.src.ecommerce.controller;

import com.gmail.src.ecommerce.domain.Perfume;
import com.gmail.src.ecommerce.repos.PerfumeRepository;
import com.gmail.src.ecommerce.service.PerfumeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PerfumeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerfumeRepository perfumeRepository;

    @Autowired
    private PerfumeService perfumeService;

    @Autowired
    private PerfumeController perfumeController;

    @Test
    public void mainMenuTest() throws Exception {
        List<Perfume> perfumes = new ArrayList<>();
        Perfume perfume = new Perfume();
        perfumes.add(perfume);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Perfume> page = new PageImpl<>(perfumes);

        when(perfumeRepository.findAll(pageable)).thenReturn(page);

        assertNotNull(perfumes);
        assertEquals(1, perfumeService.getPerfumes(pageable).getSize());
    }

    @Test
    public void findByPerfumerTest() throws Exception {
        List<Perfume> perfumes = new ArrayList<>();
        Perfume perfume = new Perfume();
        perfume.setPerfumer("Dior");
        perfumes.add(perfume);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Perfume> page = new PageImpl<>(perfumes);

        when(perfumeRepository.findByPerfumer(perfume.getPerfumer(), pageable)).thenReturn(page);

        assertNotNull(perfumes);
        assertEquals(1, perfumeService.getPerfumesByPerfumer(perfume.getPerfumer(), pageable).getSize());
        assertEquals("Dior", perfumeService.getPerfumesByPerfumer(perfume.getPerfumer(), pageable)
                .getContent().get(0).getPerfumer());
    }

    @Test
    public void findByPerfumeGenderTest() throws Exception {
        List<Perfume> perfumes = new ArrayList<>();
        Perfume perfume = new Perfume();
        perfume.setPerfumeGender("мужской");
        perfumes.add(perfume);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Perfume> page = new PageImpl<>(perfumes);

        when(perfumeRepository.findByPerfumeGender(perfume.getPerfumeGender(), pageable)).thenReturn(page);

        assertNotNull(perfumes);
        assertEquals(1, perfumeService.getPerfumesByGender(perfume.getPerfumeGender(), pageable).getSize());
        assertEquals("мужской", perfumeService.getPerfumesByGender(perfume.getPerfumeGender(), pageable)
                .getContent().get(0).getPerfumeGender());
    }

    @Test
    public void searchByParametersTest() throws Exception {
        List<Perfume> perfumes = new ArrayList<>();
        Perfume perfume = new Perfume();
        perfume.setPerfumeGender("мужской");
        perfume.setPerfumer("Dior");
        perfume.setPrice(1000);
        perfumes.add(perfume);

        Pageable pageable = PageRequest.of(0, 12);
        Page<Perfume> page = new PageImpl<>(perfumes);

        when(perfumeRepository.findByPriceBetween(900, 1100, pageable)).thenReturn(page);
        when(perfumeRepository.findByPerfumerIn(Collections.singletonList(perfumes.get(0).getPerfumer()), pageable)).thenReturn(page);
        when(perfumeRepository.findByPerfumerInAndPerfumeGenderIn(
                Collections.singletonList(perfumes.get(0).getPerfumer()),
                Collections.singletonList(perfumes.get(0).getPerfumeGender()),
                pageable)).thenReturn(page);

        assertNotNull(perfumes);
        assertEquals(1000, perfumeService.findByPriceBetween(900, 1100, pageable)
                .getContent().get(0).getPrice());
        assertEquals("Dior", perfumeService.findByPerfumerIn(Collections.singletonList(perfumes.get(0).getPerfumer()), pageable)
                .getContent().get(0).getPerfumer());
        assertEquals(1, perfumeService.findByPerfumerInAndPerfumeGenderIn(
                Collections.singletonList(perfumes.get(0).getPerfumer()),
                Collections.singletonList(perfumes.get(0).getPerfumeGender()),
                pageable).getSize());
    }
}