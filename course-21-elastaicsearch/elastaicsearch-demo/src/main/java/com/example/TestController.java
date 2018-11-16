package com.example;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * es的基本增删改查
 */
@Controller
public class TestController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @Autowired
    private TransportClient client;



    @ResponseBody
    @GetMapping("/post")
    public Object get(String id) {

        GetResponse response = this.client.prepareGet("post", "news", id).get();

        if(!response.isExists()){
            return null;
        }

        System.out.println("查询结果-----------》" + response.getSource());

        return response.getSource();
    }

    @ResponseBody
    @PostMapping("/post")
    public Object post(String title, String disception, String category) throws Exception{

        XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                .field("title", title).field("disception", disception)
            .field("category", category).endObject();

        IndexResponse response = this.client.prepareIndex("post", "news").setSource(content).get();

        System.out.println("插入索引数据---------》" + response.getResult());
        return response.getId();
    }

    @ResponseBody
    @PutMapping("/post")
    public Object post(String id, String title, String disception, String category) throws Exception{

        XContentBuilder content = XContentFactory.jsonBuilder().startObject();

        if(title != null) {
            content.field("title", title);
        }
        if(disception != null) {
            content.field("disception", disception);
        }
        if(category != null) {
            content.field("category", category);
        }
        content.endObject();


        UpdateRequest update = new UpdateRequest("post", "news", id);
        update.doc(content);



        UpdateResponse response = this.client.update(update).get();

        System.out.println("更新索引数据---------》" + response.getResult());
        return  response.getResult();
    }

    @ResponseBody
    @DeleteMapping("/post")
    public Object delete(String id) throws Exception{

        DeleteResponse response = this.client.prepareDelete("post", "news", id).get();
        System.out.println("删除索引数据---------》" + response.getResult());
        return response.getId();
    }

    @ResponseBody
    @GetMapping("/search")
    public Object search(String title, String category) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(title != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }

        //完全匹配
        boolQueryBuilder.filter(
                QueryBuilders.termQuery("category", category)
        );

        //数字比较
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("views");
        //关键字多字段匹配
//        QueryBuilders.multiMatchQuery(keyword,  ...)

        SearchResponse response = this.client.prepareSearch("post")
                .setTypes("news")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10)
                .get();

        List<Map> results = new ArrayList<>();

        for(SearchHit hit : response.getHits()){
            results.add(hit.getSource());
        }

        System.out.println("查询结果-----------》" + results);

        return results;
    }
}
