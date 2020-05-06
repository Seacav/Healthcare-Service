document.getElementById('subBtn').onclick = function(e) {
	var inputs = document.querySelectorAll('#regForm input');    

    for (var i = 0; i < inputs.length; i++) {
    	if (inputs[i].value === ""){
    		inputs[i].style.borderColor = "red";
    		inputs[i].addEventListener('input', function(e) {
    			if (e.target.value===""){
    				e.target.style.borderColor = "red";
    			} else {
    				e.target.style.borderColor = "#079992";
    			}
    		});
    		e.preventDefault();
    	}
    }
}