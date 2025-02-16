function submit_form(id) {
    let form = $(`#${id}`)
    let url = form.attr("action")
    let data = $(`#${id} :input`).serialize() || {};

    data["_csrf"] = $('[name="_csrf"]').val();

    console.log(data);

    $.ajax({
        url: url,
        data: data,
        type: 'POST',
        success: () => {
            $(form).parent().remove()
        }
    })

}

function form_submit_in_table(id) {
    $(`#${id}`).submit()
}

function copy(that) {
    var inp = document.createElement('input');
    document.body.appendChild(inp)
    inp.value = that.textContent.trim()
    inp.select();
    document.execCommand('copy', false);
    inp.remove();

    $('.messages').append(
        `
            <div class="alert alert-success notifs" role="alert">
                Скопировано
            </div>
        `
    )

    setTimeout(() => {
        $('.notifs').remove()
    }, 1000)
}


$(document).on("click", '.fe.fe-copy', (e) => {
    copy(e.target)
})


$('.dropzone').each((i, e) => {
    let accept = $(e).attr("data-accept")

    let params = {};
    params.save = true;
    let fileKey = $(e).attr("data-file-key");

    if (fileKey) {
        params.fileKey = fileKey;
    }

    if (accept == undefined) {
        accept = '.jpg, .png, .jpeg, .ico'
    }

    Dropzone.options.myAwesomeDropzone = false;
    Dropzone.autoDiscover = false;
    var myDropzone = new Dropzone(e, {
        url: $(e).attr("data-url"),
        parallelUploads: 1,
        acceptedFiles: accept,
        maxFilesize: 10,
        maxFiles: $(e).attr('data-max'),
        params: params,
        previewsContainer: `#${$(e).find('.dz-preview-container').attr('id')}`,
        success: (file, response) => {
            var removeButton = Dropzone.createElement(`<a class="dz-remove" data-dz-remove>Delete</a>`);
            removeButton.addEventListener("click", function (ev) {
                ev.preventDefault();
                ev.stopPropagation();
                myDropzone.removeFile(file);

                data = {}

                if(fileKey) {
                    data['fileKey'] = fileKey;
                }

                data['filePath'] = response.filePath;

                $.ajax({
                    url: $(e).attr('data-delete'),
                    type: 'POST',
                    data: data,
                    success: () => {
                        console.log('success')
                    },
                    error: () => {
                        console.log('error')
                    }
                })

            });
            file.previewElement.appendChild(removeButton);
        }
    });


})


setTimeout(() => {
    $('.notifs').remove()
}, 3000)


