<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="createArticle article">
        <div class="form-box">
            <div class="header">Create Article</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="create"/>
                    <div class="field">
                        <div class="name">
                            <label for="title">Title:</label>
                        </div>
                        <div class="value">
                            <input name="title" id="title" type="text">
                        </div>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text:</label>
                        </div>
                        <div class="value">
                            <textarea name="text" id="text" cols="24" rows="10"></textarea>
                        </div>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Create">
                    </div>
                    <div class="error">
                        ${error!}
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
        $(function () {
            $(".createArticle form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");

                ajax({
                        action: "create",
                        title: title,
                        text: text,
                    },
                    function (response) {
                        $error.text(response["error"])
                    }
                )
                return false;
            });
        })
    </script>
</@c.page>