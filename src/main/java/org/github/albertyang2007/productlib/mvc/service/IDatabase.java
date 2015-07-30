package org.github.albertyang2007.productlib.mvc.service;

import java.util.List;

import org.github.albertyang2007.productlib.common.ProductCatalog;

public interface IDatabase {
	 AddProductResult insertProduct(ProductCatalog product);
	 
	 public static enum AddProductResult {
			successfully, productAlreadyExist, unknownError
		};

	 List<ProductCatalog> getAllProducts();	 
	 
	 List<ProductCatalog> getProducts(Integer begin, Integer end);
	
	 List<ProductCatalog> getProductSet(String[] ids);

	 List<ProductCatalog> getAllProductsJson();

	 ProductCatalog getProductJson(String id);
	 
	 int getProductCount();

	 void removeProduct(String id);

}
