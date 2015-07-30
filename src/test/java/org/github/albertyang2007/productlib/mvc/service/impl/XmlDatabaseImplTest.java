package org.github.albertyang2007.productlib.mvc.service.impl;

import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.List;

import net.sf.json.JSONObject;

import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.service.impl.XmlDatabaseImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class XmlDatabaseImplTest {

	private static XmlDatabaseImpl xmlDatabaseImplOne;
	
	private static XmlDatabaseImpl xmlDatabaseImplMultiple;

	private static ProductCatalog product;

	

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		xmlDatabaseImplOne = new XmlDatabaseImpl();
		xmlDatabaseImplOne.setFileName("productsTest1.xml");

		product = new ProductCatalog();
		product.setDescription("SAMSUNG");
		product.setEnabled(true);
		product.setPrice(888);
		product.setProductNo(9898);

		xmlDatabaseImplOne.insertProduct(product);
		
		xmlDatabaseImplMultiple = new XmlDatabaseImpl();
		xmlDatabaseImplMultiple.setFileName("productsTest2.xml");

		
		product.setDescription("HUAWEI");
		product.setEnabled(true);
		product.setPrice(999);
		product.setProductNo(9899);

		xmlDatabaseImplMultiple.insertProduct(product);
		
	
		product.setDescription("SAMSUNG");
		product.setEnabled(true);
		product.setPrice(888);
		product.setProductNo(9898);

		xmlDatabaseImplMultiple.insertProduct(product);
		
	

	}

	@AfterClass
	public static void tearDownBeforeClass() throws Exception {
		File fOne = new File(XmlDatabaseImpl.getProductsPath() + File.separator
				+ xmlDatabaseImplOne.getFileName());
		
		File fMultiple = new File(XmlDatabaseImpl.getProductsPath() + File.separator
				+ xmlDatabaseImplMultiple.getFileName());

		if (fOne.exists()) {
			boolean result = false;
			int tryCount = 0;
			while (!result && tryCount++ < 10) {

				System.gc();
				result = fOne.delete();

			}

		}
		
		if (fMultiple.exists()) {
			boolean result = false;
			int tryCount = 0;
			while (!result && tryCount++ < 10) {

				System.gc();
				result = fMultiple.delete();

			}

		}

	}

	@After
	public  void tearDown() throws Exception {


	}

	
	@Test
	public void testReadJSONProduct() {

		ProductCatalog prdu = xmlDatabaseImplOne.getProductJson(String.valueOf(product
				.getProductNo()));
		
	
		Assert.assertThat(prdu.getProductNo(), is(product.getProductNo()));
		
		 prdu = xmlDatabaseImplMultiple.getProductJson(String.valueOf(product
				.getProductNo()));
		
	
		Assert.assertThat(prdu.getProductNo(), is(product.getProductNo()));

	}

	@Test
	public void testReadProduct() {

		List<ProductCatalog> listUnderTest = xmlDatabaseImplOne.getAllProducts();


		Assert.assertThat(listUnderTest.contains(product), is(true));
		
		 listUnderTest = xmlDatabaseImplOne.getAllProductsJson();

		
		Assert.assertThat(listUnderTest.contains(product), is(true));
		
	 listUnderTest = xmlDatabaseImplMultiple.getAllProducts();


		Assert.assertThat(listUnderTest.contains(product), is(true));
		
		 listUnderTest = xmlDatabaseImplMultiple.getAllProductsJson();

		
		Assert.assertThat(listUnderTest.contains(product), is(true));

	}

	@Test
	public void testReadSetProduct() {

		String[] ids = { "9898" };
		
 
		List<ProductCatalog> listUnderTest = xmlDatabaseImplOne.getProductSet(ids);
		
	
		Assert.assertThat(listUnderTest.contains(product), is(true));
		

		listUnderTest = xmlDatabaseImplMultiple.getProductSet(ids);
		
	
		Assert.assertThat(listUnderTest.contains(product), is(true));

	}
	@Test
	public void testAddProductsAndRead() {

		
		ProductCatalog productTest = new ProductCatalog();
		productTest.setDescription("HUAWEI");
		productTest.setEnabled(true);
		productTest.setPrice(999);
		productTest.setProductNo(9899);

		xmlDatabaseImplOne.insertProduct(productTest);
		
		String[] ids = { "9899" };
		
 
		List<ProductCatalog> listUnderTest = xmlDatabaseImplOne.getProductSet(ids);
		
		Assert.assertThat(listUnderTest.size(), is(1));
		Assert.assertThat(listUnderTest.contains(productTest), is(true));
		
		xmlDatabaseImplOne.removeProduct("9899");
		
		listUnderTest = xmlDatabaseImplOne.getProductSet(ids);
		
		Assert.assertThat(listUnderTest.size(), is(0));


	}
	
}
