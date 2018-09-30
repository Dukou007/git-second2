package mblog.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * 创建用户:狂飙的yellowcong
 * 创建时间:上午11:53:52
 * 创建日期:2017年12月2日
 * 机能概要:索引我的笔记文件夹，然后输入关键词，获取到文件
 * http://blog.haoji.me/lucene-hello-world.html?from=xa
 * https://github.com/sxei/lucene-demo
 */
public class LuceneDemo {
    public static final String INDEX_PATH = "D:\\test"; // 存放Lucene索引文件的位置


    public static void main(String args[]) throws IOException {

        Analyzer analyzer = new SmartChineseAnalyzer();

        TokenStream stream=  analyzer.tokenStream("content", "生活就像海洋，只有意志坚强的人才能到达彼岸");
        //获取每个单词信息,获取词元文本属性
        CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
        stream.reset();
        while(stream.incrementToken()){
            System.out.print("[" + cta + "]");
        }

        System.out.println();
        System.out.println("----------------------------------------");

//        LuceneDemo demo = new LuceneDemo();
//        demo.creatIndex();
//        demo.search("生活");
    }

    /**
     * 创建索引
     */
    public void creatIndex() {
        IndexWriter indexWriter = null;
        try {
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));

            //分词器
            Analyzer analyzer = new SmartChineseAnalyzer();

            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            indexWriter.deleteAll();// 清除以前的index


            Document document = new Document();
            document.add(new Field("content", "生活就像海洋，只有意志坚强的人才能到达彼岸生", TextField.TYPE_STORED));
            indexWriter.addDocument(document);

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexWriter != null) indexWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 搜索
     */
    public void search(String keyWord) {
        DirectoryReader directoryReader = null;
        try {
            // 1、创建Directory
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
            // 2、创建IndexReader
            directoryReader = DirectoryReader.open(directory);
            // 3、根据IndexReader创建IndexSearch
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            // 4、创建搜索的Query
            Analyzer analyzer = new SmartChineseAnalyzer(); // 使用?分词

            // 简单的查询，创建Query表示搜索域为content包含keyWord的文档
            //Query query = new QueryParser("content", analyzer).parse(keyWord);

            String[] fields = {"fileName", "content"}; // 要搜索的字段，一般搜索时都不会只搜索一个字段
            // 字段之间的与或非关系，MUST表示and，MUST_NOT表示not，SHOULD表示or，有几个fields就必须有几个clauses
            BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
            // MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国"
            Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);

            // 5、根据searcher搜索并且返回TopDocs
            TopDocs topDocs = indexSearcher.search(multiFieldQuery, 100); // 搜索前100条结果
            System.out.println("共找到匹配处：" + topDocs.totalHits); // totalHits和scoreDocs.length的区别还没搞明白
            // 6、根据TopDocs获取ScoreDoc对象
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共找到匹配文档数：" + scoreDocs.length);

            QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
            // 自定义高亮代码
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span style=\"backgroud:red\">", "</span>");
            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
            highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 7、根据searcher和ScoreDoc对象获取具体的Document对象
                Document document = indexSearcher.doc(scoreDoc.doc);
                System.out.println("-----------------------------------------");

                System.out.println("高亮前：：" + document.get("content"));

                System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (directoryReader != null) directoryReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
