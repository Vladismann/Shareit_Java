package ru.practicum.shareit.item;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.common.CommonForControllers.*;
import static ru.practicum.shareit.item.ItemPaths.COMMENT_PATH;
import static ru.practicum.shareit.item.ItemPaths.ITEMS_PATH;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final ItemDto itemDto
            = new ItemDto(1, "Test", "TestD", true, null, null, null, null);
    private final CommentDto commentDto = new CommentDto((long) 1, "Test", "Test", LocalDateTime.now());
    private MockMvc mvc;

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();
    }

    @Test
    public void createItem() throws Exception {
        when(itemService.createItem(anyLong(), any())).thenReturn(itemDto);

        MvcResult result = mvc.perform(post(ITEMS_PATH).content(mapper.writeValueAsString(itemDto))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        ItemDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), ItemDto.class);

        assertEquals(itemDto, actualDto);
    }

    @Test
    public void updateItem() throws Exception {
        when(itemService.updateItem(anyLong(), anyLong(), any())).thenReturn(itemDto);

        MvcResult result = mvc.perform(patch(ITEMS_PATH + BY_ID_PATH, 1)
                .content(mapper.writeValueAsString(itemDto))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        ItemDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), ItemDto.class);

        assertEquals(itemDto, actualDto);
    }

    @Test
    public void getItem() throws Exception {
        when(itemService.getItem(anyLong(), anyLong())).thenReturn(itemDto);

        MvcResult result = mvc.perform(get(ITEMS_PATH + BY_ID_PATH, 1)
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        ItemDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), ItemDto.class);

        assertEquals(itemDto, actualDto);
    }

    @Test
    public void getUserItems() throws Exception {
        when(itemService.getAllByUserId(anyLong(), any())).thenReturn(List.of(itemDto));

        MvcResult result = mvc.perform(get(ITEMS_PATH)
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        List<ItemDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(itemDto), actualDto);
    }

    @Test
    public void searchItems() throws Exception {
        when(itemService.searchItemByText(anyString(), any())).thenReturn(List.of(itemDto));

        MvcResult result = mvc.perform(get(ITEMS_PATH + SEARCH_PATH)
                .header(USER_HEADER, 1)
                .param("text", "text")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        List<ItemDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(itemDto), actualDto);
    }

    @Test
    public void createComment() throws Exception {
        when(itemService.addComment(anyLong(), anyLong(), any())).thenReturn(commentDto);

        MvcResult result = mvc.perform(post(ITEMS_PATH + BY_ID_PATH + COMMENT_PATH, 1)
                .content(mapper.writeValueAsString(new CreateCommentDto("Test")))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        CommentDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), CommentDto.class);

        assertEquals(commentDto, actualDto);
    }
}
