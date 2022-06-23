var fecha = new Date();
var dia = fecha.getDate();
var mes = fecha.getMonth();
var anio = fecha.getFullYear() - 18;
var anioActual = fecha.getFullYear();

$(function () {

    $('#menu-navegacion .navbar-toggler').click(function () {

        $('.boton-menu').toggleClass('cerrar-boton')

    });

    /*Click en un enlace*/

    $('#menu-navegacion .navbar-nav a').click(function () {

        $('.boton-menu').removeClass('cerrar-boton');

        /*Collapse menu*/

        $('#menu-navegacion .navbar-collapse').collapse('hide')

    })

    /*Iniciando el Swiper*/
    var animacion = new Swiper('.swiper-container', {

        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        pagination: {
            el: '.swiper-pagination',
            type: 'bullets',
            clickable: true,
        },
        speed: 500,
        effect: 'fade',
        grabCursor: true,
        loop: true,
        /*autoplay:{
            delay: 3000,
        },*/
        keyboard: true,

    });

    // ------------ VENOBOX ------------- //


    $('.venobox-video').venobox({
        autoplay: true,
        bgcolor: 'rgba(255,255,255,0.4)',
        border: '5px',
        titleattr: 'Sergio Alcazar',
        spinner: 'wave'
    });

    // ------------ COUNTER UP ------------- //

    $('.counter').counterUp();

    // ------------ DATAPICKER ------------- //

    $('#mi-formulario .datepicker').pickadate({
        monthsFull: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
        monthsShort: ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"],
        weekdaysFull: ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"],
        weekdaysShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
        today: "Hoy",
        clear: "Borrar",
        close: "Cerrar",
        firstDay: 1,
        format: "yyyy-mm-dd",
        formatSubmit: "yyyy-mm-dd",
        labelMonthNext: 'Siguiente mes',
        labelMonthPrev: 'Anterior mes',
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        min: true,
    })
    
    $('#form-cuenta .datepicker').pickadate({
        monthsFull: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
        monthsShort: ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"],
        weekdaysFull: ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"],
        weekdaysShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
        today: "Today",
        clear: "Delete",
        close: "Close",
        firstDay: 1,
        format: "yyyy-mm-dd",
        formatSubmit: "yyyy-mm-dd",
        labelMonthNext: 'Siguiente mes',
        labelMonthPrev: 'Anterior mes',
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        max: new Date(anio, mes, dia),
        selectYears: true,
        selectMonths: true
    })
    
    $('#registro-usuario .datepicker').pickadate({
        monthsFull: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
        monthsShort: ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"],
        weekdaysFull: ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"],
        weekdaysShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
        today: "Today",
        clear: "Delete",
        close: "Close",
        firstDay: 1,
        format: "yyyy-mm-dd",
        formatSubmit: "yyyy-mm-dd",
        labelMonthNext: 'Siguiente mes',
        labelMonthPrev: 'Anterior mes',
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        max: new Date(anio, mes, dia),
        selectYears: true,
        selectMonths: true
    })
    
    $('#form-alquilar .datepicker').pickadate({
        monthsFull: ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"],
        monthsShort: ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"],
        weekdaysFull: ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"],
        weekdaysShort: ["dom", "lun", "mar", "mié", "jue", "vie", "sáb"],
        today: "Today",
        clear: "Delete",
        close: "Close",
        firstDay: 1,
        format: "yyyy-mm-dd",
        formatSubmit: "yyyy-mm-dd",
        labelMonthNext: 'Siguiente mes',
        labelMonthPrev: 'Anterior mes',
        labelMonthSelect: 'Selecciona un mes',
        labelYearSelect: 'Selecciona un año',
        min: new Date(anioActual, mes, dia),
        selectYears: true,
        selectMonths: true
    })

    // ------------ DATAPICKER TIMER------------- //

    $('.timepicker').pickatime({
        clear: 'Borrar',
        format: 'HH:i',
        min: [10, 00],
        max: [20, 00],
        interval: 15
    })
    
    $('#form-alquilar .timepicker').pickatime({
        clear: 'Clear',
        format: 'HH:i',
        min: [10, 00],
        max: [20, 00],
        interval: 60
    })

    // ------------ VALIDACION PARSLEY ------------- //

    $('#mi-formulario').parsley({
        errorClass: 'is-invalid text-danger',
        successClass: 'is-valid',
        errorsWrapper: '<ul class="list-unstyled text-danger mb-0 pt-2"></ul>',
        errorTemplate: '<li class="parsley-error"></li>',
        trigger: 'change',
        triggerAfterFailure:'change'
    });
    
    /*$('#formulario-registro').parsley({
        errorClass: 'is-invalid text-danger',
        successClass: 'is-valid',
        errorsWrapper: '<ul class="list-unstyled text-danger mb-0 pt-2"></ul>',
        errorTemplate: '<li class="parsley-error"></li>',
        trigger: 'change',
        triggerAfterFailure:'change'
    });*/

    // ------------ STICKIT ------------- //

    $('#menu-navegacion').stickit({
        className: 'menu-fijo'
    });

    // ------------ PageToScroll ------------- //

    $("#menu-principal a").mPageScroll2id({
        offset: 70,
        highlightClass: "active"
    });

    // ------------ Añadir foto -------------- //

	$('#file').change(function(){
	
        filePreview(this);
        
    });
    
    /*$('#myOptions').change(function() {
	    var val = $("#myOptions option:selected").text();
	    calcularPrecio(val)
	});*/
	
	var precio = $("#precioAlquiler .precio").text();
	var preciototal = precio.toString()
	$("#precioInput").val(preciototal);

})

function filePreview(input){
    if(input.files && input.files[0]){
        var reader = new FileReader();
        reader.onload = function(e){
            $('.image-preview').html("<img src='"+e.target.result+"' class='img-fluid' max-width=500px/>")
        }
        reader.readAsDataURL(input.files[0])
    }
}

function formatoFecha(fecha, formato) {
    const map = {
        dd: fecha.getDate(),
        MM: fecha.getMonth() + 1,
        yy: fecha.getFullYear().toString().slice(-2),
        yyyy: fecha.getFullYear() - 18 
    }

    return formato.replace(/dd|MM|yyyy/gi, matched => map[matched]);
}

var i = 0;
var precio = null;
function calcularPrecio(horas){
	
	i ++;
	var numero = null;	
	if(i == 1){
		precio = $("#precioAlquiler .precio").text();
		precio = precio / 2;
	}
	
	var total = parseInt(horas) * precio;
	var preciototal = total.toString()
	
	$("#precioAlquiler .precio").text(preciototal);
	$("#precioInput").val(preciototal);
}


