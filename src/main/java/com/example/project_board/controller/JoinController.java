package com.example.project_board.controller;

import com.example.project_board.dto.JoinDto;
import com.example.project_board.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

  private final JoinService joinService;

  @PostMapping
  public String joinProcess(
      @RequestBody JoinDto joinDto) {

    joinService.joinProcess(joinDto);

    return "ok";
  }
}
