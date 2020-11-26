<#-- @ftlvariable name="notice" type="ru.itmo.wp.domain.Notice" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeForm" -->

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
                        <textarea name="content" id="content" cols="24" rows="10">${noticeForm.content!}</textarea>
                    </div>
                </div>
                <@c.error "noticeForm.content"/>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>
</@c.page>
