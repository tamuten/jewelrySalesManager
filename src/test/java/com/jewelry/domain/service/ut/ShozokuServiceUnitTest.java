package com.jewelry.domain.service.ut;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jewelry.domain.model.Shozoku;
import com.jewelry.domain.repository.ShozokuRepository;
import com.jewelry.domain.service.ShozokuService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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
        when(repository.count()).thenReturn(-1L); 
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
    void findPageは件数が1件のときrepository_findPageで取得したリストを含むページを返す(){
        Pageable pageable =  PageRequest.of(0, 10);
        List<Shozoku> returnList = new ArrayList<>();
        returnList.add(new Shozoku(1, "所属1"));

        when(repository.count()).thenReturn(1L); 
        when(repository.findPage(pageable)).thenReturn(returnList);
        

        Page<Shozoku> actual = service.findPage(pageable);

        assertThat(actual.getContent().size()).isEqualTo(1);
        assertThat(actual.getContent().get(0)).isEqualTo(new Shozoku(1, "所属1"));
        assertThat(actual.getPageable()).isEqualTo(pageable);
        assertThat(actual.getTotalPages()).isEqualTo(1);
        assertThat(actual.getSize()).isEqualTo(10);
        assertThat(actual.getNumber()).isEqualTo(0);

        verify(repository, times(1)).count();
        verify(repository, times(1)).findPage(pageable);
    }

    @Test
    void findPageは件数が10件のときrepository_findPageで取得したリストを含むページを返す(){
        Pageable pageable =  PageRequest.of(0, 10);
        List<Shozoku> returnList = new ArrayList<>();
        returnList.add(new Shozoku(1 , "所属1" ));
        returnList.add(new Shozoku(2 , "所属2" ));
        returnList.add(new Shozoku(3 , "所属3" ));
        returnList.add(new Shozoku(4 , "所属4" ));
        returnList.add(new Shozoku(5 , "所属5" ));
        returnList.add(new Shozoku(6 , "所属6" ));
        returnList.add(new Shozoku(7 , "所属7" ));
        returnList.add(new Shozoku(8 , "所属8" ));
        returnList.add(new Shozoku(9 , "所属9" ));
        returnList.add(new Shozoku(10, "所属10"));

        when(repository.count()).thenReturn(10L); 
        when(repository.findPage(pageable)).thenReturn(returnList);
        

        Page<Shozoku> actual = service.findPage(pageable);

        assertThat(actual.getContent().size()).isEqualTo(10);
        assertThat(actual.getContent().get(0)).isEqualTo(new Shozoku( 1, "所属1" ));
        assertThat(actual.getContent().get(1)).isEqualTo(new Shozoku( 2, "所属2" ));
        assertThat(actual.getContent().get(2)).isEqualTo(new Shozoku( 3, "所属3" ));
        assertThat(actual.getContent().get(3)).isEqualTo(new Shozoku( 4, "所属4" ));
        assertThat(actual.getContent().get(4)).isEqualTo(new Shozoku( 5, "所属5" ));
        assertThat(actual.getContent().get(5)).isEqualTo(new Shozoku( 6, "所属6" ));
        assertThat(actual.getContent().get(6)).isEqualTo(new Shozoku( 7, "所属7" ));
        assertThat(actual.getContent().get(7)).isEqualTo(new Shozoku( 8, "所属8" ));
        assertThat(actual.getContent().get(8)).isEqualTo(new Shozoku( 9, "所属9" ));
        assertThat(actual.getContent().get(9)).isEqualTo(new Shozoku(10, "所属10"));
        assertThat(actual.getPageable()).isEqualTo(pageable);
        assertThat(actual.getTotalPages()).isEqualTo(1);
        assertThat(actual.getSize()).isEqualTo(10);
        assertThat(actual.getNumber()).isEqualTo(0);

        verify(repository, times(1)).count();
        verify(repository, times(1)).findPage(pageable);
    }
}
