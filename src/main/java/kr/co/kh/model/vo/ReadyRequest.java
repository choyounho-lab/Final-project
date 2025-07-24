package kr.co.kh.model.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReadyRequest {
    private String email;
    private String itemName;
    private int totalAmount;
}
