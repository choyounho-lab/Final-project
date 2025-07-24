package kr.co.kh.controller.cmmon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kakaopay")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?> dfssdfdsf() {

        //


        return ResponseEntity.ok().build();
    }

}
