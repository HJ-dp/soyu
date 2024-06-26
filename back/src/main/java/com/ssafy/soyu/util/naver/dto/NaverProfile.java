package com.ssafy.soyu.util.naver.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverProfile {
    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String name;
        private String mobile;
    }

    public String getEmail() {
        return response.email;
    }

    public String getName(){
        return response.name;
    }

    public String getMobile(){
        return response.mobile;
    }

}
