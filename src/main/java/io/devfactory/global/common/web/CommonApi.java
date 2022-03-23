package io.devfactory.global.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
public class CommonApi {

  private final CommonService commonService;

}
