package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticApplicationTests {

    @Autowired
    JestClient jestClient;

    /**
     * 测试添加
     */
    @Test
    public void add() {
        //1、给Es中索引（保存）一个文档；
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("zhangsan");
        article.setContent("Hello World");

        Article article2 = new Article(2, "国家大事", "lisi", "The World!");
        Article article3 = new Article(3, "娱乐新闻", "wangwu", "Hello");
        Article article4 = new Article(4, "坏消息", "zhaoliu", "S·H·I·T");

        //构建一个索引功能							   database         table
        Index index = new Index.Builder(article).index("atguigu").type("news").build();
        Index index2 = new Index.Builder(article2).index("atguigu").type("news").build();
        Index index3 = new Index.Builder(article3).index("atguigu").type("news").build();
        Index index4 = new Index.Builder(article4).index("atguigu").type("news").build();

        try {
            //执行
            jestClient.execute(index);
            jestClient.execute(index2);
            jestClient.execute(index3);
            jestClient.execute(index4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试分页 没测试还
     */
    public void addPage() {
        for (int i = 0; i < 100; i++) {
            new Article(i, "作者" + i, "title" + i, "The World! " + i + " s");
        }
    }

    /**
     * 测试删除
     */
    @Test
    public void delete() {
        //构建一个删除功能
        Delete delete = new Delete.Builder("4").index("atguigu").type("news").build();

        try {
            //执行
            DocumentResult deleteResult = jestClient.execute(delete);
            System.out.println(deleteResult);
//			deleteResult.getPathToResult(); // ok
//			deleteResult.getResponseCode(); // 200
//			deleteResult.isSucceeded(); // true
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试更新 没试
     */
    public void update(){
        String script = "{\n" +
                "    \"script\" : \"ctx._source.tags += tag\",\n" +
                "    \"params\" : {\n" +
                "        \"tag\" : \"blue\"\n" +
                "    }\n" +
                "}";

        try {
            jestClient.execute(new Update.Builder(script).index("atguigu").type("news").id("1").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试修改 直接再添加一遍就好了
     *
     * 测试查询单个
     */
    @Test
    public void searchOne() {
        Get search = new Get.Builder("atguigu","1").type("news").build();

        try {
            DocumentResult result = jestClient.execute(search);
            Article article = result.getSourceAsObject(Article.class);
            System.out.println(article.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查询列表
     */
    @Test
    public void search() {

        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        //更多操作：https://github.com/searchbox-io/Jest/tree/master/jest
        //构建搜索功能
        Search search = new Search.Builder("").addIndex("atguigu").addType("news").build();
//		Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

        //执行
        try {
            SearchResult result = jestClient.execute(search);
            JsonObject jsonObject = result.getJsonObject();
            JsonObject hitsObject = jsonObject.getAsJsonObject("hits");
            long took = jsonObject.get("took").getAsLong();
            long total = hitsObject.get("total").getAsLong();

            System.out.println("took:" + took + "  " + "total:" + total);

            //https://blog.csdn.net/u011781521/article/details/77853824
            //自动解析 还有手动解析分页等没研究。。
            List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);

            List<Article> articles = new ArrayList<>();
            for (SearchResult.Hit<Article, Void> hit : hits) {

                Article source = hit.source;
                articles.add(source);

            }
            System.out.println();
            System.out.println(articles.size());
            System.out.println(result.getJsonString());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
