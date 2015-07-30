package org.github.albertyang2007.productlib.mvc.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.controller.ProductsWebController;
import org.github.albertyang2007.productlib.mvc.service.IDatabase;
import org.github.albertyang2007.productlib.mvc.service.IDatabase.AddProductResult;
import org.github.albertyang2007.productlib.mvc.service.impl.XmlDatabaseImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:Config.xml" })
public class ProductsWebControllerTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	public AnnotationMethodHandlerAdapter handlerAdapter;
	@Autowired
	private ProductsWebController productsWebController;

	private IDatabase db;
	private static final String SUCCESS_VIEW = "succ";

	private static final String FAILED_VIEW = "addfail";
	private static MockHttpServletRequest request;

	private static MockHttpServletResponse response;

	@Before
	public void before() {
		this.db = org.mockito.Mockito.mock(XmlDatabaseImpl.class);

	}

	@After
	public void after() {
	}

	@Test
	public void testAddOK() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/addproduct.do");
		request.setMethod(HttpMethod.POST.name());

		response = new MockHttpServletResponse();
		request.addParameter("description", "eyaweiw");
		request.addParameter("price", "123456");
		request.addParameter("productID", "100008");
		String[] t = new String[] { "true" };
		request.addParameter("enabled", t);

		ProductCatalog product = new ProductCatalog();
		product.setDescription("eyaweiw");

		product.setEnabled(Boolean.parseBoolean("true"));

		product.setPrice(Float.parseFloat("123456"));
		product.setProductNo(Integer.parseInt("100008"));

		when(db.insertProduct(product)).thenReturn(AddProductResult.successfully);

		ReflectionTestUtils.setField(productsWebController, "xmldatabase", db,
				IDatabase.class);

		ModelAndView mv = null;
		try {
			mv = handlerAdapter
					.handle(request, response, productsWebController);
		} catch (Exception e) {
			Logger.getRootLogger().error(e);
		}

		Assert.assertThat(mv, notNullValue());
		Assert.assertThat(response.getStatus(), is(200));
		Assert.assertThat(mv.getViewName(), is("redirect:showall.do"));

		verify(db, times(1)).insertProduct(product);
		verifyNoMoreInteractions(db);
	}
    
	@Test
	public void testAddExistedProduct() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/addproduct.do");
		request.setMethod(HttpMethod.POST.name());

		response = new MockHttpServletResponse();
		request.addParameter("description", "eyaweiw");
		request.addParameter("price", "123456");
		request.addParameter("productID", "100008");
		String[] t = new String[] { "true" };
		request.addParameter("enabled", t);

		ProductCatalog product = new ProductCatalog();
		product.setDescription("eyaweiw");

		product.setEnabled(Boolean.parseBoolean("true"));

		product.setPrice(Float.parseFloat("123456"));
		product.setProductNo(Integer.parseInt("100008"));

		when(db.insertProduct(product)).thenReturn(AddProductResult.productAlreadyExist);

		ReflectionTestUtils.setField(productsWebController, "xmldatabase", db,
				IDatabase.class);

		ModelAndView mv = null;
		try {
			mv = handlerAdapter
					.handle(request, response, productsWebController);
		} catch (Exception e) {
			Logger.getRootLogger().error(e);
		}

		Assert.assertThat(mv, notNullValue());
		Assert.assertThat(response.getStatus(), is(200));
		Assert.assertThat(mv.getViewName(), is("redirect:showall.do"));

		verify(db, times(1)).insertProduct(product);
		verifyNoMoreInteractions(db);
	}
	@Test
	public void testAddInvalidProduct() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/addproduct.do");
		request.setMethod(HttpMethod.POST.name());

		response = new MockHttpServletResponse();
		request.addParameter("description", "eyaweiw");
	    request.addParameter("productID", "100008");
		String[] t = new String[] { "true" };
		request.addParameter("enabled", t);




		ModelAndView mv = null;
		try {
			mv = handlerAdapter
					.handle(request, response, productsWebController);
		} catch (Exception e) {
			Logger.getRootLogger().error(e);
		}

		Assert.assertThat(mv, notNullValue());
		Assert.assertThat(response.getStatus(), is(200));
		Assert.assertThat(mv.getViewName(), is(FAILED_VIEW));


	}

	@Test
	public void testShowAll() {

		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		request.setRequestURI("/showall.do");
		request.setMethod(HttpMethod.GET.name());
		request.setAttribute("info", "TEST");

		response = new MockHttpServletResponse();
		ModelAndView mv = null;
		try {
			mv = handlerAdapter
					.handle(request, response, productsWebController);
		} catch (Exception e) {
			Logger.getRootLogger().error(e);
		}

		Assert.assertThat(mv, notNullValue());
		Assert.assertThat(response.getStatus(), is(200));
		Assert.assertThat(mv.getViewName(), is(SUCCESS_VIEW));

	}

}
