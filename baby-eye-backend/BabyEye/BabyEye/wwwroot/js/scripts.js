function get(url, success, error = (err) => { alert('Error get') }, complete = () => {  }) {
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: (json) => { success(json) },
        error: (err) => { error(err) },
        complete: () => { complete() }
    })
}

function create(url, data, success, error = (err) => { alert('Error create') }) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'html',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: (res) => { success(res) },
        error: (err) => { error(err) }
    })
}

function deleteById(url, id, success, error = (err) => { alert('Error delete') }) {
    $.ajax({
        type: 'DELETE',
        url: url + '/' + id,
        contentType: false,
        processData: false,
        success: (res) => { success(res) },
        error: (err) => { error(err) }
    })
}