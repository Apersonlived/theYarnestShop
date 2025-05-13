<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id=header>
	<header>
		<div>
			<h1>The Yarnest Shop</h1>
			<h6>Nest of Yarns</h6>
		</div>
		<div class="sticky-header">
			<nav>
				<ul>
					<li><a href="${pageContext.request.contextPath}/Home">Home</a></li>
					<li><a href="${pageContext.request.contextPath}/Products">Products</a></li>
					<li><a href="${pageContext.request.contextPath}/Contact">Contact
							Us</a></li>
					<li><a href="${pageContext.request.contextPath}/About">About
							Us</a></li>
				</ul>
			</nav>
			<div class=header-right>
			<div class=search-profile-container>
			<div class="search-bar">
				<input type="text" id="searchInput" placeholder="Search Crochet...."
					name="search">
				<button onclick="searchProducts()">
					<i class="fa fa-search" style='font-size:26px;'></i>
				</button>
			</div>
			<div class=profile>
				<a href="${pageContext.request.contextPath}/Profile" class="profile-icon">
  					<i class="fa fa-user-circle" style='font-size:36px;'></i>
				</a>

			</div>
			</div>
			</div>
		</div>
	</header>
</div>