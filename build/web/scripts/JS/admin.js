// Perserve scroll postiion function
document.addEventListener("DOMContentLoaded", function (event) {
    var scrollpos = localStorage.getItem('scrollpos');
    if (scrollpos)
        window.scrollTo(0, scrollpos);
});

window.onbeforeunload = function (e) {
    localStorage.setItem('scrollpos', window.scrollY);
};
// Turn password into aterisks
document.addEventListener("DOMContentLoaded", function () {
    var passwordElements = document.querySelectorAll('.password');

    passwordElements.forEach(function (element) {
        var length = element.textContent.length;
        element.textContent = '*'.repeat(length);
    });
});