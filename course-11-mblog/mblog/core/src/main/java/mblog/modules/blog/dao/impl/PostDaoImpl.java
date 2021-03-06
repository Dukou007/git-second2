/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.modules.blog.dao.impl;

import mblog.modules.blog.data.PostVO;
import mblog.modules.utils.BeanMapUtils;
import mblog.modules.blog.dao.custom.PostDaoCustom;
import mblog.modules.blog.entity.Post;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author langhsu
 *
 */
public class PostDaoImpl implements PostDaoCustom {

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<PostVO> search(Pageable pageable, String q) throws Exception {
		//根据EntityManager来创建一个全文实体管理对象
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		SearchFactory sf = fullTextSession.getSearchFactory();
		//为实体类创建一个查询构建，实体类上需要有索引注解等
		QueryBuilder qb = sf.buildQueryBuilder().forEntity(Post.class).get();

		//设置搜索关键字，设置查询返回字段。根据构建条件创建查询
		org.apache.lucene.search.Query luceneQuery  = qb.keyword().onFields("title","summary","tags")
				.matching(q).createQuery();

		//使用lucene的session对象查询结果。返回的是全文搜索的查询对象。（使用org.hibernate.Query包装Lucene query）
		org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		//对查询结果做高亮效果
	    StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
	    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        QueryScorer queryScorer = new QueryScorer(luceneQuery);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        
		List<Post> list = query.getResultList();
	    List<PostVO> rets = new ArrayList<>(list.size());

	    for (Post po : list) {
			PostVO m = BeanMapUtils.copy(po, 0) ;

			// 处理高亮
			String title = highlighter.getBestFragment(standardAnalyzer, "title", m.getTitle());
			String summary = highlighter.getBestFragment(standardAnalyzer, "summary", m.getSummary());

			if (StringUtils.isNotEmpty(title)) {
				m.setTitle(title);
			}
			if (StringUtils.isNotEmpty(summary)) {
				m.setSummary(summary);
			}
			rets.add(m);
		}
		return new PageImpl<>(rets, pageable, query.getResultSize());
	}
	
	@Override
	public Page<PostVO> searchByTag(Pageable pageable, String tag) {
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		SearchFactory sf = fullTextSession.getSearchFactory();
		QueryBuilder qb = sf.buildQueryBuilder().forEntity(Post.class).get();

		org.apache.lucene.search.Query luceneQuery  = null;

		MustJunction term = qb.bool().must(qb.phrase().onField("tags").sentence(tag).createQuery());

		luceneQuery = term.createQuery();

		org.hibernate.search.jpa.FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery);
	    query.setFirstResult(pageable.getOffset());
	    query.setMaxResults(pageable.getPageSize());

		Sort sort = new Sort(new SortField("id", SortField.Type.LONG, true));
		query.setSort(sort);

		List<Post> results = query.getResultList();
		List<PostVO> rets = new ArrayList<>(results.size());

		for (Post po : results) {
			PostVO m = BeanMapUtils.copy(po, 0);
			rets.add(m);
		}

		return new PageImpl<>(rets, pageable, query.getResultSize());
	}

	@Override
	public void resetIndexs() {
		FullTextEntityManager fullTextSession = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		//异步
		fullTextSession.createIndexer(Post.class).start();
	}

}
