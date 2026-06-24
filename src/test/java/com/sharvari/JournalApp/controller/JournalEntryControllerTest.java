package com.sharvari.JournalApp.controller;

import com.sharvari.JournalApp.model.JournalEntry;
import com.sharvari.JournalApp.model.Users;
import com.sharvari.JournalApp.service.JournalEntryService;
import com.sharvari.JournalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class JournalEntryControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private UserService userService;
//
//    @MockitoBean
//    private JournalEntryService journalEntryService;
//
//    @Test
//    @WithMockUser(username = "testUser", roles = {"USER"})
//    void getAllEntries_ShouldReturn204_ForAuthenticatedUser() throws Exception {
//        Users mockUser = new Users();
//        mockUser.setJournalEntryList(new ArrayList<>());
//        when(userService.findByUsername("testUser")).thenReturn(mockUser);
//
//        mockMvc.perform(get("/journal"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void getAllEntries_ShouldReturn401_WhenNotAuthenticated() throws Exception {
//        mockMvc.perform(get("/journal"))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    @WithMockUser(username = "testUser", roles = {"USER"})
//    void adminEndpoint_ShouldReturn403_ForNonAdmin() throws Exception {
//        mockMvc.perform(get("/admin/all-users"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
//    void adminEndpoint_ShouldReturn200_ForAdmin() throws Exception {
//        Users mockUser = new Users();
//        mockUser.setUsername("adminUser");
//
//        when(userService.getAll()).thenReturn(Arrays.asList(mockUser));
//
//        mockMvc.perform(get("/admin/all-users"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void saveEntry_ShouldSaveAndAddToUser() {
//        String username = "testUser";
//
//        JournalEntry entry = new JournalEntry();
//        entry.setTitle("My Day");
//
//        Users mockUser = new Users();
//        mockUser.setUsername(username);
//        mockUser.setJournalEntryList(new ArrayList<>());
//
//        when(userService.findByUsername(username)).thenReturn(mockUser);
//        doNothing().when(journalEntryService).saveEntry(entry, username);
//
//        journalEntryService.saveEntry(entry, username);
//
//        verify(journalEntryService, times(1)).saveEntry(entry, username);
//    }

}
