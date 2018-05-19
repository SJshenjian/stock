// 发贴
$('.addArticle').unbind('click').click(function() {
    window.open('/community/addArticle');
});

$('#searchContent').blur(function () {
    debugger;
    var searchContent = $('#searchContent').val();
    searchContent = searchContent.replace("/\s/g", "");
    if (searchContent == null || searchContent.length <= 0) {
        $('.search').attr("disabled", true);
    } else {
        $('.search').attr("disabled", false);
    }
});
