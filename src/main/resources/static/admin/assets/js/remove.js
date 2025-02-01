function submitFilteredForm() {
    const form = document.getElementById('filterForm');
    const inputs = form.querySelectorAll('input, select');
    console.log("slslslslls")

    inputs.forEach(input => {
        if (!input.value) {
            input.removeAttribute('name'); // Bo'sh maydonlarni query parametrlarga qo'shilishini oldini oladi
        }
    });

    form.submit(); // Formani jo'natadi
}
