$('[data-bs-target="#newGroupModal"]').on("click", (e) => {

    $('#newGroupModal').find('[name="name"]').val("")
    $('#newGroupModal').find('[name="subText"]').val("")
    $('#newGroupModal').find('#transl_group_key_error').html("")
    $('#newGroupModal').find('#transl_group_title_error').html("")
    $('#newGroupModal').modal('show')
})


$('#add_new_group_form').on("submit", (e) => {
    e.preventDefault()  
    let data = getFormData(e)
    let url = $(e.target).attr('action')

    $.ajax({
        url: url,
        headers: getHeaders(),
        type: 'POST',
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        datatype: 'json',
        success: (data) => {
            $('#group_links').html(
                $('#group_links').html() + 
                `
                    <a href="/admin/translations/groups/${ data?.id }" 
                    class="btn btn-info me-3 bg-transparent 
                    text-info group-link">${ data?.name }</a>
                `
            )

            $(e.target).find('[name="title"]').val("")
            $(e.target).find('[name="subText"]').val("")
            $('#newGroupModal').modal("hide")
        },
        error: (xhr, status, error) => {
            const errorJson = xhr.responseJSON['error'];

            $('#newGroupModal').find('#transl_group_key_error').css("display", "block");
            $('#newGroupModal').find('#transl_group_key_error').text(errorJson?.subText);
        }
    })
})
