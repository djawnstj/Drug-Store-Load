package com.example.project.direction.controller

import com.example.project.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework. test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc
    private DirectionService directionService = Mock()

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }

    def "GET /dir/{encodedId}"() {
        given:
        String encodedId = "r"

        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11, 128.11"

        when:
        directionService.findDirectionUrlById(encodedId) >> redirectURL

        def result = mockMvc.perform(get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect { status().is3xxRedirection() }
            .andExpect { redirectedUrl(redirectURL) }
            .andDo { print() }
    }
}
