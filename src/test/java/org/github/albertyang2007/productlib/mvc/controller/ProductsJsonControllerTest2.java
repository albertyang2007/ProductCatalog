package org.github.albertyang2007.productlib.mvc.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.controller.ProductsJsonController;
import org.github.albertyang2007.productlib.mvc.service.IDatabase;
import org.github.albertyang2007.productlib.mvc.service.impl.XmlDatabaseImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:Config.xml" })
// @ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
public class ProductsJsonControllerTest2 {

	@Autowired
	private IDatabase xmldatabaseMock;
	@Autowired
	private ProductsJsonController productsJsonController;

	private MockMvc mockMvc;
	private ProductCatalog mokeRtnFirst;
	private ProductCatalog mokeRtnSecond;
	private ProductCatalog mokeRtnThird;

	@Before
	public void before() {
		mokeRtnFirst = new ProductCatalog();
		mokeRtnFirst.setProductNo(101);
		mokeRtnFirst.setDescription("des1");
		mokeRtnFirst.setEnabled(true);
		mokeRtnFirst.setPrice(1.0f);

		mokeRtnSecond = new ProductCatalog();
		mokeRtnSecond.setProductNo(102);
		mokeRtnSecond.setDescription("des2");
		mokeRtnSecond.setEnabled(true);
		mokeRtnSecond.setPrice(2.0f);

		mokeRtnThird = new ProductCatalog();
		mokeRtnThird.setProductNo(103);
		mokeRtnThird.setDescription("des3");
		mokeRtnThird.setEnabled(true);
		mokeRtnThird.setPrice(3.0f);

		this.xmldatabaseMock = org.mockito.Mockito.mock(XmlDatabaseImpl.class);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productsJsonController)
				.build();
	}

	@After
	public void after() {

	}

	@Test
	public void findAllProducts() {

		when(xmldatabaseMock.getAllProducts()).thenReturn(
				Arrays.asList(mokeRtnFirst, mokeRtnSecond, mokeRtnThird));

		ReflectionTestUtils.setField(productsJsonController, "xmldatabase",
				xmldatabaseMock, IDatabase.class);

		try {

			mockMvc.perform(get("/products"))
					.andExpect(status().isOk())
					.andExpect(
							content().contentType(
									TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$[0].productNo", is(101)))
					.andExpect(jsonPath("$[0].description", is("des1")))
					.andExpect(jsonPath("$[0].price", is(1.0)))
					.andExpect(jsonPath("$[0].enabled", is(true)))
					.andExpect(jsonPath("$[1].productNo", is(102)))
					.andExpect(jsonPath("$[1].description", is("des2")))
					.andExpect(jsonPath("$[1].price", is(2.0)))
					.andExpect(jsonPath("$[1].enabled", is(true)))
					.andExpect(jsonPath("$[2].productNo", is(103)))
					.andExpect(jsonPath("$[2].description", is("des3")))
					.andExpect(jsonPath("$[2].price", is(3.0)))
					.andExpect(jsonPath("$[2].enabled", is(true)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(xmldatabaseMock, times(1)).getAllProducts();
		verifyNoMoreInteractions(xmldatabaseMock);
	}

	@Test
	public void findProductsByIds() {

		String[] productIds = { "101", "102" };

		when(xmldatabaseMock.getProductSet(productIds)).thenReturn(
				Arrays.asList(mokeRtnFirst, mokeRtnSecond, mokeRtnThird));

		ReflectionTestUtils.setField(productsJsonController, "xmldatabase",
				xmldatabaseMock, IDatabase.class);

		try {

			mockMvc.perform(get("/products?productNo=101,102"))
					.andExpect(status().isOk())
					.andExpect(
							content().contentType(
									TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$[0].productNo", is(101)))
					.andExpect(jsonPath("$[0].description", is("des1")))
					.andExpect(jsonPath("$[0].price", is(1.0)))
					.andExpect(jsonPath("$[0].enabled", is(true)))
					.andExpect(jsonPath("$[1].productNo", is(102)))
					.andExpect(jsonPath("$[1].description", is("des2")))
					.andExpect(jsonPath("$[1].price", is(2.0)))
					.andExpect(jsonPath("$[1].enabled", is(true)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(xmldatabaseMock, times(1)).getProductSet(productIds);
		verifyNoMoreInteractions(xmldatabaseMock);
	}

	@Test
	public void findOneProduct() {

		int productId = 101;

		when(xmldatabaseMock.getProductJson(productId + "")).thenReturn(
				mokeRtnFirst);

		ReflectionTestUtils.setField(productsJsonController, "xmldatabase",
				xmldatabaseMock, IDatabase.class);

		try {

			mockMvc.perform(get("/products/{id}", productId))
					.andExpect(status().isOk())
					.andExpect(
							content().contentType(
									TestUtil.APPLICATION_JSON_UTF8))
					.andExpect(jsonPath("$.productNo", is(productId)))
					.andExpect(jsonPath("$.description", is("des1")))
					.andExpect(jsonPath("$.price", is(1.0)))
					.andExpect(jsonPath("$.enabled", is(true)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(xmldatabaseMock, times(1)).getProductJson(productId + "");
		verifyNoMoreInteractions(xmldatabaseMock);
	}

	@Test
	public void findNoSuchProduct() {

		int productId = 101;

		when(xmldatabaseMock.getProductJson(productId + "")).thenReturn(null);

		ReflectionTestUtils.setField(productsJsonController, "xmldatabase",
				xmldatabaseMock, IDatabase.class);

		try {

			mockMvc.perform(get("/products/" + productId)).andExpect(
					status().isOk());

		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(xmldatabaseMock, times(1)).getProductJson(productId + "");
		verifyNoMoreInteractions(xmldatabaseMock);
	}
}

class TestUtil {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
}
