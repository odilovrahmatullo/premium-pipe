$(document).on("click", ".collab-edit", (e) => {
    const data = $(e.target).data();

    $("#collaborator-change-form").find("[name='name']").val(data?.name);
    $("#collaborator-change-form").find("[name='email']").val(data?.email);
    $("#collaborator-change-form").find(`option[value='${data?.role}']`).change();
    $("#collaborator-change-form").attr("data-id", data?.id);

    $("#exampleModal").modal("show");
})


$(document).on("submit", "#collaborator-change-form", (e) => {
    e.preventDefault();

    const id = $(e.target).attr("data-id");

    const formData = getFormData(e);

    $.ajax({
        url: `/admin/api/collaborator/${id}/edit`,
        type: "POST",
        headers: getHeaders(),
        data: JSON.stringify(formData),
        contentType: "application/json; charset=utf-8",
        datatype: 'json',
        success: (data) => {
            const row = $(`tr[data-id=${data?.id}]`);
            
             console.log(row, data);

            $(row).find("p.name").text(data?.name);
            $(row).find("p.email").text(data?.email);
            $(row).find(".collab-edit").data(data);

            if(data?.role == "AUTHOR") {
                $(row).find(".role").text("Автор");
            } else {
                $(row).find(".role").text("Переводчик");
            }
            
            $("#exampleModal").modal("hide");
            
        },
        error: (xhr, status, error) => {
            console.log(xhr, status, error);
        }
    })
})
