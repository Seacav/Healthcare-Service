var renderInfo = (id) => {
	const url=`getPatient?id=${id}`;
	var promise = fetch(url);
	promise
	.then(data=>{
		return data.json()
	})
	.then(res=>{
		var divContainer = document.getElementById("secondColumn");
		divContainer.innerHTML = "";
		var p = document.createElement('p');
		p.className = 'infoLine';
		p.textContent = 'Insurance Number: ' + res.insNumber;
		divContainer.appendChild(p)
		
		p = document.createElement('p');
		p.className = 'infoLine'
		p.textContent = 'Diagnosis: ' + res.diagnosis;
		divContainer.appendChild(p);
		
		p = document.createElement('a');
		p.innerHTML = 'Show appointments <i class="fas fa-arrow-right"></i>';
		p.href = `list-appointments?id=${res.id}`
		divContainer.appendChild(p)
		divContainer.style.display = 'inline-block';
	});
}

window.onclick = function(event){
    if (event.target.className !== 'patientName' && 
    		event.target.id !== 'secondColumn') {
    	document.getElementById("secondColumn").style.display = 'none';
    }
}