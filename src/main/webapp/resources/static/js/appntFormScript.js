const addTimeButton = document.querySelector('#addTime');
const dates = document.querySelector('#dates');
let counter = 1;

addTimeButton.addEventListener('click', function (e) {
    const dateDiv = document.createElement('div');
    dateDiv.className = "date";
    const dateEl = document.createElement('input');
    dateEl.name = 'treatTime';
    dateEl.type = 'time';

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

