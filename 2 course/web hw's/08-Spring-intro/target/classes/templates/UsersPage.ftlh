<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user?? && !user.disabled><th>Status</th></#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                    <tr>
                        <td class="id"><a href="/user/${u.id}">${u.id}</a></td>
                        <td class="login">${u.login}</td>
                        <td class="creationTime">${u.creationTime}</td>
                        <#if user?? && !user.disabled>
                            <td class="status">
                                <#if user.login != u.login>
                                    <form method="post" action="">
                                        <input type="hidden" name="userId" value="${u.id}"/>
                                        <#if u.disabled>
                                            <input type="hidden" name="disabled" value="false">
                                            <input type="submit" class="disabled" value="Enable">
                                        <#else>
                                            <input type="hidden" name="disabled" value="true">
                                            <input type="submit" class="enabled" value="Disable">
                                        </#if>
                                    </form>
                                <#else>
                                    <div>You</div>
                                </#if>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
