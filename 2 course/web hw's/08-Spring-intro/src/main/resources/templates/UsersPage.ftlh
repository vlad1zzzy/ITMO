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
                <th>Status</th>
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
                <#list users as user>
                    <tr>
                        <td class="id"><a href="/user/${user.id}">${user.id}</a></td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <td class="status">
                            <form method="post" action="">
                                <input type="hidden" name="userId" value="${user.id}"/>
                                <#if user.disabled>
                                    <input type="hidden" name="disabled" value="false">
                                    <input type="submit" class="disabled" value="Enable">
                                <#else>
                                    <input type="hidden" name="disabled" value="true">
                                    <input type="submit" class="enabled" value="Disable">
                                </#if>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
