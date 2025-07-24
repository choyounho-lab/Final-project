package kr.co.kh.model.payload.response;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReadyResponse {
    private String tid;
    private Boolean tms_result;
    private String created_at;
    private String next_redirect_pc_url;
    private String next_redirect_mobile_url;
    private String next_redirect_app_url;
    private String android_app_scheme;
    private String ios_app_scheme;

    @Builder
    public ReadyResponse(String tid, Boolean tms_result, String created_at, String next_redirect_pc_url, String next_redirect_mobile_url, String next_redirect_app_url, String android_app_scheme, String ios_app_scheme) {
        this.tid = tid;
        this.tms_result = tms_result;
        this.created_at = created_at;
        this.next_redirect_pc_url = next_redirect_pc_url;
        this.next_redirect_mobile_url = next_redirect_mobile_url;
        this.next_redirect_app_url = next_redirect_app_url;
        this.android_app_scheme = android_app_scheme;
        this.ios_app_scheme = ios_app_scheme;
    }
}
