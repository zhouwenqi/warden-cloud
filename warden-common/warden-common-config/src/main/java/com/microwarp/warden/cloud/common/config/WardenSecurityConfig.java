package com.microwarp.warden.cloud.common.config;

import com.microwarp.warden.cloud.common.core.util.AesUtil;
import com.microwarp.warden.cloud.common.core.util.DesUitl;
import com.microwarp.warden.cloud.common.core.util.RsaUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * configuration - 安全配置
 * @author zhouwenqi
 */
@ConfigurationProperties(prefix = "warden.cloud.security")
@Configuration
public class WardenSecurityConfig {
    /** Rsa公钥 */
    private String rsaPublicKey;
    /** Rsa私钥 */
    private String rsaPrivateKey;
    /** Aes密钥 */
    private String aesKey;
    /** Des密钥 */
    private String desKey;
    /** Des张量 */
    private String desIv;

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
        RsaUtil.DEFAULT_PUBLIC_KEY = rsaPublicKey;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
        RsaUtil.DEFAULT_PRIVATE_KEY = rsaPrivateKey;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
        AesUtil.DEFAULT_KEY = aesKey;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
        DesUitl.DEFAULT_KEY = desKey;
    }

    public String getDesIv() {
        return desIv;
    }

    public void setDesIv(String desIv) {
        this.desIv = desIv;
        DesUitl.DEFAULT_IV = desIv;
    }
}
