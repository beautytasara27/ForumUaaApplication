package com.binus.project.forumuaa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    private String clientId = "uaa";
    private String clientSecret = "password@123";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAtfhGkFdeOKQZHcqucRqvyF1m3ffwB6LWDI8F5dj5D2cQdS2A\n" +
            "TpD4IuZdCoZnYILco7uGtQWbdBPrcYCpHKYm1+Nu1pAtha+QAw+nVTJarKCf5dK3\n" +
            "ambyNI7vCjwGnXYY2CY0oXIASXGEJLOaqeSDje/BiEXzoPCpx9kKl9y022f8Oxv7\n" +
            "HrYc8xFWS05e55yjbpvfSetBRc5nl1jCSwnJdrRREtnHzJ1hvhU0/uE7YwRju58x\n" +
            "RZDMRZSf+d6wBv76K14fsr73bIXKhwqllLFR6F973Fu29xQChuNxtzCRWdsEX4uW\n" +
            "VvrOeXx5bJA6S3hZcfGOExwYahLw+aBvOeJk8QIDAQABAoIBABAX2QXC9E5GFQKR\n" +
            "fkP6fDlYKVTQLKZAPHX34Cmnur8l+kqir4pDP+62ONxJbrMWX7ULTKqNyGXHrTbo\n" +
            "jgw+lYjaUF8heSUtlarvo3jhbyt9OSb2L8p1OE51paZeEKe0XhYfqt/sWSIlq+4j\n" +
            "BDaqQwg2gAMxTI6bOii2hT1RA52zumm0q8g3YUgbjKTlXEPMVDTmIceZZIbCMstP\n" +
            "CPHUoFQogeQ+prTCIZqCqZ8cGt7ARCgs3yhS5i5MwBpuraltDkjCmWv6+Z/EAPTw\n" +
            "H6N0D8Kg1y2IIpZQPaGCNgV2L5oway5o19JPuM1EKCxxHfgW1Zw0IAlhxaW0Hq0h\n" +
            "ejGcYFECgYEA1/mBq6UnRgqG2kWchMGBySlDZYe7VliwnUHf8yvss/y5VKE3FVjj\n" +
            "hzJzKE95d9mriEMpI8k5maowtj45jLnBLu9uV6KBGBWRjEpQ+JmYuLlcYIe/Wamc\n" +
            "zdjWvMwMk/7WaOn1O3Px8PVdD7JapiqVng+JYjZLl6oRST9Iw4xRYDsCgYEA17F6\n" +
            "RE4iHnvZmdxDu7W22uxgIYWf7FU7wZw8upbUMvJCGFM5RPDGHdRpJ6NQtXQB+23Q\n" +
            "tzdY2pIS4/BY/MhDSJeXWSzYZBO/CE9nrHgxuINCRq/7O6BgmBHgjx/08o/Xq/EU\n" +
            "wTfbrRBb8syz+Gz/GfJ+850h0M5qZ+S8wfqoyMMCgYEAvbx13byGpzLCS+mR2tNB\n" +
            "WN9GVWEMBIyABHeF7lTtayYgDyhegwOHSBPbNVO0q8TvvBL8hnLWhpgMmV5LIsVh\n" +
            "4QD5mEWsUeNI7c2wGuadU9OMQ0Wepn+qwPC5nj1I44+i/6JiDewLZhu4uPyARLDu\n" +
            "pmCR2lVGzaPb8dSF8T1JlFcCgYB1MfiGDpIZUB/uqrCe2GAvWYnuDOXVCoa6bwLM\n" +
            "O1G3EMx63RVb8RskcBpmsCUVQqAlFgj20/rSX9YlxZ0EVGfClKNmj6hP0C7W/BYP\n" +
            "rwsM/zzOnaX7QuvZFLWX7rwv45pC0W3+gjQ5N49OQI106Pw/A/SYt4L9KnrEUpOB\n" +
            "Lonw8wKBgF5/1N+9OsAyh9nE16j6Vk21FhKpfiq2bGSTL+wSE/eM8QlrqNdHtkUo\n" +
            "OofBqaq1rpO1QZUgSPp/zcXo+B4pupyvRk0BS1tr00EOygX2NHcIquIr2etLodCb\n" +
            "yhTuuznLPbeTwYWI0tSOOGTg4zRxDRtXpd2eYqdCf90oHa0k4fjO\n" +
            "-----END RSA PRIVATE KEY-----\n";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtfhGkFdeOKQZHcqucRqv\n" +
            "yF1m3ffwB6LWDI8F5dj5D2cQdS2ATpD4IuZdCoZnYILco7uGtQWbdBPrcYCpHKYm\n" +
            "1+Nu1pAtha+QAw+nVTJarKCf5dK3ambyNI7vCjwGnXYY2CY0oXIASXGEJLOaqeSD\n" +
            "je/BiEXzoPCpx9kKl9y022f8Oxv7HrYc8xFWS05e55yjbpvfSetBRc5nl1jCSwnJ\n" +
            "drRREtnHzJ1hvhU0/uE7YwRju58xRZDMRZSf+d6wBv76K14fsr73bIXKhwqllLFR\n" +
            "6F973Fu29xQChuNxtzCRWdsEX4uWVvrOeXx5bJA6S3hZcfGOExwYahLw+aBvOeJk\n" +
            "8QIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(600000)
                .refreshTokenValiditySeconds(600000);

    }
}