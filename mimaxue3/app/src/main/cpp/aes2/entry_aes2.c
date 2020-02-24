//
// Created by superads-028 on 2019/8/5.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <fcntl.h>
#include <errno.h>

#include "aes.h"

#include <android/log.h>
#include <jni.h>

void main_aes2() {
    int i;

    const unsigned char master_key[16] = {
            '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0', 'a', 'b', 'c', 'd', 'e', 'f',
    };
    const unsigned char text[16] = {
            '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0', 'a', 'b', 'c', 'd', 'e', 'f',
    };

    const unsigned char iv[16] = {
            '9', '0', 'a', 'b', 'c', 'd', 'e', 'f',
            '1', '2', '3', '4', '5', '6', '7', '8',
    };

    unsigned char ivc[16];

    unsigned char encrypted[16], decrypted[16];
    memset(encrypted, 0, 16 * sizeof(char));
    memset(decrypted, 0, 16 * sizeof(char));

    AES_KEY key;

    for (i = 0; i < 16; i++) {
        printf("%02x ", (unsigned int) text[i]);
    }

    AES_set_encrypt_key(master_key, 128, &key);

    // ecb
    // AES_ecb_encrypt(text, encrypted, &key, AES_ENCRYPT);


    //cbc
    memcpy(ivc, iv, 16 * sizeof(char));
    AES_cbc_encrypt(text, encrypted, 16, &key, ivc, AES_ENCRYPT);

    for (i = 0; i < 16; i++) {
        printf("%02x ", (unsigned int) encrypted[i]);
    }

    AES_set_decrypt_key(master_key, 128, &key);
    // ecb
    // AES_ecb_encrypt(encrypted, decrypted, &key, AES_DECRYPT);

    //cbc
    memcpy(ivc, iv, 16 * sizeof(char));
    AES_cbc_encrypt(encrypted, decrypted, 16, &key, ivc, AES_DECRYPT);

    for (i = 0; i < 16; i++) {
        printf("%02x ", (unsigned int) decrypted[i]);
    }

}

JNIEXPORT void JNICALL
Java_com_superads_mimaxue3_NativeEntry_aes2(
        JNIEnv *env,
        jobject thiz) {
        __android_log_print(ANDROID_LOG_DEBUG, "SUPER_ADS_NATIVE", "main_aes2\n");
        main_aes2();

}