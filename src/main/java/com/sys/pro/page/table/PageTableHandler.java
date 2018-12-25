package com.sys.pro.page.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JYB 
 * @date 2018.10 
 * @version 版本标识
 * @parameter 分页查询处理器
 * @return 
 * @throws
 */
public class PageTableHandler {
	private CountHandler countHandler;
	private ListHandler listHandler;
	private OrderHandler orderHandler;

	public PageTableResponse handle(PageTableRequest dtRequest) {
		int count = 0;
		List<?> list = null;
		count = this.countHandler.count(dtRequest);
		if (count > 0) {
			if (orderHandler != null) {
				dtRequest = orderHandler.order(dtRequest);
			}
			list = this.listHandler.list(dtRequest);
		}
		
		if (list == null) {
			list  = new ArrayList<>();
		}
		return new PageTableResponse(count, count, list);
	}

	public PageTableHandler(CountHandler countHandler, ListHandler listHandler) {
		super();
		this.countHandler = countHandler;
		this.listHandler = listHandler;
	}

	public PageTableHandler(CountHandler countHandler, ListHandler listHandler, OrderHandler orderHandler) {
		this(countHandler, listHandler);
		this.orderHandler = orderHandler;
	}

	public interface OrderHandler {
		PageTableRequest order(PageTableRequest request);
	}

	public interface ListHandler {
		List<?> list(PageTableRequest request);
	}

	public interface CountHandler {
		int count(PageTableRequest request);
	}
}
