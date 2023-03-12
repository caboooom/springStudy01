package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;//JPA에서 제공하는 repository interface

import java.util.ArrayList;

//CrudRepository<관리 대상 entity, 대표값의 type>
public interface ArticleRepository extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();
}
