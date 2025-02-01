$(document).ready(() => {
    let url = window.location.href;

    let id = url.split("#").slice(-1)

    $(`[data-bs-target="#${id[0]}"]`).tab("show")
})


$(document).on("click", '.main-tab-links', (e) => {
    let btn = $(e.target).find('button');

    let id = $(btn).attr("data-bs-target");

    page_url = window.location.href

    if(page_url.includes('#')) {
        page_url = page_url.split('#')[0]
    }

    window.location = `${page_url}${id}`


    $(btn).tab("show")
})


$('#tab_complaints').on("shown.bs.tab", (e) => {
    let id = $(e.target).attr("data-object-id");

    $.ajax({
        url: "/admin/read_complaints",
        data: {
            "news_id": id
        },
        success: () => {
            $(e.target).find("#complaints_count").remove()
        }
    })

})