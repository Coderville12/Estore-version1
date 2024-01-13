package com.gp.electro.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.gp.electro.store.dtos.PageableResponse;


public class Helper {
	
	
	
	public static <E,V> PageableResponse<V> getPageableResponse(Page<E> page, Class<V> type) {
		
		List<E> entity = page.getContent();
		List<V> entityDto = entity.stream()
				.map(object -> new ModelMapper().map (object,type))
				.collect(Collectors.toList());
		PageableResponse<V> pageableResponse = PageableResponse.<V>builder()
				.content(entityDto)
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.isLastPage(page.isLast())
				.build();
		
		return pageableResponse;
	}

}
