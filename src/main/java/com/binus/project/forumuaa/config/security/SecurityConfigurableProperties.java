package com.binus.project.forumuaa.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "security")
@Validated
class SecurityConfigurableProperties {
    private List<String> unsecuredUris =new ArrayList<>();
    private UnsecuredEndpoints unsecuredEndpoints;
    private Cors cors;


    public List<String> getUnsecuredUris() {
        return unsecuredUris;
    }

    public void setUnsecuredUris(List<String> unsecuredUris) {
        this.unsecuredUris = unsecuredUris;
    }

    public UnsecuredEndpoints getUnsecuredEndpoints() {
        return unsecuredEndpoints;
    }

    public void setUnsecuredEndpoints(UnsecuredEndpoints unsecuredEndpoints) {
        this.unsecuredEndpoints = unsecuredEndpoints;
    }

    public Cors getCors() {
        return cors;
    }

    public void setCors(Cors cors) {
        this.cors = cors;
    }

    public static class Cors{

        private List<String> allowedOrigins;

        private List<String> allowedMethods;


        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public List<String> getAllowedMethods() {
            return allowedMethods;
        }

        public void setAllowedMethods(List<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
        }
    }
    
    public static class UnsecuredEndpoints {
        private List<String> get=new ArrayList<>();
        private List<String> post=new ArrayList<>();
        private List<String> put=new ArrayList<>();
        private List<String> delete=new ArrayList<>();
        private List<String> patch=new ArrayList<>();

        public List<String> getGet() {
            return get;
        }

        public void setGet(List<String> get) {
            this.get = get;
        }

        public List<String> getPost() {
            return post;
        }

        public void setPost(List<String> post) {
            this.post = post;
        }

        public List<String> getPut() {
            return put;
        }

        public void setPut(List<String> put) {
            this.put = put;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
        }

        public List<String> getPatch() {
            return patch;
        }

        public void setPatch(List<String> patch) {
            this.patch = patch;
        }
    }

}
