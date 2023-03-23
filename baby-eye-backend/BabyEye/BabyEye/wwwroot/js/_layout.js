var menuItems = Array.from(document.getElementsByClassName('nav-link'))
menuItems.forEach(item => {
    item.addEventListener('click', function () {
        Array.from(document.getElementsByClassName('nav-link'))
            .forEach(item => function () {
                item.classList.remove('active')
            })
        item.classList.add('active')
    })
})

function markMenuItemAsActive(item) {
    Array.from(document.getElementsByClassName('nav-link'))
        .forEach(item => function () {
            item.classList.remove('active')
        })
    console.log(item)
    var activeElementName = ''
    switch (item) {
        case 'music':
            activeElementName = 'navLinkMusic'
            break;
        case 'admins':
            activeElementName = 'navLinkAdmins'
            break;
        default:
            console.warn('No matching types')
    }
    console.log(activeElementName)
    document.getElementById(activeElementName).classList.add('active')
}