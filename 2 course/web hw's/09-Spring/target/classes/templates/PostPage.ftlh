<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="post">
        <@c.post post/>
        <div class="comments">
            <h3>Comments:</h3>
            <#if comments?? && comments?size gt 0>
                <ul>
                    <#list comments as comm>
                        <li>
                            <span class="user">${comm.user.login}</span>
                            <span class="creationTime">(${comm.creationTime})</span>
                            <div class="content">${comm.text}</div>
                        </li>
                    </#list>
                </ul>
            <#else>
                You can be first <#if !user??>(but login before)</#if>
            </#if>
        </div>
        <#if user??>
            <div class="form">
                <div class="header">Comment Post</div>
                <div class="body">
                    <form method="post" action="">
                        <input type="hidden" name="action" value="commentPost"/>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text">${comment.text!}</textarea>
                            </div>
                            <@c.error "comment.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Comment">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
    </div>
</@c.page>

