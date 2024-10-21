var buttonLoad, buttonAdd, buttonUpdate, buttonDelete;
var selectedStateId = null;
var selectedCountryId = null;

$(document).ready(function () {
    // Initialize buttons
    buttonLoad = $("#buttonLoadCountries");
    buttonAdd = $("#buttonAddState");
    buttonUpdate = $("#buttonUpdateState");
    buttonDelete = $("#buttonDeleteState");
    var stateList = $("#dropDownStates");
    var countryList = $("#dropDownCountries");

    // Load countries on button click
    buttonLoad.click(function () {
        loadCountries();
    });

    // Add state on button click
    buttonAdd.click(function () {
        addState();
    });

    // Update state on button click
    buttonUpdate.click(function () {
        updateState();
    });

    // Delete state on button click
    buttonDelete.click(function () {
        deleteState();
    });

    // Handle selecting a country from the dropdown
    countryList.change(function () {
        selectedCountryId = $(this).val();
        // Reset state-related fields
        $("#dropDownStates").empty();
        $("#fieldStateName").val("");
        buttonUpdate.prop("disabled", true); // Disable update button
        buttonDelete.prop("disabled", true); // Disable delete button

        if (selectedCountryId) {
            loadStates(selectedCountryId); // Load states for the selected country
        }
    });

    // Handle selecting a state from the dropdown
    stateList.change(function () {
        selectedStateId = $(this).val();
        $("#fieldStateName").val($("#dropDownStates option:selected").text());
        // Enable update and delete buttons if a state is selected
        if (selectedStateId) {
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
                countryList.append($('<option>').val(country.id).text(country.name).appendTo(countryList));
            });
            // Reset buttons
            buttonUpdate.prop("disabled", true);
            buttonDelete.prop("disabled", true);
        },
        error: function (xhr) {
            alert("Error loading countries: " + xhr.status + " - " + xhr.responseText);
        }
    });
}

// Function to load states based on selected country (GET)
function loadStates(countryId) {
    var url = "/api/states/country/" + countryId;

    $.ajax({
        url: url,
        type: "GET",
        success: function (responseJson) {
            var stateList = $("#dropDownStates");
            stateList.empty(); // Clear existing states
            $.each(responseJson, function (index, state) {
                stateList.append($('<option>').val(state.id).text(state.name));
            });

            buttonUpdate.prop("disabled", true);  // Disable update until a state is selected
            buttonDelete.prop("disabled", true);  // Disable delete until a state is selected
        },
        error: function (xhr) {
            alert("Error loading states: " + xhr.status + " - " + xhr.responseText);
        }
    });
}

// Function to add a new state (POST)
function addState() {
    if (!selectedCountryId) {
        alert("Please select a country first.");
        return;
    }

    var url = "/api/states/create";
    var stateName = $("#fieldStateName").val();

    if (stateName.trim() === "") {
        alert("State name cannot be empty");
        return;
    }

    var stateDto = {
        name: stateName,
        country: {
            id: selectedCountryId
        }
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(stateDto),
        success: function (response) {
            alert("State added successfully.");
            loadStates(selectedCountryId); // Refresh the states
        },
        error: function (xhr) {
            alert("Error adding state: " + xhr.status + " - " + xhr.responseText);
        }
    });
}

// Function to update an existing state (PUT)
function updateState() {
    if (!selectedStateId || !selectedCountryId) {
        alert("Please select a state to update.");
        return;
    }

    var url = "/api/states/update/" + selectedStateId;
    var stateName = $("#fieldStateName").val();

    if (stateName.trim() === "") {
        alert("State name cannot be empty");
        return;
    }

    var stateDto = {
        id: selectedStateId,
        name: stateName,
        country: {
            id: selectedCountryId
        }
    };

    $.ajax({
        url: url,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(stateDto),
        success: function (response) {
            alert("State updated successfully.");
            loadStates(selectedCountryId); // Refresh the states
        },
        error: function (xhr) {
            alert("Error updating state: " + xhr.status + " - " + xhr.responseText);
        }
    });
}

// Function to delete a state (DELETE)
function deleteState() {
    if (!selectedStateId) {
        alert("Please select a state to delete.");
        return;
    }

    var url = "/api/states/delete/" + selectedStateId;

    $.ajax({
        url: url,
        type: "DELETE",
        success: function (response) {
            alert("State deleted successfully.");
            loadStates(selectedCountryId); // Refresh the states
        },
        error: function (xhr) {
            alert("Error deleting state: " + xhr.status + " - " + xhr.responseText);
        }
    });
}
