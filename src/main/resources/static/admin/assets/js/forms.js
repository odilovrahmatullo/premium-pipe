const getHeaders = () => {
    let csrf = $('[name="_csrf"]').val();
    let csrfHeader = $('[name="_csrf_header"]').val();

    let headers = {};

    headers[`${csrfHeader}`] = csrf;

    return headers;
}


const getFormData = (e) => {
    const formData = new FormData($(e.target)[0]);

    var data = {};
    formData.forEach((value, key) => data[key] = value);

    return data;
}
