package com.freestyle.wenda.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {

    private static final String DEFAULT_REPLEMEMTN = "***";

    // root node
    private TrieNode rootNode = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            System.out.println("trie begin");
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            read.close();
            System.out.println("trie ok");
        } catch (Exception e) {

        }
    }

    private class TrieNode {

        boolean isEnd = false;

        Map<Character, TrieNode> subNodes = new HashMap<>();

        void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getTrieNode(Character key) {
            return subNodes.get(key);
        }

        void setKeywordEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        boolean getKeywordEnd() {
            return this.isEnd;
        }
    }

    void addWord(String text) {

        int len = text.length();
        TrieNode tmpNode = rootNode;
        for (int i = 0; i < len; i ++) {
            Character character = text.charAt(i);

            TrieNode node = tmpNode.getTrieNode(character);
            if (node == null) {
                node = new TrieNode();
                tmpNode.addSubNode(character, node);
            }

            tmpNode = node;

            if (i == len - 1) {
                node.setKeywordEnd(true);
            }
        }
    }

    String filter(String text) {

        int len = text.length();

        int begin = 0;
        int position = 0;

        StringBuilder result = new StringBuilder();

        TrieNode node = rootNode;

        while (position < len) {
            char ch = text.charAt(position);

//            System.out.println("position = " + position + "ch = " + ch);

            node = node.getTrieNode(ch);

            if (node == null) {
                result.append(text.charAt(begin));
                begin ++;
                position = begin;
                node = rootNode;

            } else if (node.getKeywordEnd()) {

                result.append(DEFAULT_REPLEMEMTN);
                begin = position + 1;
                position = begin;
                node = rootNode;
            } else {
                position ++;
            }
        }

        result.append(text.substring(begin));

        return result.toString();
    }

    public static void main(String[] args) {

        SensitiveService sensitiveService = new SensitiveService();
        sensitiveService.addWord("abc");
        sensitiveService.addWord("df");



        System.out.println(sensitiveService.filter("abcddf"));
    }
}
