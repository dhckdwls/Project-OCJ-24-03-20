package com.example.demo.repository;

import com.example.demo.vo.CSV;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CSVRepository {

    @Insert({
            "<script>",
            "INSERT INTO CSV (ranking, title, address, type, count) VALUES ",
            "<foreach collection='csvList' item='item' index='index' separator=','>",
            "(#{item.ranking},#{item.title}, #{item.address}, #{item.type}, #{item.count})",
            "</foreach>",
            "</script>"
    })
    void insertCSVList(List<CSV> csvList);
}