package com.joeun.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PagingMapper {

	int countAllProducts();
	int countProductsByKeyword(String keyword);
	int countProductscategory(int category);
<<<<<<< Updated upstream
	int countAllPost();
=======


>>>>>>> Stashed changes
}
