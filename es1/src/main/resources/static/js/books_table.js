function addRow(booksTableBody, title, year, publisher) {
    const newRow = booksTableBody.insertRow();

    const numberCell = newRow.insertCell(0);
    const titleCell = newRow.insertCell(1);
    const yearCell = newRow.insertCell(2);
    const publisherCell = newRow.insertCell(3);

    numberCell.textContent = booksTableBody.getElementsByTagName("tr").length;
    titleCell.textContent = title;
    yearCell.textContent = year;
    publisherCell.textContent = publisher;
}


function updateNumberBooks(rows, index) {
    for (let i = index; i < rows.length; ++i) {
        const bookNumber = rows[i].getElementsByTagName("td")[0];
        bookNumber.textContent -= 1;
    }
}


function removeRow(booksTableBody, index) {
    const rows = booksTableBody.querySelectorAll("tr");

    if (index >= 0 && index < rows.length) {
        rows[index].remove();
        updateNumberBooks(rows, index);
    }
    else
        alert("Numero Libro non valido.");
}


function onAddBookButtonClick(event, booksTableBody) {
    event.preventDefault();

    const bookTitle = document.getElementById("bookTitle");
    const bookYear = document.getElementById("bookYear");
    const bookPublisher = document.getElementById("bookPublisher");

    if (!bookTitle.value || !bookYear.value || !bookPublisher.value)
        alert("Tutti i campi per l'aggiunta del libro devono essere compilati.");
    else {
        addRow(booksTableBody, bookTitle.value, bookYear.value, bookPublisher.value);

        bookTitle.value = "";
        bookYear.value = "";
        bookPublisher.value = "";
    }
}


function onRemoveBookButtonClick(event, booksTableBody) {
    event.preventDefault();

    const bookNumber = document.getElementById("bookNumber").value;

    removeRow(booksTableBody, parseInt(bookNumber) - 1);
}


function ready() {
    const addBookButton = document.getElementById("addBookButton");
    const removeBookButton = document.getElementById("removeBookButton");
    const booksTableBody = document.getElementById("booksTableBody");

    addBookButton.addEventListener("click", (event) => onAddBookButtonClick(event, booksTableBody));
    removeBookButton.addEventListener("click", (event) => onRemoveBookButtonClick(event, booksTableBody));
}


document.addEventListener("DOMContentLoaded", ready);