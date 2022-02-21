package com.example.tmsaapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.example.tmsaapi.model.AppUser;
import com.example.tmsaapi.repository.UserRepository;
import com.example.tmsaapi.utils.RoleUser;

import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FileService fileService;


    @Test
    public void getAllUsersTest() throws ParseException {

        AppUser user1 = new AppUser("Clémence","Muller",new SimpleDateFormat("dd-MM-yyyy").parse("10-07-1984"),"Nanterre","FR","https://s3.amazonaws.com/uifaces/faces/twitter/kushsolitary/128.jpg","Jacquet SEM","Coordinator","+33 191835849","eva.leclerc","manon.aubert@gmail.com","mr3j8v",RoleUser.ADMIN);
        List<AppUser> data = Arrays.asList(user1);

        BDDMockito.given(userRepository.findAll()).willReturn(data);

        Assertions.assertThat(userRepository.findAll())
                                    .hasSize(1)
                                    .contains(user1);
    }

    
}
