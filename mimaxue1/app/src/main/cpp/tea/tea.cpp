//
// Created by superads-028 on 2019/8/6.
//

void EncryptTEA(unsigned int *firstChunk, unsigned int *secondChunk, unsigned int *key) {
    unsigned int y = *firstChunk;
    unsigned int z = *secondChunk;
    unsigned int sum = 0;

    unsigned int delta = 0x9e3779b9;

    for (int i = 0; i < 8; i++)//8轮运算(需要对应下面的解密核心函数的轮数一样)
    {
        sum += delta;
        y += ((z << 4) + key[0]) ^ (z + sum) ^ ((z >> 5) + key[1]);
        z += ((y << 4) + key[2]) ^ (y + sum) ^ ((y >> 5) + key[3]);
    }

    *firstChunk = y;
    *secondChunk = z;
}

void DecryptTEA(unsigned int *firstChunk, unsigned int *secondChunk, unsigned int *key) {
    unsigned int sum = 0;
    unsigned int y = *firstChunk;
    unsigned int z = *secondChunk;
    unsigned int delta = 0x9e3779b9;

    sum = delta << 3; //32轮运算，所以是2的5次方；16轮运算，所以是2的4次方；8轮运算，所以是2的3次方

    for (int i = 0; i < 8; i++) //8轮运算
    {
        z -= (y << 4) + key[2] ^ y + sum ^ (y >> 5) + key[3];
        y -= (z << 4) + key[0] ^ z + sum ^ (z >> 5) + key[1];
        sum -= delta;
    }

    *firstChunk = y;
    *secondChunk = z;
}

//buffer：输入的待加密数据buffer，在函数中直接对元数据buffer进行加密；size：buffer长度；key是密钥；
void EncryptBuffer(char *buffer, int size, unsigned int *key) {
    char *p = buffer;

    int leftSize = size;

    while (p < buffer + size &&
           leftSize >= sizeof(unsigned int) * 2) {
        EncryptTEA((unsigned int *) p, (unsigned int *) (p + sizeof(unsigned int)), key);
        p += sizeof(unsigned int) * 2;

        leftSize -= sizeof(unsigned int) * 2;
    }
}

//buffer：输入的待解密数据buffer，在函数中直接对元数据buffer进行解密；size：buffer长度；key是密钥；
void DecryptBuffer(char *buffer, int size, unsigned int *key) {
    char *p = buffer;

    int leftSize = size;

    while (p < buffer + size &&
           leftSize >= sizeof(unsigned int) * 2) {
        DecryptTEA((unsigned int *) p, (unsigned int *) (p + sizeof(unsigned int)), key);
        p += sizeof(unsigned int) * 2;

        leftSize -= sizeof(unsigned int) * 2;
    }
}

#include <android/log.h>
#include <jni.h>

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <fcntl.h>

void tea_enc() {
//-----设置密钥，必须需要16个字符或以上（这里的长度错误由评论#3楼legion提出修正，表示感谢。）
    unsigned int *key = (unsigned int *) "testkey123456789";
//-----读取文件
    unsigned int pSize = 0;
    char *pBuffer = NULL;
    FILE *fp;
    char *sFileName = const_cast<char *>("/data/data/com.superads.mimaxue1/test/my_json_request.txt");
    if ((fp = fopen(sFileName, "rb")) == NULL) { //sFileName是读取的加密/解密文件名
        perror("Problem in opening the file");
        return;
    }
    fseek(fp, 0, SEEK_END);
    pSize = ftell(fp); //得到长度
    fseek(fp, 0, SEEK_SET);
    pBuffer = new char[pSize]; //开辟内存空间
    pSize = fread(pBuffer, sizeof(char), pSize, fp); //读取内容
    fclose(fp); //关闭文件

//-----对原始文件进行加密
    EncryptBuffer(pBuffer, pSize, key);

//如果是已经加密过的文件，则对应为解密函数
//DecryptBuffer(pBuffer, pSize, key);

//-----将数据写入文件当中
    FILE *fDestFile;
    char *sTagetFileName = const_cast<char *>("/data/data/com.superads.mimaxue1/test/my_json_request_enc.txt");
    //fDestFile = fopen(sTagetFileName, "wb"); //sTagetFileName是写入的加密/解密文件名
    if ((fDestFile = fopen(sTagetFileName, "wb")) == NULL) { //sTagetFileName是写入的加密/解密文件名
        perror("Problem in opening the file");
        exit(1);
    }

    fwrite(pBuffer, sizeof(char), pSize, fDestFile);
    fclose(fDestFile);//关闭文件

    delete[]pBuffer;
}

void tea_dec() {
//-----设置密钥，必须需要16个字符或以上（这里的长度错误由评论#3楼legion提出修正，表示感谢。）
    unsigned int *key = (unsigned int *) "testkey123456789";
//-----读取文件
    unsigned int pSize = 0;
    char *pBuffer = NULL;
    FILE *fp;
    char *sFileName = const_cast<char *>("/data/data/com.superads.mimaxue1/test/my_json_request_enc.txt");
    if ((fp = fopen(sFileName, "rb")) == NULL) { //sFileName是读取的加密/解密文件名
        perror("Problem in opening the file");
        return;
    }
    fseek(fp, 0, SEEK_END);
    pSize = ftell(fp); //得到长度
    fseek(fp, 0, SEEK_SET);
    pBuffer = new char[pSize]; //开辟内存空间
    pSize = fread(pBuffer, sizeof(char), pSize, fp); //读取内容
    fclose(fp); //关闭文件

//-----对原始文件进行加密
//    EncryptBuffer(pBuffer, pSize, key);

//如果是已经加密过的文件，则对应为解密函数
    DecryptBuffer(pBuffer, pSize, key);

//-----将数据写入文件当中
    FILE *fDestFile;
    char *sTagetFileName = const_cast<char *>("/data/data/com.superads.mimaxue1/test/my_json_request_dec.txt");
    //fDestFile = fopen(sTagetFileName, "wb"); //sTagetFileName是写入的加密/解密文件名
    if ((fDestFile = fopen(sTagetFileName, "wb")) == NULL) { //sTagetFileName是写入的加密/解密文件名
        perror("Problem in opening the file");
        exit(1);
    }

    fwrite(pBuffer, sizeof(char), pSize, fDestFile);
    fclose(fDestFile);//关闭文件

    delete[]pBuffer;
}

extern "C" JNIEXPORT void JNICALL
Java_com_superads_mimaxue1_NativeEntry_teaEnc(
        JNIEnv *env,
        jobject /* this */) {
    __android_log_print(ANDROID_LOG_DEBUG, "SUPER_ADS_NATIVE", "before Java_com_superads_mimaxue1_NativeEntry_teaEnc\n");
    tea_enc();
    __android_log_print(ANDROID_LOG_DEBUG, "SUPER_ADS_NATIVE", "after Java_com_superads_mimaxue1_NativeEntry_teaEnc\n");
}

extern "C" JNIEXPORT void JNICALL
Java_com_superads_mimaxue1_NativeEntry_teaDec(
        JNIEnv *env,
        jobject /* this */) {
    __android_log_print(ANDROID_LOG_DEBUG, "SUPER_ADS_NATIVE", "before Java_com_superads_mimaxue1_NativeEntry_teaDec\n");
    tea_dec();
    __android_log_print(ANDROID_LOG_DEBUG, "SUPER_ADS_NATIVE", "Java_com_superads_mimaxue1_NativeEntry_teaDec\n");
}
