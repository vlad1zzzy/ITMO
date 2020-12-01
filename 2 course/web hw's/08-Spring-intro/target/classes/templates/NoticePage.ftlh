<#-- @ftlvariable name="notice" type="ru.itmo.wp.domain.Notice" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="create form-box">
        <div class="header">Create Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="content">Content: </label>
                    </div>
                    <div class="value">
                        <textarea name="content" id="content" cols="24" rows="10">${notice.content!}</textarea>
                    </div>
                    <@c.error "notice.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>
</@c.page>
