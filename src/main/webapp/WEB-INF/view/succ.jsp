<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.github.albertyang2007.productlib.common.ProductCatalog"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="dist/css/bootstrap.css">
<link href="dist/css/signin.css" rel="stylesheet">
<title>Product Catalog</title>
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" >ProductCatalog Demo</a>
			</div>
			<div class="navbar-collapse collapse">
				<form class="navbar-form navbar-right" role="form"
					action="addproduct.jsp">

					<button type="submit" class="btn btn-lg btn-primary">Add
						a Product</button>
				</form>
				<form class="navbar-form navbar-right" role="form"
					action="showall.do">

					<button type="submit" class="btn btn-lg btn-primary">Browse
						All Products</button>
				</form>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>


	<div class="container">

		<div class="span12">
			<h2 class="form-signin-heading" align="center" id="resultInfo">
				<c:out value="${info}" />
			</h2>
			<table class="table">
				<thead>
					<tr>
						<td><h3>ProductNo</h3></td>
						<td><h3>Description</h3></td>
						<td><h3>Price</h3></td>
						<td><h3>Enabled</h3></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${products}" varStatus="productvar">
						<tr class="success">
							<td><c:out value="${product.productNo}" /></td>
							<td><c:out value="${product.description}" /></td>
							<td>&yen; <c:out value="${product.price}" /></td>


							<td align=center ><c:choose>
									<c:when test="${product.enabled}">
										<img src="image/yes.jpg" />
									</c:when>
									<c:otherwise>
										<img src="image/no.jpg" />
									</c:otherwise>


								</c:choose></td>

						</tr>
					</c:forEach>

				</tbody>


			</table>


		</div>
	</div>

</body>
</html>