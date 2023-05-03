package com.metacoding.myblog.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

@Data
@Generated("jsonschema2pojo")
public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Data
    @Generated("jsonschema2pojo")
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
        @Data
        @Generated("jsonschema2pojo")
        public class Profile {
            public String nickname;
            private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
            public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
            }
            public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
            }
        }
    }
    @Data
    @Generated("jsonschema2pojo")
    public class Properties {

        public String nickname;
        private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }
}

