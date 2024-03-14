package com.sparta.outsourcing.member.controller;

import static com.sparta.outsourcing.member.test.MemberInfo.TEST_EMAIL;
import static com.sparta.outsourcing.member.test.MemberInfo.TEST_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing.domain.member.controller.dto.SignupRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import com.sparta.outsourcing.domain.member.service.MemberService;
import com.sparta.outsourcing.domain.member.service.dto.MemberInfoResponse;
import com.sparta.outsourcing.global.security.UserDetailsImpl;
import com.sparta.outsourcing.member.test.MemberTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest implements MemberTest {

  @Autowired
  WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  MemberService memberService;

  @Autowired
  MockMvc mockMvc;

  private static final String BASE_URL = "/api/members";

  private static UserDetailsImpl testUserDetails;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();

    testUserDetails = new UserDetailsImpl(TEST_MEMBER);

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
        testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
  }

  @Test
  @DisplayName("회원가입 테스트")
  void 회원가입_성공_테스트() throws Exception {
    // given
    SignupRequestDto dto = new SignupRequestDto();
    ReflectionTestUtils.setField(dto, "email", "test123@naver.com");
    ReflectionTestUtils.setField(dto, "nickname", "테스트닉네임");
    ReflectionTestUtils.setField(dto, "password", "Sk@123456");
    ReflectionTestUtils.setField(dto, "address", "테스트주소");
    ReflectionTestUtils.setField(dto, "number", "010-1234-1234");

    String body = objectMapper.writeValueAsString(dto);

    // when, then
    mockMvc.perform(post(BASE_URL + "/signup")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("회원 가입에 성공하였습니다."))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 조회 테스트")
  void 회원조회_성공_테스트() throws Exception {
    // given
    MemberInfoResponse response = MemberInfoResponse.builder()
        .memberId(1L)
        .favoriteList(null)
        .reviewList(null)
        .build();

    given(memberService.memberInfo(anyLong())).willReturn(response);

    // when, then
    mockMvc.perform(get(BASE_URL + "/{memberId}", TEST_ID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("조회에 성공하였습니다."))
        .andDo(print());
  }

  @Test
  @DisplayName("회원 정보 수정 테스트")
  void 회원_정보수정_성공_테스트() throws Exception {
    // given
    UpdateRequestDto dto = UpdateRequestDto.builder()
        .nickname("이름변경테스트")
        .password(TEST_PASSWORD)
        .address("주소변경테스트")
        .number("010-0000-0000")
        .build();

    String body = objectMapper.writeValueAsString(dto);

    // when, then
    mockMvc.perform(put(BASE_URL + "/{memberId}", TEST_ID)
            .content(body)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("회원 정보를 성공적으로 변경하였습니다."))
        .andDo(print());

    verify(memberService, atLeastOnce()).updateMember(eq(TEST_ID), eq(TEST_EMAIL),
        any(UpdateRequestDto.class));
  }

  @Test
  @DisplayName("회원 정보 비밀번호 수정 테스트")
  void 회원_정보_비밀번호_수정_성공_테스트() throws Exception {
    // given
    UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto("Change@123", TEST_PASSWORD,
        TEST_PASSWORD);

    String body = objectMapper.writeValueAsString(dto);

    // when, then
    mockMvc.perform(patch(BASE_URL + "/{memberId}", TEST_ID)
            .content(body)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("회원의 비밀번호를 변경하였습니다."))
        .andDo(print());

    verify(memberService, atLeastOnce()).updatePasswordMember(eq(TEST_ID), eq(TEST_EMAIL),
        any(UpdatePasswordRequestDto.class));
  }

  @Test
  @DisplayName("회원 정보 삭제 테스트")
  void 회원_정보_삭제_성공_테스트() throws Exception {
    // when, then
    mockMvc.perform(delete(BASE_URL + "/{memberId}", TEST_ID)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("회원을 성공적으로 삭제하였습니다."))
        .andDo(print());

    verify(memberService, atLeastOnce()).deleteMember(TEST_ID, TEST_EMAIL);
  }

  @Test
  @DisplayName("회원 로그아웃 테스트")
  void 회원_로그아웃_성공_테스트() throws Exception {
    // when, then
    mockMvc.perform(post(BASE_URL + "/logout")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("200 OK"))
        .andExpect(jsonPath("$.message").value("로그아웃에 성공하였습니다."))
        .andDo(print());

    verify(memberService, atLeastOnce()).logout(testUserDetails);
  }
}
