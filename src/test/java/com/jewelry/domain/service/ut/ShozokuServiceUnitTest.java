package com.jewelry.domain.service.ut;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.repository.ShozokuRepository;
import com.jewelry.domain.service.ShozokuService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class ShozokuServiceUnitTest {
    @Autowired
    private ShozokuService service;
    @MockBean
    private ShozokuRepository repository;
    


    @Test
    void findPageは件数が0件のとき空のリストを含むページを返す(){
        when(repository.count()).thenReturn(0L); 
        Pageable pageable =  PageRequest.of(0, 10);

        Page<Shozoku> actual = service.findPage(pageable);
        
        assertThat(actual.getContent()).isEqualTo(Collections.emptyList());
        assertThat(actual.getPageable()).isEqualTo(pageable);
        assertThat(actual.getTotalPages()).isEqualTo(0);
        assertThat(actual.getSize()).isEqualTo(10);
        assertThat(actual.getNumber()).isEqualTo(0);
        
        verify(repository, times(1)).count();
        verify(repository, times(0)).findPage(pageable);
    }

        
    @Test
    void findPageは件数がマイナス1件のとき空のリストを含むページを返すANDrepository_findPageメソッドは呼ばれない(){
        when(repository.count()).thenReturn(0L); 
        Pageable pageable =  PageRequest.of(0, 10);

        Page<Shozoku> expected = new PageImpl<>(Collections.emptyList(), pageable, 0L);

        Page<Shozoku> actual = service.findPage(pageable);


        verify(repository, times(1)).count();
        verify(repository, times(0)).findPage(pageable);
    }

    @Test
    void findPageは件数が1件のときrepository_findPageで取得したリストを含むページを返す(){

    }

    @Test
    void findPageは件数が10件のときrepository_findPageで取得したリストを含むページを返す(){

    }
}
