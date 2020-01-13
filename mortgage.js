function c(){
	var p = parseInt(document.getElementById("p").value);
	var y = parseInt(document.getElementById("y").value);
	var months = y*12;
	var i = parseInt(document.getElementById("i").value);
	var interest = (i / 100)/12;
	var r = (1 + interest);
	var m1 = interest * (Math.pow(r, months));
	var m2 = (Math.pow(r, months)) - 1;
	var mortgage =  p * (m1 / m2);
	var res = mortgage.toFixed(2);
	document.getElementById("r").value = res;
	return res;
}

function remainingBalanceSet() {
	var ret = remainingBalance();
	document.getElementById("k").innerHTML = ret[0];
}

function remainingBalance() {
	var principal = parseInt(document.getElementById("p").value);
	var y = parseInt(document.getElementById("y").value);
	var i = parseInt(document.getElementById("i").value);
	var interest = (i / 100)/12;
	var r = (1 + interest);
	var balance;
	var months = y * 12;
	var result = "";
	var Totalpayment = [];
	var Remainingbalance = [];
	var totalMortgage = c();
	var monthList = [];
	//var b = document.getElementById("k"); // .value = "After " + i + " payment your mortgage is" + balance
	for (var i = 1; i <= months; i++) {
		
		balance = (principal * ((Math.pow(r, months)) - (Math.pow(r, i))))
				/ ((Math.pow(r, months)) - 1);
				balance = balance.toFixed(2);;
		result = result + "After " + i + " payment your remaining mortgage is " + balance + "<br/><br />";
		Totalpayment.push(totalMortgage*i);
		Remainingbalance.push(balance);
		monthList.push(i);
	}
	return [result, Totalpayment, Remainingbalance, monthList];
}

function chartJS() {
	var dataset = remainingBalance();
	var barChartData = {
		labels : dataset[3],
		datasets : [
			{
				label: "Total Payment",
				fillColor: "rgba(151,187,205,0.5)",
				strokeColor: window.chartColors.red,
				highlightFill: "rgba(151,187,205,0.75)",
				highlightStroke: "rgba(151,187,205,1)",
				fill: false,
				data : dataset[1],
			},
			{
				label: "Remaining Balance",
				strokeColor: window.chartColors.blue,
				fill: false,
				data : dataset[2],
			}
		]
	}

	var ctx = document.getElementById("canvas").getContext("2d");

	var chart = new Chart(ctx).Line(barChartData, {
		responsive : true,
		animation: true,
		barValueSpacing : 5,
		barDatasetSpacing : 5,
		tooltipFillColor: "rgba(0,0,0,0.8)",                
		tooltipTemplate: function(valuesObject){
			console.log(valuesObject);
			// do different things here based on whatever you want;
			var label = valuesObject.label;
			var objLen = label.length;
			var string = label.substring(0,objLen-2);
			console.log(string);
			return "Order nr: " + string;
			}
	});
}

