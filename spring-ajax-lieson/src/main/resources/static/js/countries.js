var buttonLoad, buttonAdd, buttonUpdate, buttonDelete;
var selectedCountryId = null;

$(document).ready(function () {
    // Initialize buttons
    buttonLoad = $("#buttonLoadCountries");
    buttonAdd = $("#buttonAdd");
    buttonUpdate = $("#buttonUpdate");
    buttonDelete = $("#buttonDelete");
    var countryList = $("#dropDownCountries");

    // Load countries on button click
    buttonLoad.click(function () {
        loadCountries();
    });

    // Add country on button click
    buttonAdd.click(function () {
        addCountry();
    });

    // Update country on button click
    buttonUpdate.click(function () {
        updateCountry();
    });

    // Delete country on button click
    buttonDelete.click(function () {
        deleteCountry();
    });

    // Handle selecting a country from the dropdown
    countryList.change(function () {
        selectedCountryId = $(this).val();
        $("#countryName").val($("#dropDownCountries option:selected").text());
        // Enable update and delete buttons if a country is selected
        if (selectedCountryId) {
            buttonUpdate.prop("disabled", false);
            buttonDelete.prop("disabled", false);
        }
    });
});

// Function to load countries (GET)
function loadCountries() {
    var url = "/api/countries/list";

    $.ajax({
        url: url,
        type: "GET",
        success: function (responseJson) {
            var countryList = $("#dropDownCountries");
            countryList.empty(); // Clear existing countries
            $.each(responseJson, function (index, country) {
                countryList.append($('<option>').val(country.id).text(country.name));
            });
            buttonUpdate.prop("disabled", true);  // Disable update until a country is selected
            buttonDelete.prop("disabled", true);  // Disable delete until a country is selected
        },
        error: function (xhr) {
            alert("Error loading countries: " + xhr.responseText);
        }
    });
}

// Function to add a new country (POST)
function addCountry() {
    var url = "/api/countries/create";
    var countryName = $("#countryName").val();

    if (countryName.trim() === "") {
        alert("Country name cannot be empty");
        return;
    }

    var countryDto = {
        name: countryName
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(countryDto),
        success: function (response) {
            alert("Country added successfully!");
            loadCountries(); // Reload the country list after adding
            $("#countryName").val(""); // Clear input field
        },
        error: function (xhr) {
            alert("Error adding country: " + xhr.responseText);
        }
    });
}

// Function to update a selected country (PUT)
function updateCountry() {
    if (!selectedCountryId) {
        alert("Please select a country to update.");
        return;
    }

    var url = "/api/countries/update/" + selectedCountryId;
    var countryName = $("#countryName").val();

    if (countryName.trim() === "") {
        alert("Country name cannot be empty");
        return;
    }

    var countryDto = {
        name: countryName
    };

    $.ajax({
        url: url,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(countryDto),
        success: function (response) {
            alert("Country updated successfully!");
            loadCountries(); // Reload the country list after updating
            $("#countryName").val(""); // Clear input field
            buttonUpdate.prop("disabled", true); // Disable update button
            buttonDelete.prop("disabled", true); // Disable delete button
        },
        error: function (xhr) {
            alert("Error updating country: " + xhr.responseText);
        }
    });
}

// Function to delete a selected country (DELETE)
function deleteCountry() {
    if (!selectedCountryId) {
        alert("Please select a country to delete.");
        return;
    }

    var url = "/api/countries/delete/" + selectedCountryId;

    $.ajax({
        url: url,
        type: "DELETE",
        success: function (response) {
            alert("Country deleted successfully!");
            loadCountries(); // Reload the country list after deleting
            $("#countryName").val(""); // Clear input field
            buttonUpdate.prop("disabled", true); // Disable update button
            buttonDelete.prop("disabled", true); // Disable delete button
        },
        error: function (xhr) {
            alert("Error deleting country: " + xhr.responseText);
        }
    });
}
