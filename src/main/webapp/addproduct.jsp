<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add products</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="dist/css/bootstrap.css">
<link href="dist/css/signin.css" rel="stylesheet">
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">ProductCatalog Demo</a>
        </div>
        <div class="navbar-collapse collapse">
          <form class="navbar-form navbar-right" role="form" action="addproduct.jsp">
        
            <button type="submit" class="btn btn-lg btn-primary" >Add a Product</button>
          </form>
                    <form class="navbar-form navbar-right" role="form" action="showall.do">
       
            <button type="submit" class="btn btn-lg btn-primary" >Browse All Products</button>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </div>
	<div class="container">
		<form class="form-signin" method="POST" action="./addproduct.do">
			<h2 class="form-signin-heading" align="center">Admin</h2>
			<label>ProductNo:</label>
			<p></p>
			<div>
				<input type="text" class="form-control" placeholder="productNo"
					id="productID" name="productID" required autofocus>
			</div>
			<p></p>
			<label>Description:</label>
			<p></p>
			<div>
				<input type="text" class="form-control" placeholder="description"
					id="description" name="description" required autofocus>
			</div>
			<p></p>
			<label>Price:</label>
			<p></p>
			<div>
				<input type="text" class="form-control" placeholder="price"
					id="price" name="price" required autofocus>
			</div>
			<p></p>
			<div>
			<label>Enabled:</label>
		
				 <input type="checkbox" 
					id="enabled" name="enabled" value="true" autofocus>
				<p></p>
	        </div>
	
	
		    

			
		   <button class="btn btn-lg btn-primary" type="submit" id="submit">Add</button>
			<button class="btn btn-lg btn-primary" type="reset" id="reset">Reset</button>
		</form>


	</div>
	
</body>
</html>