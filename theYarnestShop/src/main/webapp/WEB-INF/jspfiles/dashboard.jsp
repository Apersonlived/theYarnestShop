<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" href="${contextPath}/css/admin-dashboard.css">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

	<div class="container">
		<!-- Side Navigation -->
		<div class="sidebar">
			<h2>The Yarnest Shop</h2>
			<p>Nest of Yarns</p>
			<ul class="nav">
				<li class="active"><a href="#"><span class="icon">📊</span>
						Dashboard</a></li>
				<li><a href="${contextPath}/Products"><span class="icon">🧶</span>
						Products</a></li>
				<li><a href="${contextPath}/UserList"><span class="icon">👥</span>
						User List</a></li>
				<li><a href="#"><span class="icon">🔍</span> Search</a></li>
			</ul>
		</div>

		<!-- Main Content -->
		<div class="content">
			<!-- Top Cards -->
			<div class="header">
				<div class="info-box">
					<h3>Monthly Sales</h3>
					<p>$${monthlySales}</p>
				</div>
				<div class="info-box">
					<h3>Total Users</h3>
					<p>${totalUsers}</p>
				</div>
			</div>

			<!-- Dashboard Content -->
			<div class="dashboard-content">
				<!-- Low Stock Section -->
				<div class="card stock-card">
					<h3>Low Stock Levels</h3>
					<table>
						<thead>
							<tr>
								<th>Product Name</th>
								<th>Stock</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lowStockProducts}" var="product">
								<tr>
									<td>${product.product_name}</td>
									<td class="stock-value">${product.stock}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- Bestsellers Section -->
				<div class="card bestsellers-card">
					<h3>Bestsellers</h3>
					<ul class="bestsellers-list">
						<c:forEach items="${bestsellers}" var="entry">
							<li><span class="product-name">${entry.key}</span> <span
								class="sold-count">${entry.value} sold</span></li>
						</c:forEach>
					</ul>
				</div>

				<!-- Yearly Revenue Chart -->
				<div class="card chart-card">
					<h3>Yearly Revenue</h3>
					<div class="chart-container">
						<canvas id="revenueChart"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
        // Initialize revenue chart
        const ctx = document.getElementById('revenueChart').getContext('2d');
        const revenueData = {
            labels: [<c:forEach items="${yearlyRevenue}" var="entry">"${entry.key}",</c:forEach>],
            datasets: [{
                label: 'Revenue ($)',
                data: [<c:forEach items="${yearlyRevenue}" var="entry">${entry.value},</c:forEach>],
                backgroundColor: 'rgba(178, 65, 105, 0.2)',
                borderColor: 'rgba(178, 65, 105, 1)',
                borderWidth: 2
            }]
        };
        
        new Chart(ctx, {
            type: 'bar',
            data: revenueData,
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</body>
</html>