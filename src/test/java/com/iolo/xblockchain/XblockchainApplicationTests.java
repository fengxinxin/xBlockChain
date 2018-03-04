package com.iolo.xblockchain;

import com.google.gson.GsonBuilder;
import com.iolo.xblockchain.entity.Block;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XblockchainApplicationTests {
    //区块链
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    //区块链的难度
    public static int difficulty = 6;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() {
        //因为需要起始区块，之前没有其他的，所以暂用“0”作为他前一块的hash值
        Block genesisBlock = new Block("Hi im the first block", "0");
        System.out.println("Hash for block 1 : " + genesisBlock.getHash());

        Block secondBlock = new Block("Yo im the second block", genesisBlock.getHash());
        System.out.println("Hash for block 2 : " + secondBlock.getHash());

        Block thirdBlock = new Block("Hey im the third block", secondBlock.getHash());
        System.out.println("Hash for block 3 : " + thirdBlock.getHash());
        //至此只是区块，还没有达到链
    }

    @Test
    public void test2() {
        //将我们的区块加入到区块链 ArrayList 中：
        blockchain.add(new Block("Hi im the first block", "0"));
        //之所以需要-1，因为几个并没有定长度，每次添加一个元素，获取前一个元素的hash值
        blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).getHash()));
        blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).getHash()));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }

    @Test
    public void test3() {
        //将我们的区块添加至区块链 ArrayList 中：

        blockchain.add(new Block("Hi im the first block", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }

    //验证合法性
    public static Boolean isChainValid() {
        //当前区块
        Block currentBlock;
        //前区块
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        //循环区块链并检查 hash 值
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //比较当前区块存储的 hash 值和计算出来的 hash 值：
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //比较前一个区块存储的 hash 值和当前区块存储的 previousHash 值：
            if (!previousBlock.getHash().equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //检查 hash 值是否已经存在，验证前几个是否是0
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
