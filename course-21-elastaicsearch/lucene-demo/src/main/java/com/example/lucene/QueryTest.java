package com.example.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.FileSystems;
import java.nio.file.Path;
 
/**
 * Created by sean on 2018/8/19.
 */
public class QueryTest {
 
    public static void main(String[] args) throws Exception {
        Path indexPath = FileSystems.getDefault().getPath("d:\\index\\");
        Directory dir = FSDirectory.open(indexPath);
        // 分词器
        Analyzer analyzer = new StandardAnalyzer();
 
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
 
        // 同时查询多个域
//        String[] queryFields = {"Title", "Content", "LastModified"};
//        QueryParser parser = new MultiFieldQueryParser(queryFields, analyzer);
//        Query query = parser.parse("sean");
 
        // 一个域按词查doc
//        Term term = new Term("Title", "test");
//        Query query = new TermQuery(term);
 
        // 模糊查询
//        Term term = new Term("Title", "se*");
//        WildcardQuery query = new WildcardQuery(term);
 
        // 范围查询
        Query query1 = LongPoint.newRangeQuery("LastModified", 1L, 1637069693000L);
 
        // 多关键字查询，必须指定slop（key的存储方式）
        PhraseQuery.Builder phraseQueryBuilder = new PhraseQuery.Builder();
        phraseQueryBuilder.add(new Term("Content", "test"));
        phraseQueryBuilder.add(new Term("Content", "sean"));
        phraseQueryBuilder.setSlop(10);
        PhraseQuery query2 = phraseQueryBuilder.build();
 
        // 复合查询
        BooleanQuery.Builder booleanQueryBuildr = new BooleanQuery.Builder();
        booleanQueryBuildr.add(query1, BooleanClause.Occur.MUST);
        booleanQueryBuildr.add(query2, BooleanClause.Occur.MUST);
        BooleanQuery query = booleanQueryBuildr.build();
 
        // 返回doc排序
        // 排序域必须存在，否则会报错
        Sort sort = new Sort();
        SortField sortField = new SortField("Title", SortField.Type.SCORE);
        sort.setSort(sortField);
 
        TopDocs topDocs = searcher.search(query, 10, sort);
        if(topDocs.totalHits > 0)
            for(ScoreDoc scoreDoc : topDocs.scoreDocs){
                int docNum = scoreDoc.doc;
                Document doc = searcher.doc(docNum);
                System.out.println(doc.toString());
            }
    }
}