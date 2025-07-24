// controller/PaymentController.java
package kr.co.kh.controller.pay;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.User;
import kr.co.kh.model.payload.response.ReadyResponse;
import kr.co.kh.model.vo.ApproveRequest;
import kr.co.kh.model.vo.ReadyRequest;
import kr.co.kh.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/pay")
public class PaymentController {

    @Value("${kakao.admin-key}")
    private String adminKey = "DEV0570670DBC65B5887D71F1BAD3FC051172A60";

    private final UserRepository userRepository;

    public PaymentController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approvePayment(@RequestBody ApproveRequest request, @CurrentUser CustomUserDetails user) {
        String pgToken = request.getPgToken();

        String tid = null;
        String email = null;

        if (tid == null || email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 또는 결제 정보가 없습니다.");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY " + adminKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String params =
                "cid=TC0ONETIME&tid=" + tid +
                        "&partner_order_id=1234" +
                        "&partner_user_id=" + email +
                        "&pg_token=" + pgToken;

        HttpEntity<String> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kapi.kakao.com/v1/payment/approve",
                entity,
                String.class
        );

//        if (response.getStatusCode().is2xxSuccessful()) {
//            // 결제 성공 → 유저 paid = true 저장
//            Optional<User> userOptional = userRepository.findByEmail(email);
//            if (userOptional.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 없음");
//            }
//
//           user = userOptional.get(); // 여기서 Optional에서 실제 User 꺼냄
//            user.setPaid(true);
//            userRepository.save(user);
//
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 승인 실패");
//        }
            return ResponseEntity.ok().build();

    }
    // PaymentController.java 안에 ↓ 이 메서드 추가

    @PostMapping("/ready")
    public ResponseEntity<?> readyToPay(@RequestBody ReadyRequest request , @CurrentUser CustomUserDetails user) {
        // Request header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "DEV_SECRET_KEY " + "DEV0570670DBC65B5887D71F1BAD3FC051172A60");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request param
        HashMap<String, String> params = new HashMap<>();
        params.put("cid", "TC0ONETIME");
        params.put("partner_order_id", "1234");
        params.put("partner_user_id", user.getEmail());
        params.put("item_name", "Echo 프리미엄 구독권");
        params.put("quantity", "1");
        params.put("total_amount", String.valueOf(request.getTotalAmount()));
        params.put("vat_amount", "100");
        params.put("tax_free_amount", "0");
        params.put("approval_url", "http://localhost:8001/success");
        params.put("cancel_url", "http://localhost:8001/cancel");
        params.put("fail_url", "http://localhost:8001/fail");
        // Send reqeust
        HttpEntity<HashMap<String, String>> entityMap = new HttpEntity<>(params, headers);
        ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                entityMap,
                ReadyResponse.class
        );
        ReadyResponse readyResponse = response.getBody();

        // 주문번호와 TID를 매핑해서 저장해놓는다.
        // Mapping TID with partner_order_id then save it to use for approval request.
        System.out.println(readyResponse);
        return ResponseEntity.ok(readyResponse);
    }

}
