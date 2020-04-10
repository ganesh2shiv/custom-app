package com.network.app.http;

import java.io.InputStream;

import okio.Buffer;

class CertManager {

    static InputStream getSelfSignedCert() {
        String certAuthority = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIDeTCCAmGgAwIBAgIEBz9ZqTANBgkqhkiG9w0BAQsFADBtMQswCQYDVQQGEwI5\n" +
                "MTEPMA0GA1UECBMGcHVuamFiMQ8wDQYDVQQHEwZtb2hhbGkxDzANBgNVBAoTBmFn\n" +
                "bmV4dDEPMA0GA1UECxMGYWduZXh0MRowGAYDVQQDExF2aXNoYWwgYW5kIGJhbnNh\n" +
                "bDAeFw0yMDAxMzAwOTI1MzlaFw0zMDAxMjcwOTI1MzlaMG0xCzAJBgNVBAYTAjkx\n" +
                "MQ8wDQYDVQQIEwZwdW5qYWIxDzANBgNVBAcTBm1vaGFsaTEPMA0GA1UEChMGYWdu\n" +
                "ZXh0MQ8wDQYDVQQLEwZhZ25leHQxGjAYBgNVBAMTEXZpc2hhbCBhbmQgYmFuc2Fs\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnsaJHeVtF3b1AFBQinnc\n" +
                "559NDcjvWClxQKDCVOm5ZIEeRDtbPPJ+HKIjPxZxZXQnEk7GHUJROPQBtlg0m78u\n" +
                "ERL84UuH09e5UyYpd+64WeOpKYPuhiEAeV8PYBJu4erbIQDblUZMGSFFv/vFl19f\n" +
                "i6bIynn5hqd5HHgprUumuM0Pq2XEhqX2iA39CPgCtAccN0azKDekj3Z0tUwCwsBO\n" +
                "CTl2OFHVSeUBMggA6bFWIeqDDIvBW70RYcWTGM9HSyW0GNgK47HIg3wcCKnak5Ou\n" +
                "W8DqBqvGVdhFNQo+8IXpI895jBn8OiMZMaqQUtuq46Rsf1cBLR73I0iUDr6bIGnc\n" +
                "5QIDAQABoyEwHzAdBgNVHQ4EFgQUa9b1hAtYGOQRsWmtAkYTPBIwwb0wDQYJKoZI\n" +
                "hvcNAQELBQADggEBAH8T7xjG9pBSNH6I38W1N9dC9f2mVKeAKsOu5boKUxUwTiSe\n" +
                "HZwuWC3Ti7qZ8QeRK/UBlWxKSfEsF0tpG+9fTnquroJCXXnLDJtnAPmJOsPULedS\n" +
                "RdpbKXtea8YIKPNVgXArzFiv0PjUoUEu4K6QNYYG7hwlO5UCoVNIxNJfSAYPLYX/\n" +
                "Wr15WDLdnlfI7EI3GBTG7T9wDZ+6ldEGoJYm8mU0XPj7eJBpZI9KqLpxwvRCsFBN\n" +
                "HaS6fiKmkbek3I6uMJjaIYY23WaGqKNzFZV/pyRFtMikciNcGlRMoREeFmolmS/O\n" +
                "flRchqzWBd8RznQbUSM/oxqVKERVioO5tGfLHhQ=\n" +
                "-----END CERTIFICATE-----";

        return new Buffer()
                .writeUtf8(certAuthority)
                .inputStream();
    }
}