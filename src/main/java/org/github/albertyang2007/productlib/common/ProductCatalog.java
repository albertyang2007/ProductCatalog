package org.github.albertyang2007.productlib.common;

import java.io.Serializable;

public class ProductCatalog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865069524488205346L;

	private int productNo;

	private String description;

	private float price;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductCatalog other = (ProductCatalog) obj;
		if (productNo != other.productNo) {
			return false;
		}
		return true;
	}

	private boolean enabled;

	public String toString() {
		return this.productNo + ";" + this.description + ";" + this.price + ";"
				+ this.enabled;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	};
}
