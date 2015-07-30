package org.github.albertyang2007.productlib.mvc.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONNull;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.log4j.Logger;
import org.github.albertyang2007.productlib.common.ProductCatalog;
import org.github.albertyang2007.productlib.mvc.service.IDatabase;
import org.springframework.stereotype.Service;

@Service
public class XmlDatabaseImpl implements IDatabase {
	private static String productsPath = "c:/Temp";
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static String getProductsPath() {
		return productsPath;
	}

	public static void setProductsPath(String productsPath) {
		XmlDatabaseImpl.productsPath = productsPath;
	}

	public boolean hasProduct(int id) {

		JSONArray json = null;

		File f = new File(productsPath + File.separator + fileName);

		if (f.exists()) {

			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON jso = xmlSerializer.readFromFile(f);
			if (!(jso instanceof JSONNull)) {
				if (jso.isArray()) {
					json = (JSONArray) jso;
					for (int i = 0; i < json.size(); i++) {
						JSONObject jsonObject = json.getJSONObject(i);
						ProductCatalog prdu = (ProductCatalog) JSONObject
								.toBean(jsonObject, ProductCatalog.class);

						if (id == prdu.getProductNo()) {
							return true;
						}
					}

				} else {

					JSONObject jsonObject = (JSONObject) jso;
					jsonObject = jsonObject.getJSONObject("e");
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);
					if (id == prdu.getProductNo()) {
						return true;
					}

					// get all product catalogs and put them into request
					// attribute

				}
			}
			return false;

		} else {

			return false;
		}

	}

	public AddProductResult insertProduct(ProductCatalog product) {
		JSONArray json = null;

		String s;

		File f = new File(productsPath + File.separator + fileName);
		if (!hasProduct(product.getProductNo())) {
			try {

				if (f.exists()) {

					XMLSerializer xmlSerializer = new XMLSerializer();
					JSON jso = xmlSerializer.readFromFile(f);
					if (jso.isArray()) {
						json = (JSONArray) jso;
						json.add(product);

						s = xmlSerializer.write(json);

					} else {

						JSONObject jsonObject = (JSONObject) jso;
						jsonObject = jsonObject.getJSONObject("e");
						JSONArray jsa = new JSONArray();
						jsa.add(jsonObject);
						jsa.add(product);
						s = xmlSerializer.write(jsa);
					}

					BufferedWriter output;

					output = new BufferedWriter(new FileWriter(f));

					output.write(s);
					output.close();

				} else {
					f.createNewFile();
					json = new JSONArray();
					json.add(product);
					XMLSerializer xmlSerializer = new XMLSerializer();
					s = xmlSerializer.write(json);
					BufferedWriter output = new BufferedWriter(
							new FileWriter(f));
					output.write(s);
					output.close();
				}
			} catch (IOException e) {
				Logger.getRootLogger().error(e);
				return AddProductResult.unknownError;

			}
			return AddProductResult.successfully;
		} else {
			return AddProductResult.productAlreadyExist;
		}
	}

	public List<ProductCatalog> getAllProducts() {

		JSONArray json = null;
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();

		File f = new File(productsPath + File.separator + fileName);

		if (f.exists()) {

			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON jso = xmlSerializer.readFromFile(f);
			if (jso.isArray()) {
				json = (JSONArray) jso;
				for (int i = 0; i < json.size(); i++) {
					JSONObject jsonObject = json.getJSONObject(i);
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);

					list.add(prdu);
				}

			} else {

				JSONObject jsonObject = (JSONObject) jso;
				jsonObject = jsonObject.getJSONObject("e");
				ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
						jsonObject, ProductCatalog.class);
				list.add(prdu);

				// get all product catalogs and put them into request
				// attribute

			}

			return list;

		} else {

			return null;
		}

	};

	public List<ProductCatalog> getProductSet(String[] ids) {
		JSONArray json = null;

		File f = new File(productsPath + File.separator + fileName);

		List<ProductCatalog> list = new ArrayList<ProductCatalog>();

		if (f.exists()) {
			JSONArray rcjson = new JSONArray();
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON jso = xmlSerializer.readFromFile(f);
			if (jso.isArray()) {
				json = (JSONArray) jso;

				for (int i = 0; i < json.size(); i++) {
					JSONObject jsonObject = json.getJSONObject(i);
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);
					if (prdu.isEnabled()) {

						for (int j = 0; j < ids.length; j++) {

							if (prdu.getProductNo() == Integer.parseInt(ids[j])) {

								rcjson.add(jsonObject);
								list.add(prdu);
								break;
							}
						}

					}
				}

			} else {

				JSONObject jsonObject = (JSONObject) jso;
				jsonObject = jsonObject.getJSONObject("e");
				ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
						jsonObject, ProductCatalog.class);
				if (prdu.isEnabled()) {
					for (int j = 0; j < ids.length; j++) {

						if (prdu.getProductNo() == Integer.parseInt(ids[j])) {

							list.add(prdu);
							return list;
						}
					}
				}
				// get all product catalogs and put them into request
				// attribute

			}

		}

		return list;
	};

	public List<ProductCatalog> getAllProductsJson() {
		JSONArray json = null;

		File f = new File(productsPath + File.separator + fileName);

		List<ProductCatalog> list = new ArrayList<ProductCatalog>();

		if (f.exists()) {
			JSONArray rcjson = new JSONArray();
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON jso = xmlSerializer.readFromFile(f);
			if (jso.isArray()) {
				json = (JSONArray) jso;

				for (int i = 0; i < json.size(); i++) {
					JSONObject jsonObject = json.getJSONObject(i);
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);
					if (prdu.isEnabled()) {

						rcjson.add(jsonObject);
						list.add(prdu);

					}
				}

			} else {

				JSONObject jsonObject = (JSONObject) jso;
				jsonObject = jsonObject.getJSONObject("e");
				ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
						jsonObject, ProductCatalog.class);
				if (prdu.isEnabled()) {

					list.add(prdu);
					return list;

				}
				// get all product catalogs and put them into request
				// attribute

			}

		}

		return list;

	}

	public int getProductCount() {

		return getAllProductsJson().size();
	}

	public ProductCatalog getProductJson(String id) {
		JSONArray json = null;

		File f = new File(productsPath + File.separator + fileName);

		if (f.exists()) {

			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON jso = xmlSerializer.readFromFile(f);

			if (jso.isArray()) {
				json = (JSONArray) jso;
				for (int i = 0; i < json.size(); i++) {
					JSONObject jsonObject = json.getJSONObject(i);
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);
					if (prdu.getProductNo() == Integer.parseInt(id)) {

						return prdu;
					}

				}

			} else {

				JSONObject jsonObject = (JSONObject) jso;
				jsonObject = jsonObject.getJSONObject("e");
				ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
						jsonObject, ProductCatalog.class);
				if (prdu.getProductNo() == Integer.parseInt(id)) {

					return prdu;
				}

				// get all product catalogs and put them into request
				// attribute

			}

		}

		return null;

	}

	public void removeProduct(String id) {

		JSONArray json = null;

		File f = new File(productsPath + File.separator + fileName);

		try {
			if (f.exists()) {

				XMLSerializer xmlSerializer = new XMLSerializer();
				JSON jso = xmlSerializer.readFromFile(f);
				if (!(jso instanceof JSONNull )){
				if (jso.isArray()) {
					json = (JSONArray) jso;
					for (int i = 0; i < json.size(); i++) {
						JSONObject jsonObject = json.getJSONObject(i);
						ProductCatalog prdu = (ProductCatalog) JSONObject
								.toBean(jsonObject, ProductCatalog.class);

						if (Integer.parseInt(id) == prdu.getProductNo()) {
							json.remove(jsonObject);
							break;
						}

					}
					String s = xmlSerializer.write(json);
					BufferedWriter output;

					output = new BufferedWriter(new FileWriter(f));

					output.write(s);
					output.close();

				} else {

					JSONObject jsonObject = (JSONObject) jso;
					jsonObject = jsonObject.getJSONObject("e");
					ProductCatalog prdu = (ProductCatalog) JSONObject.toBean(
							jsonObject, ProductCatalog.class);
					if (Integer.parseInt(id) == prdu.getProductNo()) {
						jsonObject.remove(jsonObject);

					}
					String s = xmlSerializer.write(json);
					BufferedWriter output;

					output = new BufferedWriter(new FileWriter(f));

					output.write(s);
					output.close();
					// get all product catalogs and put them into request
					// attribute

				}}

			}
		} catch (IOException e) {
			Logger.getRootLogger().error(e);

		}

	}

	public List<ProductCatalog> getProducts(Integer begin, Integer end) {

		List<ProductCatalog> allProducts = getAllProductsJson();
		int beginIndex;
		if (begin == null) {
			beginIndex = 0;
		} else {
			beginIndex = begin;
		}
		int endIndex;
		if (end == null || end >= allProducts.size()) {
			endIndex = allProducts.size() - 1;
		} else {
			endIndex = end;
		}
		if (begin >= allProducts.size()) {
			return null;
		}
		try {
			return allProducts.subList(beginIndex, endIndex + 1);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
