package org.github.albertyang2007.productlib.mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.service.IDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductsJsonController {

	@Autowired
	private IDatabase xmldatabase;

	@RequestMapping("/products")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ResponseBody
	public List<ProductCatalog> getAllProductCatalog(
			@RequestParam(value = "begin", required = false)Integer beginIndex,
			@RequestParam(value = "end", required = false)Integer endIndex,
			@RequestParam(value = "productNo", required = false) String productNos,			
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
    
		if(beginIndex!=null||endIndex!=null){
			return xmldatabase.getProducts(beginIndex, endIndex);
		}else if (productNos != null) {
            
			String[] productNosArray = productNos.split(",");
			return xmldatabase.getProductSet(productNosArray);
		} else {

			return xmldatabase.getAllProductsJson();
		}
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ResponseBody
	public ProductCatalog getProductCatalog(@PathVariable String id)
			throws IOException {
		return xmldatabase.getProductJson(id);

	}
	
	@RequestMapping(value = "/products/count", method = RequestMethod.GET)
	@ResponseBody
	public int getProductCount()
			throws IOException {
		return xmldatabase.getProductCount();

	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public String putProduct(HttpServletRequest request) throws IOException {

		return null;

	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delProduct(@PathVariable String id, HttpServletResponse response)
			throws IOException {

		xmldatabase.removeProduct(id);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);

	}
}
