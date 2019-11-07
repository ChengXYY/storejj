$(function () {

    $("body").on("change", "#option-type", function () {
        var type = $("#option-type option:selected").val();
        switch (type){
            case "2":
                $("#option-list").html(getRadio());
                break;
            case "3":
                $("#option-list").html(getCheckbox());
                break;
            default:
                $("#option-list").html(getInput());
                break;
        }
    });

    $("body").on("click", ".add-radio", function () {
        $("#option-list > .question-option").append(getRadioItem());
    });

    $("body").on("click", ".add-checkbox", function () {
        $("#option-list > .question-option").append(getCheckboxItem());
    });

    $("body").on("click", ".close-option", function () {
        $(this).parent().remove();
    });

    function getInput() {
        return $("#questionTpl > div[data-option='1']").prop("outerHTML");
    }

    function getRadio() {
        return $("#questionTpl > div[data-option='2']").prop("outerHTML");
    }

    function getCheckbox() {
        return $("#questionTpl > div[data-option='3']").prop("outerHTML");
    }

    function getRadioItem() {
        return $("#questionTpl .option-radio").prop("outerHTML");
    }

    function getCheckboxItem() {
        return $("#questionTpl .option-checkbox").prop("outerHTML");
    }

});