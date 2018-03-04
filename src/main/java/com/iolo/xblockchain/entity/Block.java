package com.iolo.xblockchain.entity;

import com.iolo.xblockchain.util.StringUtil;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: iOLO
 * Date: 2018/3/4
 * Time: 13:33
 */
public class Block {
    //hash值，数字签名
    public String hash;
    //上一个区块的哈希值
    public String previousHash;
    //存储数据
    public String data;
    //区块诞生时间
    public long timeStamp;
    //随机数 本项目为递增
    public int nonce;

    //Block 类的构造方法.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        //其他值赋值后在进行计算
        this.hash = calculateHash();
    }

    //计算hash值，根据不想被篡改的进行hash计算
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data
        );
        return calculatedhash;
    }
    //挖矿 difficulty需要处理的0的数量
    public void mineBlock(int difficulty) {
        //创建一个用 difficulty * "0" 组成的字符串
        String target = new String(new char[difficulty]).replace('\0', '0');
        //类似一个规则，满足这个规则的hash才能算成功
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined nonce!!! : " + nonce);
        System.out.println("Block Mined!!! : " + hash);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
