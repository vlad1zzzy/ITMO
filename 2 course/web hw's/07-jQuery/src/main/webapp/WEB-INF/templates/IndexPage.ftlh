<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information">By <span class="user"></span>, <span class="creationTime"></span>, translation
                </div>
                <div class="body"></div>
                <ul class="attachment">
                    <li>Announcement of <a href="#"></a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <div class="creationTime"></div>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            <div class="empty">NO ARTICLES YET</div>
        </div>
    </div>
    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.find(".empty").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    let empty = true;
                    response["articles"].forEach(function (article) {
                        if (!article["hidden"]) {
                            empty = false;
                            $noData.hide();
                            const $article = $($(".articles template").prop("content")).clone();
                            $article.find(".user").text(findUser(article["userId"])["login"]);
                            $article.find(".title").text(article["title"]);
                            $article.find(".body").text(article["text"]);
                            $article.find(".creationTime").text(article["creationTime"]);
                            $article.find(".attachment li a").text("something");
                            $(".articles").append($article);
                        }
                    });

                    function findUser(userId) {
                        return response["users"].find(user => user["id"] === userId);
                    }

                    if(empty) {
                        $noData.find(".empty").text("NO DATA YET");
                    }
                }

            );
        })
    </script>
</@c.page>