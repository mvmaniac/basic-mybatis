package io.devfactory.global.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
public class CommonApi {

  private final CommonService commonService;

  @GetMapping("/ex")
  public ResponseEntity<Object> insertRuntimeException() {
    commonService.insertRuntimeException();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/ex/board")
  public ResponseEntity<Object> insertMemberBeforeBoardRuntimeException() {
    commonService.insertMemberBeforeBoardRuntimeException();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/ex/member")
  public ResponseEntity<Object> insertBoardBeforeMemberRuntimeException() {
    commonService.insertBoardBeforeMemberRuntimeException();
    return ResponseEntity.ok().build();
  }

}
