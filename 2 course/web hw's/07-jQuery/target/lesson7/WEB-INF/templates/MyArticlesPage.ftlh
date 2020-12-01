<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="article_id"></td>
                    <td class="article_title"></td>
                    <td>
                        <label class="toggle">
                            <input type="button" class="vision" value="">
                        </label>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3" class="empty">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    let empty = true;

                    response["articles"].forEach(function (article) {
                        empty = false;
                        $noData.hide();
                        const $tr = $($(".articles template").prop("content")).clone();
                        $tr.find(".article_id").text(article["id"]);
                        $tr.find(".article_title").text(article["title"]);
                        let val2 = article["hidden"] ? "Hide" : "Show";
                        $tr.find(".vision").attr({
                            value: article["hidden"] ? "Show" : "Hide",
                            onclick: "change(" + article["id"] + ", \"" + val2 + "\")",
                        })
                        $tr.find("tr").attr("data-articleId", article["id"]);
                        $(".articles tbody").append($tr);
                    });

                    if (empty) {
                        $noData.find(".empty").text("NO DATA YET");
                    }
                }
            );
            change = function (id, val1) {
                ajax({
                        action: "changeVision",
                        articleId: id,
                    },
                    function (response) {
                        let val2 = val1 === "Show" ? "Hide" : "Show";
                        $("[data-articleId*=\"" + id + "\"] .vision").attr({
                            value: val1,
                            onclick: "change(" + id + ", \"" + val2 + "\")",
                        })
                        notify("Article vision of \"" + response["article"].title + "\" was successfully changed!")
                    }
                )
            }

        })
    </script>
</@c.page>
