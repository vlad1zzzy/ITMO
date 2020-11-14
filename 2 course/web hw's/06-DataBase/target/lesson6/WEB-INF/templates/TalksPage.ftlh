<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="talks">
        <div class="form-box">
            <div class="header">Send Message</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="send"/>
                    <div class="field text">
                        <div class="name">
                            <label for="text">Text:</label>
                        </div>
                        <textarea name="text" id="text" cols="30" rows="10">${text!}</textarea>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="users">Send to:</label>
                        </div>
                        <select name="addressee" id="users">
                            <#list users as user>
                                <option>${user.login!}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Send">
                    </div>
                    <div class="error">
                        ${error!}
                    </div>
                </form>
            </div>
        </div>
        <div class="datatable">
            <div class="caption">Messages</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Message</th>
                    <th>Time</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id}</td>
                        <td>${users[c.findIndexBy(users, "id", talk.sourceUserId)].login}</td>
                        <td>${users[c.findIndexBy(users, "id", talk.targetUserId)].login}</td>
                        <td>${talk.text}</td>
                        <td>${talk.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>