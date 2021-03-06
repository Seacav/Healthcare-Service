const modalDosage = document.getElementById('dosageModal');
const showDsgModal = document.getElementById('dosageBtn');
const closeDsgModal = document.getElementById('closeDsg');

const modalPattern = document.getElementById('patternModal');
const showPtrnModal = document.getElementById('patternBtn');
const closePtrnModal = document.getElementById('closePtrn');

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const url=`/Rehab/activeEvents?id=${urlParams.get('appointmentId')}`;
var promise = fetch(url);

promise = promise
.then(data=>{
	return data.json()
});
	
// Listeners for dosage modal box
if (showDsgModal !== null) {
	//Code to check active(i.e. scheduled) events in appointment. If there are no active events then
	//change dosage button will not be shown
	promise
	.then(res=>{
		if (res===0){
			showDsgModal.style.display = 'none';
		} else {
			showDsgModal.style.display = 'inline-block';
			showDsgModal.onclick = function() {
			    modalDosage.style.display = 'block';
			}
		}
	})
}

closeDsgModal.onclick = function() {
    modalDosage.style.display = 'none';
};

//Listener for pattern modal box
if (showPtrnModal !== null) {
	//Code to check active(i.e. scheduled) events in appointment. If there are no active events then
	//change dosage button will not be shown
	promise
	.then(res=>{
		if (res===0){
			showPtrnModal.style.display = 'none';
		} else {
			showPtrnModal.style.display = 'inline-block';
			showPtrnModal.onclick = function(){
				modalPattern.style.display = 'block';
			}
		}
	})
};

closePtrnModal.onclick = function() {
    modalPattern.style.display = 'none';
}

window.onclick = function(event){
    if (event.target == modalDosage) {
        modalDosage.style.display = 'none';
    } else if (event.target == modalPattern) {
        modalPattern.style.display = 'none';
    }
}

//Pattern modal content
const addTimeButton = document.querySelector('#addTime');
const dates = document.querySelector('#dates');
let counter = 1;

addTimeButton.addEventListener('click', function (e) {
    const dateDiv = document.createElement('div');
    dateDiv.className = "date";
    const dateEl = document.createElement('input');
    dateEl.type = 'time';
    dateEl.name = 'treatTime[]';

    const delButton = document.createElement('button');
    delButton.innerHTML = '<i class="far fa-times-circle"></i>';
    delButton.className = 'delButton';
    delButton.addEventListener('click', function(){
        dateDiv.parentNode.removeChild(dateDiv);
        counter -= 1;
        checkCounter();
    })

    dateDiv.appendChild(dateEl);
    dateDiv.appendChild(delButton);
    dates.appendChild(dateDiv);

    counter += 1;
    checkCounter();
})

const checkCounter = function(){
    counter>=3 ? addTimeButton.style.display = 'none' : addTimeButton.style.display = 'inline-block';
}



