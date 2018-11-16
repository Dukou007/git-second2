package com.example.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sean on 2018/8/19.
 */
public class CreateTest {

    public static void main(String[] args) throws Exception {
        Path indexPath = FileSystems.getDefault().getPath("d:\\index\\");

//        FSDirectory有三个主要的子类,open方法会根据系统环境自动挑选最合适的子类创建
//        MMapDirectory：Linux, MacOSX, Solaris
//        NIOFSDirectory：other non-Windows JREs
//        SimpleFSDirectory：other JREs on Windows
        Directory dir = FSDirectory.open(indexPath);

        // 分词器
        Analyzer analyzer = new StandardAnalyzer();
        boolean create = true;
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        if (create) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        } else {
            // lucene是不支持更新的，这里仅仅是删除旧索引，然后创建新索引
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        }
        IndexWriter indexWriter = new IndexWriter(dir, indexWriterConfig);

        Document doc = new Document();
        // 域值会被索引，但是不会被分词，即被当作一个完整的token处理，一般用在“国家”或者“ID
        // Field.Store表示是否在索引中存储原始的域值
        // 如果想在查询结果里显示域值，则需要对其进行存储
        // 如果内容太大并且不需要显示域值（整篇文章内容），则不适合存储到索引中
        doc.add(new StringField("Title", "sean", Field.Store.YES));
        long time = new Date().getTime();
        // LongPoint并不存储域值
        doc.add(new LongPoint("LastModified", time));
//        doc.add(new NumericDocValuesField("LastModified", time));
        // 会自动被索引和分词的字段，一般被用在文章的正文部分
        doc.add(new TextField("Content", "this is a test of sean", Field.Store.NO));

        List<Document> docs = new LinkedList<>();
        docs.add(doc);

        indexWriter.addDocuments(docs);
        // 默认会在关闭前提交
        indexWriter.close();
    }
}