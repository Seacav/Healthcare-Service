var filterName = '?filterName=' + $('#filter-by').val();

function template(data){
	if (data.length === 0){
    	return "No events planned.";
    }
	var table = $('<table>').attr('id', 'tableEvents')
	var thead = $('<thead>')
	
	var headRow = $('<tr>');
	$('<th>', {text: 'Name'}).appendTo(headRow);
	$('<th>', {text: 'Diagnosis'}).appendTo(headRow);
	$('<th>', {text: 'Treatment'}).appendTo(headRow);
	$('<th>', {text: 'Status'}).appendTo(headRow);
	$('<th>', {text: 'Time'}).appendTo(headRow);
	$('<th>', {text: 'Options'}).appendTo(headRow);
	
	thead.append(headRow);
	table.append(thead);
	
	var tbody = $('<tbody>')
	
	$.each(data, function(index, item){
		var tRow = $('<tr>');
		var date = moment(item.date);
		$('<td>', {text: item.appointment.patient.lastName+' '+item.appointment.patient.firstName}).appendTo(tRow);
		$('<td>', {text: item.appointment.patient.diagnosis}).appendTo(tRow);
		if (item.appointment.dosage===null){
			$('<td>', {text: item.appointment.treatment.name}).appendTo(tRow);
		} else {
			$('<td>', {text: item.appointment.treatment.name+' '+item.appointment.dosage}).appendTo(tRow);
		}
		$('<td>', {text: item.status}).appendTo(tRow);
		$('<td>', {text: date.format('HH:mm DD.MM.YY')}).appendTo(tRow);
		var td = $('<td>', {class: 'options'});
		if (item.status==='SCHEDULED'){
			$('<span>', {
				html: '<i class="fas fa-check"></i></span>',
				click: function(){
						var token = $("meta[name='_csrf']").attr("content");
						$.ajax({
					        type:    "POST",
					        url:     "/Rehab/completeEvent",
					        headers: {"X-CSRF-TOKEN": token},
					        data:    {
					            "id": item.id,
					        },
					        success: function(res) {
					            console.log('Success');
					            paginate(`?filterName=${$('#filter-by').val()}&patientName=${$('#patientFilter').val()}`);
					        },
					        error: function(res) {
					        	alert('Failed to process');
				            }
					    });
				},
			}).appendTo(td);
			$('<span>', {
				html: '<i class="fas fa-times"></i>',
				click: function(){
					cancelEvent(item.id);
			},
			}).appendTo(td);
		} else if (item.status==='CANCELLED'){
			$('<span>', {
				html: '<i class="fas fa-info-circle"></i>',
				click: function(){
					$.ajax({
				        type:    "GET",
				        url:     "/Rehab/getCommentary",
				        data:    {
				            "eventId": item.id,
				        },
				        success: function(res) {
				        	$('#responseCommentary').show();
				        	$('#reasonText').text(res.commentary);
				        	$('#closeResponseModal').click(function(){
				        		$('#responseCommentary').hide();
				        	});
				        },
				        error: function(res) {
				        	alert('Failed to process');
			            }
				    });
			},
			}).appendTo(td);
		}
		tRow.append(td);
		tbody.append(tRow);
    });
	
	table.append(tbody);
	
	return table;
}

function log(content) {
  window.console && console.log(content);
}

function paginate(filterBy){
  var container = $('#pagination-bar');
  container.pagination({
    dataSource: 'http://localhost:8080/Rehab/listAllEvents'+filterBy,
    locator: 'items',
    pageSize: 20,
    totalNumberLocator: function(response){
    	return response.length;
    },
    callback: function(data, pagination) {
	    var html = template(data);
	    $('#pagination-data-container').html(html);
    }
  });
};

$(document).ready(function(){
	$('#filter-by').change(function(){
		filterName = `?filterName=${$(this).val()}&patientName=${$('#patientFilter').val()}`;
		paginate(filterName);
	});
	
	$('#patientFilter').keyup(function(){
		filterName = `?filterName=${$('#filter-by').val()}&patientName=${$(this).val()}`;
		paginate(filterName)
	})
})

paginate(filterName);



// Commentary modalbox
function cancelEvent(id){
	$('#cancelEvent').unbind();
	var modal = $('#commentaryModal');
	modal.show();
	$("#commentaryText").val('');
	$('#closeModal').click(function(){
		modal.hide();
	});
	$('#cancelEvent').click(function(){
		var token = $("meta[name='_csrf']").attr("content");
		var text = $("#commentaryText").val();
		$.ajax({
	        type:    "POST",
	        url:     "/Rehab/cancelEvent",
	        headers: {"X-CSRF-TOKEN": token},
	        data:    {
	            "id": id,
	            "commentary": text,
	        },
	        success: function(res) {
	        	modal.hide();
	            paginate(`?filterName=${$('#filter-by').val()}&patientName=${$('#patientFilter').val()}`);
	        },
	        error: function(res) {
	        	modal.hide();
	        	paginate(`?filterName=${$('#filter-by').val()}&patientName=${$('#patientFilter').val()}`);
	        	alert('Failed to process');
            }
	    });
	});
};

$(window).click(function(e) {
	if (e.target.id === "commentaryModal"){
		$('#commentaryModal').hide();
	} else if (e.target.id === "responseCommentary"){
		$('#responseCommentary').hide();
	}
});
