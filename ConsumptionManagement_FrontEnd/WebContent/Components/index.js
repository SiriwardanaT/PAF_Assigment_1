//validate form
function formValidation() {

	if ($("#AccNo").val().trim() == '') {
		return "Please Enter Acc No";
	}
	if ($("#Cmr").val().trim() == '') {
		return "Please Enter Current meter reading";
	}
	if ($("#Uprice").val().trim() == '') {
		return "Please Enter Unit price";
	}
	var Uprice = $("#Uprice").val().trim();
	if(!$.isNumeric(Uprice)){
		return "Insert a numerical value for Unit Price.";
	}
	var crm = $("#Cmr").val().trim();
	if(!$.isNumeric(crm)){
		return "Insert a numerical value for Current Reading.";
	}
	return true;

}



$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	var status = formValidation();
	if (status != true) {
		$("#alertError").show();
		$("#alertError").text(status);
	}
	else {
		//submit
		var type = ($("#hiddenID").val().trim() == "" ? "POST" : "PUT")
		console.log(type)
		$("#alertError").hide();
		$.ajax({
			url: "ConsumtionAPI",
			type: type,
			data: $("#Consumptionform").serialize(),
			datatype: "text",
			complete: function(response, status) {
				console.log(response.responseText)
				OntemSave(response.responseText, status);
			}

		});
	}

});

//update
$(document).on("click", "#btnUpdate", function() {
	$("#Cmr").prop('disabled', true);
	$("#AccNo").prop('disabled', true);
	$("#hiddenID").val($(this).data("cid"));
	$("#date").prop('disabled', true);
	$("#date").prop("type", "text");
	$("#date").val("6/5/2021");
	$("#status").val($(this).closest("tr").find("td:eq(7)").text());
	$("#AccNo").val($(this).closest("tr").find("td:eq(6)").text());
	$("#Cmr").val($(this).closest("tr").find("td:eq(5)").text());
	$("#Uprice").val($(this).closest("tr").find("td:eq(3)").text());
})

$(document).on("click", "#btnRemove", function() {
	$.ajax({

		url: "ConsumtionAPI",
		type: "DELETE",
		data: "Cid=" + $(this).data("cid"),
		datatype: "text",
		complete: function(response, status) {
			console.log(response.responseText)
			OnConsumptionDelete(response.responseText, status);
		}

	});

})

function OntemSave(response, status) {
	console.log("hello", status);
	var resultTest = JSON.parse(response);
	console.log(resultTest);
	if (resultTest.status = "success") {
		if (resultTest.Mstatus != "error") {
			$("#alertSuccess").show();
			$("#alertSuccess").text("Successfully Saved Data");
			$("#table-grid").html(resultTest.data);
		}
		else {
			$("#alertError").show();
			$("#alertError").text(resultTest.data);
		}
	}
	else {
		$("#alertError").show();
		$("#alertError").text("Something Went Wrong");
	}

}

function OnConsumptionDelete(response) {
	var resultTest = JSON.parse(response);
	if (resultTest.status = "success") {
		$("#alertSuccess").show();
		$("#alertSuccess").text("Successfully deleted.");
		$("#table-grid").html(resultTest.data);
	}
	else {
		$("#alertError").show();
		$("#alertError").text("Something Went Wrong");
	}
}

