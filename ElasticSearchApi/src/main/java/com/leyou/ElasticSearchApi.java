package com.leyou;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.Query;
import java.net.InetAddress;

public class ElasticSearchApi  {
    TransportClient client=null;
    @Before
    public void initClient()throws Exception{
        client=new PreBuiltTransportClient(Settings.EMPTY).
                addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));
    }
    @Test
    public void testQuery(){
//        QueryBuilder qb = QueryBuilders.termQuery("goodsName", "小米");//构建term查询
//        MatchAllQueryBuilder qb = QueryBuilders.matchAllQuery();//构建查询所有
//        MatchQueryBuilder qb = QueryBuilders.matchQuery("goodsName", "小米华为");
//        WildcardQueryBuilder qb = QueryBuilders.wildcardQuery("goodsName", "*小*");
//        FuzzyQueryBuilder qb = QueryBuilders.fuzzyQuery("goodsName", "小迷");
//        qb.fuzziness(Fuzziness.ONE);
//        RangeQueryBuilder qb = QueryBuilders.rangeQuery("price").gte(2000).lte(4000);
        BoolQueryBuilder qb = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("goodsName", "小米"))
                .mustNot((QueryBuilders.rangeQuery("price").gte(2500).lte(3000)));
        SearchResponse response = client.prepareSearch("heima")//执行查询的索引库
                .setQuery(qb)//把构建的term查询 放入到请求中生效
                .get();//执行
        long totalHits = response.getHits().getTotalHits();
        System.out.println("总数为："+totalHits);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }

    @After
    public void end(){
        client.close();
    } 
	@After
    public void AA(){
        client.close();
    }
	@After
    public void BB(){
        client.close();
    }
	
}
