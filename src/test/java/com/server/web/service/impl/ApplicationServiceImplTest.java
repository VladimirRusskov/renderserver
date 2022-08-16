package com.server.web.service.impl;

import com.server.web.converter.ApplicationToApplicationDtoConverter;
import com.server.web.entity.Application;
import com.server.web.repository.ApplicationRepository;
import com.server.web.service.dto.ApplicationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

    @InjectMocks
    ApplicationServiceImpl subj;

    @Mock
    ApplicationRepository repository;

    @Mock
    ApplicationToApplicationDtoConverter converter;

    @Test
    public void getListApplication_ok() {
        Application application = new Application().setId(1L).setName("name");
        ApplicationDTO applicationDTO = new ApplicationDTO(1L, "name");
        when(repository.findAll()).thenReturn(asList(application));
        when(converter.convert(application)).thenReturn(applicationDTO);
        assertEquals(subj.getListApplication(), asList(applicationDTO));
        verify(converter, times(1)).convert(application);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getListApplication_isEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(subj.getListApplication().isEmpty());
        verify(repository, times(1)).findAll();
        verifyNoInteractions(converter);
    }

    @Test
    public void count() {
        when(repository.count()).thenReturn(1L);
        assertEquals(subj.count(), 1L);
        verify(repository, times(1)).count();
    }
}