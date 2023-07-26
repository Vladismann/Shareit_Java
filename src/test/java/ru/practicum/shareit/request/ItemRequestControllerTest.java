package ru.practicum.shareit.request;

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
import ru.practicum.shareit.request.dto.AddItemRequestDto;
import ru.practicum.shareit.request.dto.CreatedItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.common.CommonForControllers.BY_ID_PATH;
import static ru.practicum.shareit.common.CommonForControllers.USER_HEADER;
import static ru.practicum.shareit.request.ItemRequestPaths.GET_ALL_ITEM_REQUEST_PATH;
import static ru.practicum.shareit.request.ItemRequestPaths.ITEM_REQUEST_PATH;

@ExtendWith(MockitoExtension.class)
public class ItemRequestControllerTest {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final LocalDateTime created = LocalDateTime.now().withNano(0);
    private final CreatedItemRequestDto createdItemRequestDto = new CreatedItemRequestDto((long) 1, "TestD", created);
    private final GetItemRequestDto getItemRequestDto = new GetItemRequestDto(1, "TestD", created, List.of());

    private MockMvc mvc;

    @InjectMocks
    private ItemRequestController itemRequestController;

    @Mock
    private ItemRequestService itemRequestService;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders
                .standaloneSetup(itemRequestController)
                .build();
    }

    @Test
    public void createRequest() throws Exception {
        when(itemRequestService.createRequest(anyLong(), any())).thenReturn(createdItemRequestDto);

        MvcResult result = mvc.perform(post(ITEM_REQUEST_PATH).content(mapper.writeValueAsString(new AddItemRequestDto("Test")))
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        CreatedItemRequestDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), CreatedItemRequestDto.class);

        assertEquals(createdItemRequestDto, actualDto);
    }

    @Test
    public void getRequestById() throws Exception {
        when(itemRequestService.geById(anyLong(), anyLong())).thenReturn(getItemRequestDto);

        MvcResult result = mvc.perform(get(ITEM_REQUEST_PATH + BY_ID_PATH, 1)
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        GetItemRequestDto actualDto = mapper.readValue(result.getResponse().getContentAsString(), GetItemRequestDto.class);

        assertEquals(getItemRequestDto, actualDto);
    }

    @Test
    public void getOwnRequests() throws Exception {
        when(itemRequestService.getUserRequests(anyLong())).thenReturn(List.of(getItemRequestDto));

        MvcResult result = mvc.perform(get(ITEM_REQUEST_PATH)
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        List<GetItemRequestDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(getItemRequestDto), actualDto);
    }

    @Test
    public void getAllRequests() throws Exception {
        when(itemRequestService.getAllRequests(anyLong(), any())).thenReturn(List.of(getItemRequestDto));

        MvcResult result = mvc.perform(get(ITEM_REQUEST_PATH + GET_ALL_ITEM_REQUEST_PATH)
                .header(USER_HEADER, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        List<GetItemRequestDto> actualDto = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(List.of(getItemRequestDto), actualDto);
    }
}
