package org.github.albertyang2007.productlib.mvc.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.service.IDatabase;
import org.github.albertyang2007.productlib.mvc.service.IDatabase.AddProductResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductsWebController {
	@Autowired
	private IDatabase xmldatabase;

	private static final String SUCCESS_VIEW = "succ";

	private static final String FAILED_VIEW = "addfail";

	private static final String FAILED_REASON = "Invalid Product information!";

	@RequestMapping("/addproduct.do")
	public ModelAndView addProduct(

	HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");

		String description = request.getParameter("description");
		String price = request.getParameter("price");
		String enabled = request.getParameter("enabled");
		String productNo = request.getParameter("productID");

		HashMap<String, String> resultMap = new HashMap<String, String>();
		if ((productNo == null) || (description == null) || (price == null)) {
			resultMap.put("failReason", FAILED_REASON);
			return new ModelAndView(FAILED_VIEW, resultMap);
		}

		ProductCatalog product = new ProductCatalog();

		product.setDescription(description);

		if (enabled != null) {

			product.setEnabled(Boolean.parseBoolean(enabled));

		}
		product.setPrice(Float.parseFloat(price));
		product.setProductNo(Integer.parseInt(productNo));

		String key = "info";

		AddProductResult rc = xmldatabase.insertProduct(product);

		if (rc == AddProductResult.successfully) {

			resultMap.put(key, "Product " + productNo
					+ " is added successfully!");
		}

		if (rc == AddProductResult.productAlreadyExist) {

			resultMap.put(key, "Product "+productNo + " is already existed!");
		}

		if (rc == AddProductResult.unknownError) {

			resultMap.put(key, "Cannot add Product " + productNo);
		}
		return new ModelAndView("redirect:showall.do", resultMap);

	}

	@RequestMapping("/showall.do")
	public ModelAndView showAll(
			@RequestParam(value = "info", required = false) String prompt,
			HttpServletRequest request) throws IOException {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String key = "info";
		if (prompt != null) {
			resultMap.put(key, prompt);

		}
		resultMap.put("products", xmldatabase.getAllProducts());
		return new ModelAndView(SUCCESS_VIEW, resultMap);

	}
}
