package ru.practicum.shareit.user;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.common.CommonForControllers;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.user.UserMessages.EMPTY_EMAIL;
import static ru.practicum.shareit.user.UserPaths.USERS_PATH;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private  UserDto userDto;
    private MockMvc mvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ValidationException(""))
                .build();
        userDto = new UserDto(1, "Test", "Test@mail.ru");
    }

    @Test
    public void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(userDto);

        MvcResult result = mvc.perform(post(USERS_PATH)
                .content(mapper.writeValueAsString(userDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        UserDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userDto, actualDto);
    }

    @Test
    public void createUserWithoutEmail() throws Exception {
        userDto.setEmail("");

        MvcResult result = mvc.perform(post(USERS_PATH)
                .content(mapper.writeValueAsString(userDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
        assertTrue(result.getResolvedException().getMessage().contains(EMPTY_EMAIL));
    }

    @Test
    public void updateUser() throws Exception {
        when(userService.updateUser(anyLong(), any())).thenReturn(userDto);

        MvcResult result = mvc.perform(patch(USERS_PATH + CommonForControllers.BY_ID_PATH, 1)
                .content(mapper.writeValueAsString(userDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        UserDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userDto, actualDto);
    }

    @Test
    public void getUser() throws Exception {
        when(userService.getUserDtoById(anyLong())).thenReturn(userDto);

        MvcResult result = mvc.perform(get(USERS_PATH + BY_ID_PATH, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        UserDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals(userDto, actualDto);
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(delete(USERS_PATH + BY_ID_PATH, 1)).andExpect(status().isOk());
    }

    @Test
    public void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userDto));

        MvcResult result = mvc.perform(get(USERS_PATH)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<UserDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(userDto), actualDto);
    }
}
