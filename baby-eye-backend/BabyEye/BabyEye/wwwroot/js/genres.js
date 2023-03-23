function createGenreItemHtml(genre) {
    return `
        <li class="list-group-item d-flex justify-content-between align-items-center">
            ${genre.name}
            <button id="btn_del_genre_${genre.id}" type="button" class="btn btn-danger">Delete</button>
        </li>
    `
}

function setUpDeleteClickListeners() {
    $('button[id^="btn_del_genre_"]').click(function () {
        const id = this.id.replace('btn_del_genre_', '')
        console.log('click ' + id)
        removeGenre(id)
    })
}

$(document).ready(function () {
    setUpDeleteClickListeners()

    getGenres = (complete) => {
        get(
            'http://localhost:7124/admin/music/genres-list',
            (json) => {
                var resultHtml = ''
                json.genres.forEach((genre) => {
                    resultHtml += createGenreItemHtml(genre)
                })
                $('#genres-list').html(resultHtml)
                setUpDeleteClickListeners()
            },
            () => { alert('Error get genres') },
            () => { complete() }
        )
    }

    createGenre = () => {
        const newGenre = { Name: $('#new-genre-input').val() }

        create(
            'http://localhost:7124/admin/music/create-genre',
            newGenre,
            (res) => {
                getGenres()
                $('#new-genre-input').val("")
            }
        )
    }

    removeGenre = (id) => {
        deleteById(
            'http://localhost:7124/admin/music/delete-genre',
            id,
            (res) => { getGenres() }
        )
    }

    getGenres(() => {
        $('#spinner').hide()
    })
})
