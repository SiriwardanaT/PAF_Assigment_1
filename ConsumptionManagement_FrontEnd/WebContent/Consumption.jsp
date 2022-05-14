<%@page import="com.ConsumptionService"%>
<html>
<head>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/index.js"></script>
</head>
<body>
	<div class="m-3">
		<h1>Consumption Management</h1>
		<form id="Consumptionform" method="post" action="Consumption.jsp">
			<div class="form-section">
				<div class="input-group input-group-sm mb-3  w-50">
					<div class="input-group-prepend">
						<span class="input-group-text" id="lblName">Year: </span>
					</div>
					<select id="AccNo" name="AccNo" class="form-control">
						<option value="">--Select Meter Account--</option>
						<option value="AC1000">AC1000</option>
						<option value="AC2000">AC2000</option>
						<option value="AC3000">AC3000</option>
						<option value="AC4000">AC4000</option>
						<option value="AC5000">AC5000</option>
					</select>
				</div>


			</div>
			<div class="form-section">
				<div class="input-group input-group-sm mb-3 w-50">
					<div class="input-group-prepend">
						<span class="input-group-text" id="lblName">Current Meter
							Reading: </span>
					</div>
					<input id="Cmr" type="text" name="Cmr" class="form-control">
				</div>
			</div>
			<div class="form-section">
				<div class="input-group input-group-sm mb-3 w-50">
					<div class="input-group-prepend">
						<span class="input-group-text" id="lblName">Unit Price: </span>
					</div>
					<input id="Uprice" type="text" name="Uprice" class="form-control">
				</div>
			</div>
			<div class="input-group input-group-sm mb-3  w-50">
				<div class="input-group-prepend">
					<span class="input-group-text" id="lblName">Status: </span>
				</div>
				<select id="status" name="status" class="form-control">
					<option value="">--Select Status--</option>
					<option value="1">Active</option>
					<option value="0">InActive</option>
				</select>
			</div>
			<div id="dateSegement" class="input-group input-group-sm mb-3  w-50">
				<div class="input-group-prepend">
					<span class="input-group-text" id="lblName">Date: </span>
				</div>
				<input id="date" name="date" type="date">
			</div>

			<input id="btnSave" type="button" value="Save"
				class="btn btn-primary"> <input id="hiddenID"
				name="hiddenID" type="hidden" value="">
		</form>
		<div id="alertSuccess" class="alert alert-success w-50"></div>
		<div id="alertError" class="alert alert-danger w-50"></div>
	</div>
	<div class="table-grid" id="table-grid">
		<%= ConsumptionService.getAllConsumptions()%>
	</div>
</body>
</html>