function onSubmitButtonClick(event) {
    const email = document.getElementById("email");
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (!emailRegex.test(email.value)) {
        event.preventDefault();
        alert("Il formato dell'email non è corretto.")
    }
}


function ready() {
    const submitButton = document.getElementById("submitButton");
    submitButton.addEventListener("click", onSubmitButtonClick);
}

document.addEventListener("DOMContentLoaded", ready)